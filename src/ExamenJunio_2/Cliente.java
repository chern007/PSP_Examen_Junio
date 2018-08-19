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
            Socket sc = new Socket("localhost",6060);
            Scanner entrada = new Scanner(System.in);
            
            DataInputStream entra = new DataInputStream(sc.getInputStream());
            DataOutputStream sale = new DataOutputStream(sc.getOutputStream());
            
            //pedimos el nombre
            String nombre = "";
            do{
            System.out.println("Como te llamas??");            
            nombre = entrada.nextLine();
            }while(nombre.equals(""));
            
            //mandamos el nombre
            sale.writeUTF(nombre);
            
            //recogemos el saludo del servidor
            System.out.println(entra.readUTF());
            
            //recogemos la pregunta de cuanto apostar
            String preguntaCantidad = entra.readUTF();
            
            //recogemos la cantidad que queremos apostar
            int cantidad = -1;
            do {   
                
                System.out.println(preguntaCantidad);
                
                cantidad = entrada.nextInt(); entrada.nextLine();
                
            } while (!(cantidad > 0));
            
            //mandamos la cantidad
            sale.writeInt(cantidad);
            
            
            
            boolean finJuego = false;
            while (!finJuego) {
            
            boolean seHaPasadoCliente = false;
            double puntuacionCliente = 0;
            boolean finSacaCartasCliente = false; 
            while(!seHaPasadoCliente && !finSacaCartasCliente){
                
                
                
                
                
                
            }
                
                
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        
        
        
        
        
    }
    
    
    
    
    
}
