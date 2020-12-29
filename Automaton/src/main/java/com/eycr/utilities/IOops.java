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

public class IOops {
    Scanner sc;
    public IOops()
    {
        sc=new Scanner(System.in);
    }
    public Integer askForToken()
    {
        System.out.println("Give me a token please. If you dont have put -1");
        return sc.nextInt();
    }
    public String askForString()
    {
        System.out.println("Give a string ");
        return sc.next();
    }
    
}