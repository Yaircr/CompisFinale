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

import com.eycr.automaton.AFN;
import com.eycr.automaton.Converter;
import com.eycr.automaton.InterfaceAFD;
import com.eycr.automaton.InterfaceAFN;
import com.eycr.utilities.Const;

public class ERAutomataEnhanced_TESTING {
    InterfaceAFD afd;
    public ERAutomataEnhanced_TESTING()
    {
        AFN f1=new AFN('+');
        f1.associateToken(Const.ADD);
        AFN f2=new AFN('*');
        f2.associateToken(Const.PROD);
        AFN f3=new AFN('?');
        f3.associateToken(Const.OPT);
        
        AFN f4=new AFN('0','9');
        AFN f5=new AFN('A','Z');
        AFN f6=new AFN('a','z');
        AFN f7=new AFN('.');
        f4.addAFN(f5);
        f4.addAFN(f6);
        f4.addAFN(f7);
        f4.associateToken(Const.SIMB);
        
        AFN f8=new AFN('&');
        f8.associateToken(Const.CONC);
        AFN f9=new AFN('|');
        f9.associateToken(Const.OR);
        
        
         AFN f10=new AFN('(');
        f10.associateToken(Const.PAR_I);
        AFN f11=new AFN(')');
        f11.associateToken(Const.PAR_D);
        
        
        AFN f12=new AFN('[');
        f12.associateToken(Const.COR_I);
        AFN f13=new AFN(']');
        f13.associateToken(Const.COR_D);
        AFN f14=new AFN('-');
        f14.associateToken(Const.GUI);
        
        f1.addAFN(f2);
        f1.addAFN(f3);
        f1.addAFN(f4);
        f1.addAFN(f8);
        f1.addAFN(f9);
        f1.addAFN(f10);
        f1.addAFN(f11);
        f1.addAFN(f12);
        f1.addAFN(f13);
        f1.addAFN(f14);
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
