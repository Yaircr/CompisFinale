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

import com.eycr.utilities.Const;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class State implements InterfaceStates {

    static Integer counter = 0;
    private Integer id;
    private Integer token;
    private Collection<Transition> transitions;
    private Boolean finalState;
    
    /*
        Recupera el token del estado
        @return token
    */
    public Integer getToken() {
        return token;
    }
    
    /*
        Define el token correspondiente al estado
        @param token
    */
    public void setToken(Integer token) {
        this.token = token;
    }

    /*
        Constructor para el estado
        @param id
        @param token
    */
    public State(Integer id, Integer token) {
        this.id = id;
        transitions = new ArrayList<>();

        this.finalState = (token != -1) ? true : false;
        this.token = token;
    }
    
    /*
        Constructor para el Estado
        @param id
        @param finalState
        @param token
    */
    public State(Integer id, Boolean finalState, Integer token) {
        this.id = id;
        transitions = new ArrayList<>();
        this.finalState = finalState;
        this.token = token;
    }
    
    /*
        Constructor para el estaado
        @param id   
        @param finalState
    */
    public State(Integer id, Boolean finalState) {
        this.id = id;
        transitions = new ArrayList<>();
        this.finalState = finalState;
        token = -1;
    }

    /*
        Constructor para el estado
        @param finalState
    */
    public State(Boolean finalState) {
        id = counter++;
        transitions = new ArrayList<>();
        this.finalState = finalState;
        token = -1;
    }

    /*
        Define un estado como final
    */
    @Override
    public Boolean isFinal() {
        return finalState;
    }
    
    /*
        Agregar transiciones del estado
        @param c
        @param nextState
        @return true cuando se agrega
    */
    public Boolean addTransition(Character c, Integer nextState) {
        return true;
    }

    /*
        Agrega transiciones
        @param t
        @return false o true si se pudo agregar
    */
    public Boolean addTransition(Transition t) {
        if (!transitions.contains(t)) {
            return transitions.add(t);
        } else {
            for (Transition itT : transitions) {
                if (itT.equals(t)) {
                    return itT.split(t);
                }
            }
        }
        return false;
    }

    /*
        Agrega transiciones
        @param t
        @return boolean de agregar transiciones en coleccion
    */
    public Boolean addTransitions(Collection t) {
        return transitions.addAll(t);
    }

    /*
        Cerradura epsilon
        @return de agregar las transiciones
    */
    @Override
    public Collection<InterfaceStates> epsilonClosure() {
        Collection<InterfaceStates> c = null;
        c = new HashSet<>();
        c.add(this);
        Collection<Transition> transitions = this.getTransitions();
        for (Transition transition : transitions) {
            if (transition.getInitialSymbol() == Const.EPSILON.charValue()) {
                //System.out.println(transition.getNextStates());
                c.addAll(transition.getNextStates());
            }
        }
        return c;
    }
    
    /*
        Obtener todas las transiciones
        @return coleccion de transiciones
    */
    @Override
    public Collection<Transition> getTransitions() {
        return transitions;
    }
    
    /*
        Recuperar el ID
        @return id
    */
    public Integer getId() {
        return id;
    }
    
    /*
        establece las transiciones
        @param transitions
    */
    public void setTransitions(Collection<Transition> transitions) {
        this.transitions = transitions;
    }
    
    /*
        recuperar estado final
        @return finalState boolean
    */
    public Boolean getFinalState() {
        return finalState;
    }

    /*
        Establecer un estado final
        @param finalState
    */
    public void setFinalState(Boolean finalState) {
        this.finalState = finalState;
    }

    /*
        Obtener contador de iteraciones sobre ese estado
        @return counter
    */
    public static Integer getCounter() {
        return counter;
    }

    /*
        Definir contador
        @param counter
    */
    public static void setCounter(Integer counter) {
        State.counter = counter;
    }

    /*
        Hashear
        @return hash
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /*
        Convertir id a string
        @return id
    */
    @Override
    public String toString() {
        return "" + id;
    }

    /*
        Verifica igualdad
        @param obj
        @return true o false
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

    /*
        Verifica si tiene transiciones
        @param c
        @return objeto de InterfaceStates
    */
    public InterfaceStates hastransition(Character c) {
        for (Transition t : transitions) {
            if (t.getInitialSymbol().equals(c)) {
                return t.getNextStates().iterator().next();
            }
        }
        return null;
    }
    
    /*
        Describe el estado
        @return info con la descripcion del estado
    */
    public String description() {
        String info = "State " + id + ":\n";
        Iterator<Transition> it = transitions.iterator();
        while (it.hasNext()) {
            info += it.next().toString() + "\n";
        }
        info += "Final state:" + finalState + "\n";
        return info;
    }
}
