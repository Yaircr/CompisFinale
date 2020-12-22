/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.automaton;

import com.eycr.utilities.Const;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author firem
 */
public class State implements InterfaceStates {
  static Integer counter=0;
  private Integer id;
  private Integer token;
  private Collection<Transition> transitions;
  private Boolean finalState;

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }
  
    public State(Integer id,Integer token)
    {
        this.id=id;
        transitions=new ArrayList<>();
        
        this.finalState=(token!=-1)?true:false;
        this.token=token;
    }
    public State(Integer id,Boolean finalState,Integer token)
    {
        this.id=id;
        transitions=new ArrayList<>();
        this.finalState=finalState;
        this.token=token;
    }
    public State(Integer id,Boolean finalState)
    {
        this.id=id;
        transitions=new ArrayList<>();
        this.finalState=finalState;
        token=-1;
    }
    public State(Boolean finalState)
    {
        id=counter++;
        transitions=new ArrayList<>();
        this.finalState=finalState;
        token=-1;
    }
    @Override
    public Boolean isFinal()
    {
        return finalState;
    }
    public Boolean addTransition(Character c,Integer nextState)
    {
        return true;
    }
    public Boolean addTransition(Transition t)
    {
        if(!transitions.contains(t))
            return transitions.add(t);
        else
        {
            for(Transition itT:transitions)
            {
                if(itT.equals(t))
                    return itT.split(t);
            }
        }
        return false;
    }
    public Boolean addTransitions(Collection t)
    {
        return transitions.addAll(t);
    }
    @Override
    public Collection<InterfaceStates> epsilonClosure() {
        Collection<InterfaceStates> c=null;
        c=new HashSet<>();
        c.add(this);
        Collection<Transition> transitions = this.getTransitions();
        for(Transition transition: transitions){
            if(transition.getInitialSymbol() == Const.EPSILON.charValue()){
                //System.out.println(transition.getNextStates());
                c.addAll(transition.getNextStates());
            }
        }
        return c;
    }

    @Override
    public Collection<Transition> getTransitions() {
        return transitions;
    }

    public Integer getId() {
        return id;
    }

    /*public void setId(Integer id) {
        this.id = id;
    }*/

    public void setTransitions(Collection<Transition> transitions) {
        this.transitions = transitions;
    }

    public Boolean getFinalState() {
        return finalState;
    }

    public void setFinalState(Boolean finalState) {
        this.finalState = finalState;
    }

    public static Integer getCounter() {
        return counter;
    }

    public static void setCounter(Integer counter) {
        State.counter = counter;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString()
    {
      return ""+id;   
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.transitions, other.transitions)) {
            return false;
        }
        if (!Objects.equals(this.finalState, other.finalState)) {
            return false;
        }
        return true;
    }
    
    public InterfaceStates hastransition(Character c){
        for(Transition t : transitions){
            if(t.getInitialSymbol().equals(c)){   
                return t.getNextStates().iterator().next();
            }
        }
        return null;
    }
    public String description()
    {
        String info="State "+id+":\n";
        Iterator<Transition> it=transitions.iterator();
        while(it.hasNext())
            info+=it.next().toString()+"\n";
        info+="Final state:"+finalState+"\n";
        return info;
    }
}
