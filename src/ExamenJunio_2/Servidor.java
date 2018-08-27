/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExamenJunio_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS-HC
 */
public class Servidor implements Runnable {

    Banca miBanca;
    Socket sc;
    Baraja miBaraja;

    public Servidor(Banca miBanca, Socket sc) {
        this.miBanca = miBanca;
        this.sc = sc;
        this.miBaraja = new Baraja();
    }

    @Override
    public void run() {

        try {

            DataInputStream entra = new DataInputStream(sc.getInputStream());
            DataOutputStream sale = new DataOutputStream(sc.getOutputStream());

            String nombre = entra.readUTF();//R1

            //enviamos el saludo
            sale.writeUTF("Hola " + nombre + " vamos a jugar al 7 y medio.");//W1

            boolean finJuego = false;
            while (!finJuego) {

                sale.writeUTF("Cuanto dinero quieres apostar?");//W2  
                int importe = entra.readInt();//R2

                if (miBanca.getSaldo() > importe) {
                    sale.writeUTF("Apuesta aceptada.");//W3

                    miBanca.reservarApuesta(importe);//reservamos la apuesta del cliente

                    //bucle saca cartas CLIENTE
                    boolean finSacaCartasCliente = false;
                    boolean seHaPasado = false;
                    double puntuacionCliente = 0;
                    while (!finSacaCartasCliente && !seHaPasado) {

                        String carta = miBaraja.sacaCarta();

                        sale.writeUTF("Has sacado la carta " + carta.split("_")[0] + " de " + carta.split("_")[1]);//W4

                        puntuacionCliente = puntuacionCliente + miBaraja.valorCarta(carta);

                        if (puntuacionCliente > 7.5) {

                            sale.writeUTF("Has PERDIDO, te has pasado de 7.5");//W6
                            seHaPasado = true;
                            miBanca.ingresarAbanca(importe * 2);

                        } else {

                            sale.writeUTF("Quieres sacar otra carta?");//W5
                            String otraCarta = entra.readUTF();//R3

                            if (otraCarta.equals("NO")) {
                                finSacaCartasCliente = true;
                            }
                        }

                    }

                    if (!seHaPasado) {

                        //bucle saca cartas MAQUINA
                        boolean seHaPasadoMaquina = false;
                        boolean finSacaCartasMaquina = false;
                        double puntuacionMaquina = 0;
                        while (!seHaPasadoMaquina && !finSacaCartasMaquina) {

                            String carta = miBaraja.sacaCarta();

                            sale.writeUTF("La máquina ha sacado la carta " + carta.split("_")[0] + " de " + carta.split("_")[1]);//W6

                            puntuacionMaquina = puntuacionMaquina + miBaraja.valorCarta(carta);

                            if (puntuacionMaquina > 7.5) {

                                seHaPasado = true;

                                sale.writeUTF("Has GANADO, la máquina se ha pasado de 7.5");//W6

                            } else if (puntuacionMaquina >= puntuacionCliente) {

                                finSacaCartasMaquina = true;
                                sale.writeUTF("Has PERDIDO, la máquina ha sacado una puntuación de " + puntuacionMaquina + " mientras que tu has sacado "+ puntuacionCliente);//W6
                                miBanca.ingresarAbanca(importe * 2);
                            }

                        }
                    }

                    sale.writeUTF("Quieres volver a echar otra partida?");//W7
                    String otraPartida = entra.readUTF();//R4
                    if (otraPartida.equals("NO")) {
                        finJuego = true;
                    }

                } else {

                    sale.writeUTF("No hay saldo disponible. Por favor intentelo más tarde.");//W3

                }

            }

            entra.close();
            sale.close();
            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
