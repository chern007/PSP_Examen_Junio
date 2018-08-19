/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExamenJunio_2;

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
    
    
    public static void main(String[] args) {
        
        try {
            
            ServerSocket ss = new ServerSocket(6060);
            Socket sc;
            
            Banca miBanca = new Banca();//creamos la banca que pasaremos a todos los clientes que se conecten
            
            while (true) {                
               
               sc = ss.accept();
                
               new Thread(new Servidor(miBanca, sc)).start();    
                
                
            }
            
            
            
            
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        
        
    }
    
    
    
}
