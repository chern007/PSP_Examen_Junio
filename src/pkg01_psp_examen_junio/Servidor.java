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

                if (miBanca.cubreApuesta(entra.readInt())) {//R1

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

                            paraSacarCartas = !entra.readBoolean();//R2

                        }else{
                            seHaPasado = true;
                        }

                    } while (!paraSacarCartas && !seHaPasado);

                } else {

                    sale.writeBoolean(false);//W1 le mandamos el cancel
                    finJuego = true;
                }

                //quiere seguir apostando???//W
                //si/no R --> fin juego
                
                
            }//primer while

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
