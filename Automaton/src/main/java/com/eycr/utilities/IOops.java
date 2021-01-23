/*
                    INSTITUTO POLITECNICO NACIONAL
                     ESCUELA SUPERIOR DE COMPUTO
                            COMPILADORES
                           PROYECTO FINAL
                                3CM6
                              ALUMNOS:
                    CUELLAR RIVERA EDUARDO YAIR
                     GARCIA VERA JARED ALBERTO
                     HERNANDEZ MEJIA DIEGO YAIR
                                
*/
package com.eycr.utilities;

import java.util.Scanner;
/*
    Clase para pedir entradas de consola  
*/
public class IOops {
    Scanner sc;
    
    /*
        Constructor para opciones de Inputs/OUTPUTS
        Creacion de un Scanner
    */    
    public IOops()
    {
        sc=new Scanner(System.in);
    }
    
    /*
        Metodo para pedir un Token
        @return sc scanner de entrada
        
    */    
    public Integer askForToken()
    {
        System.out.println("Indique el token, si no cuenta con uno, de -1");
        return sc.nextInt();
    }
    
    /*
        Metodo para pedir una String
        @return sc scanner de entrada
        
    */    
    public String askForString()
    {
        System.out.println("Introduzca cadena");
        return sc.next();
    }
    
}