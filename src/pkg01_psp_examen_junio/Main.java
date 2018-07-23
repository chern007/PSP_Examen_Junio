/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01_psp_examen_junio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS-HC
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {  
            
            ServerSocket ss = new ServerSocket();
            Socket sc;
            
            //Creamos la banca con 1000 euros
            Banca miBanca = new Banca(1000);
            
            while (true) {                
                
            sc = ss.accept();
            
            //creamos un nuevo hilo dedicado al cliente
            new Thread(new Servidor(sc,miBanca)).start();
                 
                
                
                
                
                
                
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
    }
    
}
