/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared
 */
public class readFile {
    
    String dir;
    File archivo;
    FileReader fr;
    BufferedReader br;
    List<String> renglones;
    
    public readFile(){
        archivo = null;
        fr = null;
        br = null;
        dir = "C:\\Users\\Jared\\Documents\\Compiladores\\Proyecto final\\CompisFinale\\Automaton\\Expresiones y gramaticas\\";
        renglones = new ArrayList<String>();
    }
    
    public List lineasArchivoLista(String path){
        
        try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         dir = dir + path;
         archivo = new File (dir);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null)
            renglones.add(linea);
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
        return renglones;
    }
    
    public void lineasArchivo(String path){
        try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         dir = dir + path;
         archivo = new File (dir);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null)
            System.out.println(linea);
        }
        catch(Exception e){
           e.printStackTrace();
        }finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
    }
}
