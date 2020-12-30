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

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Transition {

    private Character initialSymbol, lastSymbol;
    private Collection<InterfaceStates> nextStates;
    
    /*
        Inicializador por defecto
    */
    public void init() {
        nextStates = new HashSet<>();

    }

    /*
        Constructor de transiciones
        @param c
        @param nextStates
    */
    public Transition(Character c, Collection<InterfaceStates> nextStates) {
        init();
        if (nextStates != null) {
            this.nextStates = nextStates;
        }
        initialSymbol = c;
        lastSymbol = c;

    }
    
    /*
        Constructor
        @param c
        @param nextState
    */
    public Transition(Character c, InterfaceStates nextState) {
        init();
        initialSymbol = c;
        lastSymbol = c;
        nextStates.add(nextState);
    }
    
    /*
        Constructor
        @param initialSymbol
        @param lastSymbol
        @param nextStates
    */
    public Transition(Character initialSymbol, Character lastSymbol, Collection<InterfaceStates> nextStates) {
        init();
        this.initialSymbol = initialSymbol;
        this.lastSymbol = lastSymbol;
        if (nextStates != null) {
            this.nextStates = nextStates;
        }
    }

    /*
        Constructor
        @param initialSymbol
        @param lastSymbol
        @param nextState
    */
    public Transition(Character initialSymbol, Character lastSymbol, State nextState) {
        init();
        this.initialSymbol = initialSymbol;
        this.lastSymbol = lastSymbol;
        nextStates.add(nextState);
    }

    /*
        Agregar siguiente estado
        @param i
        @return boolean segun el resultado de agregar el estado
    */
    public Boolean addNextState(State i) {
        return nextStates.add(i);
    }

    /*
        Quitar un estado
        @param i
        @return boolean segun el resultado de quitar un estado
    */
    public Boolean removeState(Integer i) {
        return nextStates.remove(i);
    }

    /*
        Unir transiciones
        @param t2
        
    */
    public void joinTransition(Transition t2) {
        nextStates.addAll(t2.getNextStates());
    }

    /*
        Obtener simbolo inicial
    |   @return initialSymbol
    */
    public Character getInitialSymbol() {
        return initialSymbol;
    }
    /*
        Definir simbolo inicial 
        @param c
    */
    public void setInitialSymbol(Character c) {
        this.initialSymbol = c;
    }
    
    /*
        Reuperar simbolo final
        @return lastSymbol
    */
    public Character getLastSymbol() {
        return lastSymbol;
    }
    /*
        Definir simbolo final
        @param c
    */
    public void setLastSymbol(Character c) {
        this.lastSymbol = c;
    }
    /*
        Obtener siguientes estados
        @return nextStates
    */
    public Collection<InterfaceStates> getNextStates() {
        return nextStates;
    }
    /*
        Definir siguiente estado
        @param nextState
    */
    public void setNextStates(State nextState) {
        this.nextStates.add(nextState);
    }
    
    /*
        Convertir la transicion a string
        @return s
    */
    @Override
    public String toString() {
        String s = (initialSymbol == lastSymbol) ? initialSymbol.toString() : initialSymbol.toString() + ":" + lastSymbol.toString();
        s += "=[";
        for (InterfaceStates i : nextStates) {
            s += i + ",";
        }

        return s + "]";
    }
    
    /*
        Hashear
        @return hash
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.initialSymbol);
        hash = 41 * hash + Objects.hashCode(this.lastSymbol);
        return hash;
    }

    /*
        Verificar igualdad
        @param obj
        @return boolean
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
        final Transition other = (Transition) obj;
        if (!Objects.equals(this.initialSymbol, other.initialSymbol)) {
            return false;
        }
        if (!Objects.equals(this.lastSymbol, other.lastSymbol)) {
            return false;
        }
        return true;
    }
    
    /*
        Separar y agregar transiciones
        @param t
        @return boolean segun el resultado
    */
    public Boolean split(Transition t) {
        return nextStates.addAll(t.getNextStates());
    }

}
