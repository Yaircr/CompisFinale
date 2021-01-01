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
package com.eycr.regex;

import com.eycr.automaton.AFD;
import com.eycr.automaton.AFN;
import com.eycr.automaton.Converter;
import com.eycr.automaton.InterfaceAFD;
import com.eycr.automaton.InterfaceAFN;
//import com.eycr.lexicon.Analyzer;
import com.eycr.utilities.Const;
import java.util.Scanner;

public class ERAutomata {
    InterfaceAFD afd;
    /*
        Constructor que convierte un AFN a AFD
    */
    public ERAutomata()
    {
        InterfaceAFN f1=getHibrid2();
        
        
        Converter afnc=new Converter();
        afd=afnc.convertAFN(f1);
        //System.out.println(afd.analizeString("a"));
        //System.out.println(afd.analizeString("a+&(a|b)?&c"));
        //System.out.println(afd.analizeString("(a|b)?&9+&.&8+"));
    }

    public InterfaceAFD getAFD() {
        return afd;
    }

    public void setAFD(InterfaceAFD afd) {
        this.afd = afd;
    }
    
    private InterfaceAFN generate()
    {
        return getAllAutomata();
    }
    /*
        Método que obtiene 2 automatas y los junta en 1 solo
        @return 
    */
    private InterfaceAFN getHibrid1()
    {
        InterfaceAFN f1=getAllAutomata();
        InterfaceAFN f2=getAllAutomataWithPar();
        f1.addAFN(f2);
        return f1;
    }
    /*
        Método que obtiene 2 automatas (1 concatenado y otro con OR)
        @return Automata con | y & 
    */
     private InterfaceAFN getHibrid2()
    {
        InterfaceAFN f1=getHibrid1();
        InterfaceAFN f2=getHibrid1();
        
        
        
        AFN f3=new AFN('&');
        f3.associateToken(Const.CONC);
        AFN f4=new AFN('|');
        f4.associateToken(Const.OR);
        f3.addAFN(f4);
        f3.concatenateAFN(f2);
        
        f3.kleenClosure();
        f1.concatenateAFN(f3);
        return f1;
    }
     /*
        Método que obtiene el automata que tenga Parentesis izquierdo y parentesis derecho
        @return Automata con paréntesis y símbolos especiales
     */
    private InterfaceAFN getAllAutomataWithPar()
    {
        InterfaceAFN f1=getAllAutomata();
        AFN f2=new AFN('(');
        f2.associateToken(Const.PAR_I);
        AFN f3=new AFN(')');
        f3.associateToken(Const.PAR_D);
        f2.concatenateAFN(f1);
        f2.concatenateAFN(f3);
        
         /*AFN para SIMB(+|*|?)*/
        AFN f4=new AFN('+');
        f4.associateToken(Const.ADD);
        AFN f5=new AFN('*');
        f5.associateToken(Const.PROD);
        AFN f6=new AFN('?');
        f6.associateToken(Const.OPT);
        
        f4.addAFN(f5);
        f4.addAFN(f6);
        f4.optional();
        f2.concatenateAFN(f4);
        
        return f2;
    }
    /*
        Genera automatas básicos y devuelve la concatenación con estos.
        @return Automata concatenado con & y |
    */
    private InterfaceAFN getAllAutomata()
    {
        InterfaceAFN f1=generateBasicSimb();
        /*GENERAMOS LA SIGUIENTE OPCION*/
        InterfaceAFN f2=generateBasicSimb();
        AFN f3=new AFN('&');
        f3.associateToken(Const.CONC);
        AFN f4=new AFN('|');
        f4.associateToken(Const.OR);
        f3.addAFN(f4);
        f3.concatenateAFN(f2);
        f3.kleenClosure();
        //System.out.println(f3.toString());
        f1.concatenateAFN(f3);
        
        return f1;
    }
    /*
        Genera automatas para unirlos con 1 (básicos), también se crean automatas con valores numericos y alfabeticos
        Se concactena todo y devolvemos un AFN con todos los símbolos.
        @return Automata complejo de símbolos especiales, y letras, números y puntos.
    */
    private InterfaceAFN generateBasicSimb()
    {
         /*AFN para SIMB(+|*|?)*/
        AFN f1=new AFN('+');
        f1.associateToken(Const.ADD);
        AFN f2=new AFN('*');
        f2.associateToken(Const.PROD);
        AFN f3=new AFN('?');
        f3.associateToken(Const.OPT);
        
        f1.addAFN(f2);
        f1.addAFN(f3);
        f1.optional();
        /*UNIMOS TOOS LOS AFN DE FINAL*/
        AFN f4=new AFN('0','9');
        AFN f5=new AFN('A','Z');
        AFN f6=new AFN('a','z');
        AFN f7=new AFN('.');
        f4.addAFN(f5);
        f4.addAFN(f6);
        f4.addAFN(f7);
        f4.associateToken(Const.SIMB);
        f4.concatenateAFN(f1);
        
        return f4;
    }
}
