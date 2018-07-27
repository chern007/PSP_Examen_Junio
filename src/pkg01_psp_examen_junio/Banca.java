/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01_psp_examen_junio;

/**
 *
 * @author CARLOS-HC
 */
public class Banca {

    int fondo;

    public Banca(int fondo) {
        this.fondo = fondo;
    }

    //nos dira si la banca puede cubrir la apuesta del cliente
    public synchronized boolean cubreApuesta(int cantidadApostada) {

        if (fondo >= cantidadApostada) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void sumarAbanca(int cantidad) {

        fondo += cantidad;

    }

    public synchronized void restarAbanca(int cantidad) {

        fondo -= cantidad;

    }

    public synchronized int dameFondo() {

        return this.fondo;

    }

}
