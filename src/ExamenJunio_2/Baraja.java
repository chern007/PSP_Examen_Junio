/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExamenJunio_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author CARLOS-HC
 */
public class Baraja {

    List<String> cartas = new ArrayList<String>();

    public Baraja() {

        String[] tmp = {"AS_bastos", "2_bastos", "3_bastos", "4_bastos", "5_bastos", "6_bastos", "7_bastos", "SOTA_bastos", "CABALLO_bastos", "REY_bastos",
            "AS_copas", "2_copas", "3_copas", "4_copas", "5_copas", "6_copas", "7_copas", "SOTA_copas", "CABALLO_copas", "REY_copas",
            "AS_espadas", "2_espadas", "3_espadas", "4_espadas", "5_espadas", "6_espadas", "7_espadas", "SOTA_espadas", "CABALLO_espadas", "REY_espadas",
            "AS_oros", "2_oros", "3_oros", "4_oros", "5_oros", "6_oros", "7_oros", "SOTA_oros", "CABALLO_oros", "REY_oros"
        };
        
        cartas.addAll(Arrays.asList(tmp));//añadimos el array a la lista

    }
    
    
    public String sacaCarta(){
    
        String cartaSeleccionada;
        
        int num = new Random().nextInt(cartas.size());
        
        cartaSeleccionada = cartas.get(num);//sacamos la carta
        
        cartas.remove(num);//borramos del mazo la carta que acabamos de sacar
        
        return cartaSeleccionada;    
                
    }
    
    public double valorCarta(String carta){
       
        String valor;
        
        String[] mediosPuntos = {"AS","SOTA","CABALLO","REY"};
        
        valor = carta.split("_")[0];
        
        //si es una figura retornamos un valor de 0.5
        if (Arrays.asList(mediosPuntos).contains(valor)) {
            return 0.5;
            
        }else{            
            return Double.valueOf(valor);            
            
        }       
        
        
    }
    
    

}
