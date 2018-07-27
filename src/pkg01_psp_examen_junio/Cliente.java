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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS-HC
 */
public class Cliente {

    public static void main(String[] args) {

        try {

            Socket sc = new Socket("localhost", 6060);

            DataInputStream entra = new DataInputStream(sc.getInputStream());
            DataOutputStream sale = new DataOutputStream(sc.getOutputStream());

            Scanner entrada = new Scanner(System.in);

            String nombre;
            do {

                System.out.println("¿Cómo te llamas?");
                nombre = entrada.nextLine();

            } while (entrada.equals(""));

            boolean finalJuego = false;

            //mientras no finalice el juego
            while (!finalJuego) {

                int cantidad = -1;
                do {

                    System.out.println("¿Cuanto quieres apostar?");
                    //por si mete otra cosa que no sea un numero
                    try {
                        cantidad = entrada.nextInt();
                        entrada.nextLine();
                    } catch (Exception e) {
                    }

                } while (cantidad == -1);

                sale.writeInt(cantidad);//W1

                boolean apuestaAceptada = entra.readBoolean();//R1

                if (apuestaAceptada) {

                    System.out.println("Comienza el juego:");

                    boolean finJugadorSacarCartas = false;
                    String auxRespuestas = "";
                    boolean teHasPasado = false;
                    while (!finJugadorSacarCartas) {

                        System.out.println(entra.readUTF());//R2 has sacado la carta ...

                        auxRespuestas = entra.readUTF();
                        String pregunta = auxRespuestas;
                        if (auxRespuestas.contains("otra carta")) {//R3                            QUIERES SACAR OTRA CARTA o  TE HAS PASADO

                            do {
                                System.out.println(pregunta);

                                auxRespuestas = entrada.nextLine();

                            } while (!auxRespuestas.toLowerCase().equals("si") && !auxRespuestas.toLowerCase().equals("no"));

                            //si no quiere sacar otra carta le mandamos un true y si no un false
                            if (auxRespuestas.equals("si")) {

                                sale.writeBoolean(false);//W2

                            } else {
                                sale.writeBoolean(true);//W2
                                finJugadorSacarCartas= true;
                            }

                        } else {

                            System.out.println(auxRespuestas);//te has pasado
                            teHasPasado = true;
                            finJugadorSacarCartas = true;
                        }

                    }//while saca cartas jugador
                    
                    
                    if (!teHasPasado) {                        

                        boolean finSacaCarta = false;
                        String SIGUIENTEoGANADOoPERDIDO = "";
                        while (!finSacaCarta) {

                            SIGUIENTEoGANADOoPERDIDO = entra.readUTF();//R4
                            System.out.println(SIGUIENTEoGANADOoPERDIDO);

                            if (!SIGUIENTEoGANADOoPERDIDO.contains("ha sacado la carta")) {
                                finSacaCarta = true;
                            }
                        }

                    }

                    
                    auxRespuestas = entra.readUTF();//W6      quieres seguir jugando??
                    String pregunta = auxRespuestas;
                    do {
                        System.out.println(pregunta);

                        auxRespuestas = entrada.nextLine();

                    } while (!auxRespuestas.toLowerCase().equals("si") && !auxRespuestas.toLowerCase().equals("no"));

                    if (auxRespuestas.equals("si")) {

                        sale.writeBoolean(false);//seguimos jugando

                    } else {

                        sale.writeBoolean(true);//terminamos de jugar
                        finalJuego = true;
                    }

                } else {

                    System.out.println("No hay fondo suficiente para cubrir la apuesta, pruebe mas tarde.");

                }

            }
            
            //cerramos las comunicaciones
            entra.close();
            sale.close();
            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
