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

            String nombreCliente = entra.readUTF();//R1

            sale.writeUTF("Hola " + nombreCliente + " vamos a jugar al 7 y medio!");//W1

            sale.writeUTF("Cuanto dinero quieres apostar?");//W1.5
            int importeApuesta = entra.readInt();//R1.5
            
            if(importeApuesta <= miBanca.getSaldo()){

            boolean finJuego = false;
            while (!finJuego) {

                boolean seHaPasadoCliente = false;
                double puntuacionCliente = 0;
                boolean finSacaCartasCliente = false;
                while (!finSacaCartasCliente) {

                    String cartaSacada = miBaraja.sacaCarta();

                    puntuacionCliente += miBaraja.valorCarta(cartaSacada);

                    sale.writeUTF("Has sacado la carta " + cartaSacada.split("_")[0] + " de " + cartaSacada.split("_")[1] + ".");//W2

                    if (puntuacionCliente > 7.5) {

                        sale.writeUTF("Has PERDIDO, te has pasado de 7.5");//W3
                        seHaPasadoCliente = true;
                        finSacaCartasCliente = true;
                        miBanca.ingresarAbanca(importeApuesta * 2);
                    } else {
                        sale.writeUTF("Quieres sacar otra carta?");//W3
                        finSacaCartasCliente = entra.readBoolean();//R2
                    }

                }

                if (!seHaPasadoCliente) {

                    boolean seHaPasadoMaquina = false;
                    double puntuacionMaquina = 0;
                    boolean finSacaCartasMaquina = false;
                    while (!finSacaCartasCliente && !seHaPasadoMaquina) {

                        String cartaSacada = miBaraja.sacaCarta();

                        sale.writeUTF("La máquina ha sacado la carta " + cartaSacada.split("_")[0] + " de " + cartaSacada.split("_")[1] + ".");//W4

                        puntuacionMaquina += miBaraja.valorCarta(cartaSacada);

                        if (puntuacionMaquina > 7.5) {
                            seHaPasadoMaquina = true;
                            sale.writeUTF("Has GANADO, la máquina se ha pasado de 7.5 (" + importeApuesta + " euros)");//W4
                        } else if (puntuacionMaquina >= puntuacionCliente) {
                            finSacaCartasMaquina = true;
                            sale.writeUTF("Has PERDIDO la máquina ha sacado una puntuación de " + puntuacionMaquina + " y tu puntuación ha sido " + puntuacionCliente);//W4
                            miBanca.ingresarAbanca(importeApuesta * 2);
                        }

                    }

                }

                //preguntamos si quiere seguir jugando
                sale.writeUTF("Quieres echar otra partida?");//W5
                String otraPartida = entra.readUTF();//R3

                if (otraPartida.equals("no")) {
                    finJuego = true;
                }

            }//FIN DEL JUEGO
            
            }

            entra.close();
            sale.close();
            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
