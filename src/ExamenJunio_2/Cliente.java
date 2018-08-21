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
            Socket sc = new Socket("localhost", 6060);
            Scanner entrada = new Scanner(System.in);

            DataInputStream entra = new DataInputStream(sc.getInputStream());
            DataOutputStream sale = new DataOutputStream(sc.getOutputStream());

            //pedimos el nombre
            String nombre = "";
            do {
                System.out.println("Como te llamas??");
                nombre = entrada.nextLine();
            } while (nombre.equals(""));

            //mandamos el nombre
            sale.writeUTF(nombre);

            //recogemos el saludo del servidor
            System.out.println(entra.readUTF());

            boolean finJuego = false;
            while (!finJuego) {

                //recogemos la pregunta de cuanto apostar
                String preguntaCantidad = entra.readUTF();

                //recogemos la cantidad que queremos apostar
                int cantidad = -1;
                do {
                    System.out.println(preguntaCantidad);

                    try {
                        cantidad = entrada.nextInt();
                    } catch (Exception e) {
                    };

                    entrada.nextLine();

                } while (!(cantidad > 0));

                //mandamos la cantidad
                sale.writeInt(cantidad);

                //leemos si la apuesta es mayor o menor al dinero disponible en la banca
                if (entra.readBoolean()) {

                    boolean seHaPasadoCliente = false;
                    double puntuacionCliente = 0;
                    boolean finSacaCartasCliente = false;
                    while (!seHaPasadoCliente && !finSacaCartasCliente) {

                        //imprimimos la carta que has sacado
                        System.out.println(entra.readUTF());

                        //o has perdido o continuas?
                        String perdidoContinuas = entra.readUTF();

                        if (perdidoContinuas.contains("PERDIDO")) {
                            System.out.println(perdidoContinuas);
                            seHaPasadoCliente = true;
                        } else {

                            String continua = "";
                            do {
                                System.out.println(perdidoContinuas);
                                continua = entrada.nextLine().toUpperCase();

                            } while (!continua.equals("SI") && !continua.equals("NO"));

                            if (continua.equals("SI")) {
                                sale.writeBoolean(false);
                            } else {
                                sale.writeBoolean(true);
                                finSacaCartasCliente = true;
                            }

                        }

                    }

                    if (!seHaPasadoCliente) {

                        //bucle saca cartas maquina
                        boolean finSacaCartasMaquina = false;
                        while (!finSacaCartasMaquina) {

                            String resultadoMaquina = entra.readUTF();
                            System.out.println(resultadoMaquina);

                            if (resultadoMaquina.contains("GANADO") || resultadoMaquina.contains("PERDIDO")) {

                                finSacaCartasMaquina = true;
                            }

                        }
                    }

                    //quieres echar otra partida?
                    String otraPartida = entra.readUTF();

                    String respuesta;
                    do {
                        System.out.println(otraPartida);

                        respuesta = entrada.nextLine().toUpperCase();

                    } while (!respuesta.equals("SI") && !respuesta.equals("NO"));

                    //mandamos la respuesta
                    sale.writeUTF(respuesta);

                    //finalizamos el juego
                    if (respuesta.equals("NO")) {
                        finJuego = true;
                    }

                } else {

                    System.out.println("No hay dinero suficiente para cubrir la apuesta, prueba mas tarde.");
                }

            }//while finJuego
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
