/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.automaton;

import com.eycr.utilities.StateHandlers;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author firem
 */
public class Converter {
    public AFD convertAFN(InterfaceAFN afn)
    {
        int i=0;
        StateHandlers sc=new StateHandlers();
        ArrayList<Collection> scAL=new ArrayList<Collection>();
        scAL.add(afn.epsilonClausure(afn.getCurrentState()));
        InterfaceStates s=new State(i,false);
        sc.add(s);
        
        while(i<scAL.size())
        {
            for(Character c:afn.getAlpha().getSymbols())
            {
                Collection col=afn.goTo(afn.epsilonClausure(scAL.get(i)), c);
                if(!scAL.contains(col) && !col.isEmpty())
                {
                    s=new State(scAL.size(),hasFinalState(col,afn));
                    sc.add(s);
                    scAL.add(col);
                    Transition t=new Transition(c,s);
                    InterfaceStates state=(InterfaceStates)sc.get(i);
                    state.addTransition(t);                    
                }
                else if(!col.isEmpty())
                {
                    s=(InterfaceStates)sc.get(scAL.indexOf(col));
                    Transition t=new Transition(c,s);
                    InterfaceStates state=(InterfaceStates)sc.get(i);
                    state.addTransition(t);
                }
            }
            i++;
        }
        AFD afd=new AFD(sc,afn.getAlpha());
        return afd;
    }
    public Integer hasFinalState(Collection s,InterfaceAFN afn)
    {
        for(InterfaceStates state:afn.getStates())
        {
            if(s.contains(state) && state.getToken()!=-1)
            {
                return state.getToken();
            } 
        }
        return -1;
    }
}
