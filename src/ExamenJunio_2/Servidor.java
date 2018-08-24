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
    
    
    
    
    
    
    
    
    
    }

}
