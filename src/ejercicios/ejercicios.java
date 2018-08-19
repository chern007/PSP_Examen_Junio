/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author CARLOS-HC
 */
public class ejercicios {

    public static void main(String[] args) {
        
        //sacamos un numero aleatorio entre dos extremos 50 y 100
        
        int min = 50;
        int max = 100;
        
        int num = min + new Random().nextInt(max - min +1);
        
        System.out.println(num);

        //EJERCICIO QUE ENSEÑA A PASAR DE UN ARRAY A UNA LISTA DINAMICA    
        List<String> listaArellenar = new ArrayList<>();

        String[] palabras = {"hola", "caracola"};
        //pasamos del array a la lista
        listaArellenar.addAll(Arrays.asList(palabras));
        //imprimimos cada elemento de la lista    
        listaArellenar.stream().forEach(p -> System.out.println(p));

        //EJERCICIO QUE LEE UN FICHERO DE TEXTO Y LO IMPRIME EN LA CONSOLA
        try {

            RandomAccessFile raf = new RandomAccessFile("C:\\Users\\CARLOS-HC\\Desktop\\Comando tarea psp06.txt", "rwd");

            String linea = "";
            while ((linea = raf.readLine()) != null) {

                System.out.println(linea);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        try {

            Path ruta = Paths.get("C:\\Users\\CARLOS-HC\\Desktop\\Comando tarea psp06.txt");
            byte[] archivo = Files.readAllBytes(ruta);

            System.out.println("La longitud en bytes del archivo es de: " + archivo.length);

            for (byte b : archivo) {
                System.out.print((char) b);
            }

            //EJEMPLO DE CIFRADO DE UN ARCHIVO DE TEXTO
            //creamos el generador de claves
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            //le indicamos al generador la longitud de la clave
            kg.init(128);

            //generamos la clave de cifrado
            SecretKey key = kg.generateKey();

            Cipher cifrador = Cipher.getInstance("AES");
            cifrador.init(Cipher.ENCRYPT_MODE, key);

            byte[] archivoCifrado = cifrador.doFinal(archivo);

            FileOutputStream fos = new FileOutputStream("C:\\Users\\CARLOS-HC\\Desktop\\Comando tarea psp06_CIFRADO.txt");

            fos.write(archivoCifrado);

            fos.close();

            cifrador.init(Cipher.DECRYPT_MODE, key);

            FileInputStream fis = new FileInputStream("C:\\Users\\CARLOS-HC\\Desktop\\Comando tarea psp06_CIFRADO.txt");
            FileOutputStream fos2 = new FileOutputStream("C:\\Users\\CARLOS-HC\\Desktop\\Comando tarea psp062.txt");

            byte[] bufferLectura = new byte[1024];
            byte[] bufferEscritura;

            int lectura;
            while ((lectura = fis.read(bufferLectura, 0, 1024)) != -1) {  //volcamos al buffer hasta 1024 bytes mientras el byte no valga -1 y en lectura guarda el numero de bytes volcados             

                bufferEscritura = cifrador.update(bufferLectura, 0, lectura);//deciframos del buffer en numero de bytes (lectura) que hemos almacenado antes. Puede que en la ultima iteracion
                //no se hayan sobreescrito los 1024 por eso necesitamos la longitud (lectura) de los bytes a descifrar

                fos2.write(bufferEscritura);

            }

            bufferEscritura = cifrador.doFinal();
            fos2.write(bufferEscritura);

            fos2.close();

        } catch (IOException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ejercicios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

class BarajaEspañola {

    List<String> cartas = new ArrayList<>();

    public BarajaEspañola() {

        String[] tmp = {"AS_bastos", "REY_bastos", "CABALLO_bastos", "SOTA_bastos", "SIETE_bastos", "SEIS_bastos", "CINCO_bastos", "CUATRO_bastos", "TRES_bastos", "DOS_bastos",
                        "AS_copas", "REY_copas", "CABALLO_copas", "SOTA_copas", "SIETE_copas", "SEIS_copas", "CINCO_copas", "CUATRO_copas", "TRES_copas", "DOS_copas",
                        "AS_espadas", "REY_espadas", "CABALLO_espadas", "SOTA_espadas", "SIETE_espadas", "SEIS_espadas", "CINCO_espadas", "CUATRO_espadas", "TRES_espadas", "DOS_espadas",
                        "AS_oros", "REY_oros", "CABALLO_oros", "SOTA_oros", "SIETE_oros", "SEIS_oros", "CINCO_oros", "CUATRO_oros", "TRES_oros", "DOS_oros"       
                        };
        cartas.addAll(Arrays.asList(tmp));//añadimos las cartas a la lista

    }

    public String sacaUnaCarta(){
        
        
        
        
        
        
        
        
        return "";
        
    }
    
    
}
