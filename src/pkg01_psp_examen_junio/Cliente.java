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
            
            Socket sc = new Socket("localhost",6060);
            
            DataInputStream entra = new DataInputStream(sc.getInputStream());
            DataOutputStream sale = new DataOutputStream(sc.getOutputStream());
            
            Scanner entrada = new Scanner(System.in);
            
            
            
            String nombre;
            do{
                
                System.out.println("¿Cómo te llamas?");
                nombre = entrada.nextLine();
                
            }while(entrada.equals(""));
            
            
            boolean finalJuego = false;
            
            //mientras no finalice el juego
            while (!finalJuego) {                
                
                
                int cantidad = -1;
                do { 
                    
                    System.out.println("¿Cuanto quieres apostar?"); 
                    //por si mete otra cosa que no sea un numero
                    try {
                        cantidad = entrada.nextInt();
                    } catch (Exception e) {
                    }
                    
                } while (cantidad == -1);
                
                sale.writeInt(cantidad);//W1
                
                boolean apuestaAceptada = entra.readBoolean();//R1
                
                if (apuestaAceptada) {
                    
                    System.out.println("Comienza el juego:");
                    
                    System.out.println(entra.readUTF());//R2
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                }else{
                    
                    System.out.println("No hay fondo suficiente para cubrir la apuesta, pruebe mas tarde.");  
                    
                }
                
                
                
            }
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
}
