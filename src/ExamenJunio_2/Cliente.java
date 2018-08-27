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

            Scanner entrada = new Scanner(System.in);

            Socket sc = new Socket("localhost", 6060);

            DataInputStream entra = new DataInputStream(sc.getInputStream());
            DataOutputStream sale = new DataOutputStream(sc.getOutputStream());

            String nombre = "";
            do {
                System.out.println("Hola como te llamas?");

                nombre = entrada.nextLine();

            } while (entrada.equals(""));

            sale.writeUTF(nombre);//W1

            //imprimimos el saludo
            System.out.println(entra.readUTF());//R1

            boolean finJuego = false;
            while (!finJuego) {

                String cuantoDinero = entra.readUTF();//R2

                int importe = -1;
                do {
                    System.out.println(cuantoDinero);
                    try {
                        importe = entrada.nextInt();
                        entrada.nextLine();
                    } catch (Exception e) {
                    }

                } while (importe < 1);

                //mandamos el importe
                sale.writeInt(importe);//W2

                String aceptacion = entra.readUTF();//R3 

                System.out.println(aceptacion);

                if (aceptacion.contains("aceptada")) {

                    //bucle saca cartas jugador
                    boolean seHaPasadoCliente = false;
                    boolean finSacarCartas = false;

                    while (!seHaPasadoCliente && !finSacarCartas) {

                        //leemos la carta sacada
                        System.out.println(entra.readUTF());//R4

                        //leemos y guardamos si nos hemos pasado o pregunta de continuar
                        String pasadoContinuar = entra.readUTF();//R5

                        if (pasadoContinuar.contains("PERDIDO")) {

                            System.out.println(pasadoContinuar);
                            seHaPasadoCliente = true;

                        } else {

                            String respuesta = "";
                            do {
                                System.out.println(pasadoContinuar);
                                respuesta = entrada.nextLine().toUpperCase();//R6

                            } while (!respuesta.equals("SI") && !respuesta.equals("NO"));

                            //enviamos la respuesta
                            sale.writeUTF(respuesta);//W3

                            if (respuesta.equals("NO")) {

                                finSacarCartas = true;
                            }

                        }
                    }

                    if (!seHaPasadoCliente) {

                        //bucle saca cartas MAQUINA
                        boolean seHaPasadoMaquina = false;
                        boolean finSacaCartasMaquina = false;
                        while (!finSacaCartasMaquina && !seHaPasadoMaquina) {

                            String queHaPasado = entra.readUTF();//R7
                            System.out.println(queHaPasado);

                            if (queHaPasado.contains("GANADO")) {

                                seHaPasadoMaquina = true;

                            } else if (queHaPasado.contains("PERDIDO")) {

                                finSacaCartasMaquina = true;
                            }
                        }
                    }
                    
                    
                    //respondemos si queremos seguir jugando                    
                    String seguimos = entra.readUTF();
                    String respuesta = "";
                    do {                        
                        System.out.println(seguimos);
                        respuesta = entrada.nextLine().toUpperCase();                                      
                    } while (!respuesta.equals("SI") && !respuesta.equals("NO"));
                    
                    sale.writeUTF(respuesta);

                    if (respuesta.equals("NO")) {
                        finJuego= true;
                    }
                    
                }
            }
            
            entra.close();
            sale.close();
            sc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
