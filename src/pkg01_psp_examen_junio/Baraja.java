/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01_psp_examen_junio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author CARLOS-HC
 */
public class Baraja {
    
    
    List<String> cartas = new ArrayList<>();
    List<String> cartasUsadas = new ArrayList<>();
    
    public Baraja(){
        
        String[] tmp ={"picas-2","picas-3","picas-4","picas-4","picas-5","picas-6","picas-7","picas-8","picas-9","picas-10","picas-j","picas-q","picas-k","picas-as",
                        "rombos-2","rombos-3","rombos-4","rombos-4","rombos-5","rombos-6","rombos-7","rombos-8","rombos-9","rombos-10","rombos-j","rombos-q","rombos-k","rombos-as",
                        "treboles-2","treboles-3","treboles-4","treboles-4","treboles-5","treboles-6","treboles-7","treboles-8","trboles-9","treboles-10","treboles-j","treboles-q","treboles-k","treboles-as",
                        "corazones-2","corazones-3","corazones-4","corazones-4","corazones-5","corazones-6","corazones-7","corazones-8","corazones-9","corazones-10","corazones-j","corazones-q","corazones-k","corazones-as"};       
        
        cartas.addAll(Arrays.asList(tmp));
        
        
        
    }
    
    public String sacarCarta(){
      
        //generamos el numero aleatorio
        int numeroRandom = new Random().nextInt(cartas.size());
        
        //sacamos la catrta de la baraja
        String carta = cartas.get(numeroRandom);
        
        //metemos la carta en el fajo de cartas usadas
        cartasUsadas.add(carta);
        
        //borramos la carta de labaraja
        cartas.remove(numeroRandom);
        
        
        return carta;
    }
    
    public double sacarValorCarta(String carta){
    
        double valor;
        
        String numero = carta.split("-")[1];
     
        switch (numero) {
            case "j":
                valor = 0.5;
            break;
            
            case "q":
                valor = 0.5;
            break;
            
            case "k":
                valor = 0.5;
            break;
            
            case "as":
                valor = 1;
            break;
            
            default:
                valor= Double.valueOf(numero);
        }
     
        return valor;
        
    }
    
}
