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

/*
    Manejador del posicionamiento de los estados
*/

public class PlaceState {
    private Collection<InterfaceStates> states;
    private int id;
    private boolean initial;
    private boolean accepted;
    private boolean analyzed;
    private Collection<Transition> transitions;
    
    /*
        Agregar transicion
        @param t Transicion
        @see Transition
        @return true o false segun se pudo agregar
    */
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
    
    /*
        Obtener las transiciones
        @return transitions
    */
    public Collection<Transition> getTransitions() {
        return transitions;
    }

    /*
        Establece las transiciones
        @param transitions
    */
    public void setTransitions(Collection<Transition> transitions) {
        this.transitions = transitions;
    }
    
    /*
        Recupera el id de la transicion
        @return id
    */
    public int getId() {
        return id;
    }

    /*
        Establece el id de la transicion
        @param id
    */
    public void setId(int id) {
        
        this.id = id;
    }

    /*
        Constructor mediante coleccion
        @param states
    */
    public PlaceState(Collection<InterfaceStates> states) {
        this.states = states;
        transitions=new ArrayList<>();
        updateAcceptedState();
    }

    /*
        Constructor mediante parametros individuales mas detallados
        @param states
        @param initial
        @param accepted
        @param analyzed
        @param id
    */
    public PlaceState(Collection<InterfaceStates> states, boolean initial, boolean accepted, boolean analyzed, int id) {
        this.states = states;
        this.initial = initial;
        this.accepted = accepted;
        this.analyzed = analyzed;
        this.id=id;
        transitions=new ArrayList<>();
        updateAcceptedState();
    }

    /*
        Recuperar los estados
        @return states
    */
    public Collection<InterfaceStates> getStates() {
        return states;
    }

    /*
        Establecer los estados
        @param states
    */
    public void setStates(Collection<InterfaceStates> states) {
        this.states = states;
    }

    /*
        es un estado inicial?
        @return initial boolean
    */
    public boolean isInitial() {
        return initial;
    }

    /*
        define si es inicial
        @param initial
    */
    public void setInitial(boolean initial) {
        this.initial = initial;
    }
    
    /*
        es estado de aceptacion?
        @return accepted
    */
    public boolean isAccepted() {
        return accepted;
    }

    /*
        Define estado de aceptacion
        @param accepted boolean
    */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    /*
        se analizo?
        @return analyzed
    */
    public boolean isAnalyzed() {
        return analyzed;
    }

    /*
        Determina el estado como analizado
        @param analyzed boolean
    */
    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }
    
    /*
        Poner codigo Hash
        @return hash
    */
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

    /*
        Es igual?
        @param obj
        @return true o false segun el resultado
    */    
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
   
    /*
        Convertir el listado de propiedades del estado a cadena
        @return info
    */
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
    
    /*
        actualizar estado de aceptacion si es un estado final
    */
    public void updateAcceptedState(){
        for(InterfaceStates state: states){
            if(state.isFinal()){
                accepted=true;
                break;
            }
        }
    }
}
