/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.calculadora;

import com.eycr.automaton.AFN;
import com.eycr.automaton.Converter;
import com.eycr.automaton.InterfaceAFD;
import com.eycr.utilities.Const;

/**
 *
 * @author diego
 */
public class ERAutomataCalculadora {
    InterfaceAFD afd;
    /*
        Constructor que crea un automata utilizando todos los símbolos y operaciones que se le puedan aplicar, así mismo numeros y abecedario (mayus y minus)
        @return No hay
    */
    public ERAutomataCalculadora()
    {
        AFN f1=new AFN('+');
        f1.associateToken(constCalculadora.ADD);
        AFN f2=new AFN('x');
        f2.associateToken(constCalculadora.MUL);
        AFN f15=new AFN('/');
        f15.associateToken(constCalculadora.DIV);  
        AFN f16 = new AFN('*');
        f16.associateToken(constCalculadora.PROD);
        
        AFN f4=new AFN('0','9');    
        AFN f7=new AFN('.');      
        f4.addAFN(f7);
        f4.associateToken(constCalculadora.SIMB);
        
        AFN f8=new AFN('&');
        f8.associateToken(Const.CONC);
       
        
        
         AFN f10=new AFN('(');
        f10.associateToken(constCalculadora.PAR_I);
        AFN f11=new AFN(')');
        f11.associateToken(constCalculadora.PAR_D);                               
        AFN f14=new AFN('-');
        f14.associateToken(constCalculadora.MIN);
        
        f1.addAFN(f2);        
        f1.addAFN(f4);
        f1.addAFN(f8);        
        f1.addAFN(f10);
        f1.addAFN(f11);       
        f1.addAFN(f14);
        f1.addAFN(f15);
        Converter afnConverter=new Converter();
        afd= afnConverter.convertAFN(f1);
        
    }

    public InterfaceAFD getAfd() {
        return afd;
    }

    public void setAfd(InterfaceAFD afd) {
        this.afd = afd;
    }
    
    
}
