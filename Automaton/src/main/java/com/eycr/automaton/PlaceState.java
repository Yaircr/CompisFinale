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
package com.eycr.automaton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class PlaceState {
    private Collection<InterfaceStates> states;
    private int id;
    private boolean initial;
    private boolean accepted;
    private boolean analyzed;
    private Collection<Transition> transitions;

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
    
    public Collection<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Collection<Transition> transitions) {
        this.transitions = transitions;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        //id++;
        this.id = id;
    }

    public PlaceState(Collection<InterfaceStates> states) {
        this.states = states;
        transitions=new ArrayList<>();
        updateAcceptedState();
    }

    public PlaceState(Collection<InterfaceStates> states, boolean initial, boolean accepted, boolean analyzed, int id) {
        this.states = states;
        this.initial = initial;
        this.accepted = accepted;
        this.analyzed = analyzed;
        this.id=id;
        transitions=new ArrayList<>();
        updateAcceptedState();
    }

    public Collection<InterfaceStates> getStates() {
        return states;
    }

    public void setStates(Collection<InterfaceStates> states) {
        this.states = states;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.states);
        hash = 67 * hash + this.id;
        hash = 67 * hash + (this.initial ? 1 : 0);
        hash = 67 * hash + (this.accepted ? 1 : 0);
        hash = 67 * hash + Objects.hashCode(this.transitions);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        //System.out.println("Holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final PlaceState other = (PlaceState) obj;
        int num=other.getStates().size();
        if(num!=this.states.size()){
            return false;
        }
        for(InterfaceStates state2:other.getStates()){
            for(InterfaceStates state: this.states){
                if(state.equals(state2)){
                    num--;
                }
            }
        }
        if(num!=0){
            return false;
        }
        /*if (this.id != other.id) {
            return false;
        }
        if (this.initial != other.initial) {
            return false;
        }
        if (this.accepted != other.accepted) {
            return false;
        }
        if (!Objects.equals(this.states, other.states)) {
            return false;
        }*/
        
        /*if (!Objects.equals(this.transitions, other.transitions)) {
            return false;
        }*/
        return true;
    }
   
    
    @Override
    public String toString()
    {
        String info="PlaceStateId="+id+"\n"; 
        info+="Initial="+initial+":\n";
        info+="Final state:"+accepted+"\n";
        //info+="Analysed:"+analyzed+"\n";
        info+="=>Set states:"+"\n";
        for(InterfaceStates state: states){
            info+=state.getId();
            info+="\n";
        }
        info+="=>Transitions:\n";
        Iterator<Transition> it=transitions.iterator();
        while(it.hasNext())
            info+=it.next().toString()+"\n";
        return info;
    }
    
    public void updateAcceptedState(){
        for(InterfaceStates state: states){
            if(state.isFinal()){
                accepted=true;
                break;
            }
        }
    }
}
