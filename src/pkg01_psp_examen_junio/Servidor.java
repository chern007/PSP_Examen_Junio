/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01_psp_examen_junio;

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

    Socket sc;
    Banca miBanca;
    Baraja miBaraja;

    public Servidor(Socket sc, Banca miBanca) {

        this.sc = sc;
        this.miBanca = miBanca;
        this.miBaraja = new Baraja();

    }

    @Override
    public void run() {

        try {
            DataInputStream entra = new DataInputStream(sc.getInputStream());
            DataOutputStream sale = new DataOutputStream(sc.getOutputStream());

            boolean finJuego = false;
            while (!finJuego) {

                int cantidadApostada = entra.readInt();//R1

                if (miBanca.cubreApuesta(cantidadApostada)) {

                    miBanca.restarAbanca(cantidadApostada);//le restamos el dinero a la banca para reservarlo

                    sale.writeBoolean(true);//W1 le mandamos el ok

                    //bicle para seguir sacando cartas
                    boolean paraSacarCartas = false;
                    boolean seHaPasado = false;
                    double puntuacionPartida = 0;
                    do {

                        String carta = miBaraja.sacarCarta();

                        //le enseñamos que ha sacado
                        sale.writeUTF("Has sacado la carta " + carta.split("-")[1].toUpperCase() + " de " + carta.split("-")[0].toUpperCase());//W2

                        //sumamos el valor de la carta a la puntuacion
                        puntuacionPartida += miBaraja.sacarValorCarta(carta);

                        if (puntuacionPartida <= 7.5) {

                            //le preguntamos si quiere seguir sacando cartas
                            sale.writeUTF("¿Quieres sacar otra carta?");//W3    

                            paraSacarCartas = entra.readBoolean();//R2

                        } else {
                            sale.writeUTF("Te has pasado de 7.5. Has PERDIDO " + cantidadApostada + " euros.");//W3
                            miBanca.sumarAbanca(cantidadApostada * 2);
                            System.out.println("La banca dipone de un fondo de : " + miBanca.dameFondo() + " euros");
                            seHaPasado = true;
                        }

                    } while (!paraSacarCartas && !seHaPasado);

                    //comienza el turno de la maquina
                    if (!seHaPasado) {

                        double puntuacionPartidaMAQUINA = 0;
                        boolean seHaPasadoMAQUINA = false;
                        boolean haGanadoMaquina = false;
                        String cartaMAQUINA;

                        while (!seHaPasadoMAQUINA && !haGanadoMaquina) {

                            cartaMAQUINA = miBaraja.sacarCarta();
                            sale.writeUTF("La MAQUINA ha sacado la carta " + cartaMAQUINA.split("-")[1].toUpperCase() + " de " + cartaMAQUINA.split("-")[0].toUpperCase());//W4
                            puntuacionPartidaMAQUINA += miBaraja.sacarValorCarta(cartaMAQUINA);

                            if (puntuacionPartidaMAQUINA > 7.5) {

                                seHaPasadoMAQUINA = true;
                                //le mandamos al usuario que ha perdido la maquina
                                sale.writeUTF("La maquina se ha pasado. ¡Has GANADO " + cantidadApostada + " euros!");//W4
                                System.out.println("La banca dipone de un fondo de : " + miBanca.dameFondo() + " euros");

                            } else if (puntuacionPartidaMAQUINA >= puntuacionPartida) {
                                haGanadoMaquina = true;
                                sale.writeUTF("La maquina ha sacado mas puntuacion que tu ¡Has PERDIDO " + cantidadApostada + " euros!");//W4
                                miBanca.sumarAbanca(cantidadApostada * 2);
                                System.out.println("La banca dipone de un fondo de : " + miBanca.dameFondo() + " euros");
                            }

                        }

                    }

                } else {

                    sale.writeBoolean(false);//W1 le decimos que no puede cubrir su apuesta
                    //finJuego = true;
                }

                sale.writeUTF("¿Quieres seguir jugando y echar otra partida?");//W6
                finJuego = entra.readBoolean();//si elige si el juego terminará

            }//primer while            

            //cerramos las comunicaciones
            entra.close();
            sale.close();
            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
