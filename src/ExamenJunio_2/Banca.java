/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExamenJunio_2;

/**
 *
 * @author CARLOS-HC
 */
public class Banca {

    int saldo;

    public Banca() {

        this.saldo = 1000;
    }

    public synchronized int getSaldo() {
        return saldo;
    }
    
    

    public synchronized void reservarApuesta(int importe) {

        this.saldo -= importe;        
        
    }
    
    public synchronized void ingresarAbanca(int importe){
        
        this.saldo += importe;
        
    }

}
