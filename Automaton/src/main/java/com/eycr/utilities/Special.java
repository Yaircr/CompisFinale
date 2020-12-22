/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.utilities;


import com.eycr.automaton.AFN;
import com.eycr.automaton.InterfaceAFN;
import com.eycr.automaton.InterfaceStates;
import com.eycr.automaton.State;
import com.eycr.automaton.Transition;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author firem
 */
public class Special {
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
