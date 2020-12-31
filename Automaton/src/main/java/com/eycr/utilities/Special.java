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


import com.eycr.automaton.AFN;
import com.eycr.automaton.InterfaceAFN;
import com.eycr.automaton.InterfaceStates;
import com.eycr.automaton.State;
import com.eycr.automaton.Transition;
import java.util.HashMap;
import java.util.Iterator;

/*
    Clase para la creación del AFN Grande
*/
public class Special {
/*
    Metodo para los AFN y hacer uno solo
    @param HashMap<Integer,AFN> afns: Los AFN'S con los que se creara el AFN FINAL
*/
    public void unir(HashMap<Integer,AFN> afns)
    {
        Iterator it1=afns.values().iterator();
        
        InterfaceStates sIni=new State(false);
        InterfaceAFN newAFN=new AFN();
        newAFN.getStates().add((State)sIni);
               
        while(it1.hasNext())
        {
            InterfaceAFN afn=(InterfaceAFN)it1.next();
            newAFN.getStates().addAll(afn.getStates());
            newAFN.getAlpha().addAlpha(afn.getAlpha());
            newAFN.getAcceptedStates().addAll(afn.getAcceptedStates());
            Transition t=new Transition(Const.EPSILON,afn.getCurrentState());
            sIni.addTransition(t);
            
        }
        newAFN.setCurrentState(sIni);
        System.out.println("NUEVO AFN");    
        System.out.println(newAFN.toString());
        afns.clear();
        afns.put(newAFN.getId(),(AFN)newAFN);   
    }
}
