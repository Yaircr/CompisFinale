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

public interface InterfaceAFN extends InterfaceAutomaton
{
    public void optional();
    public void concatenateAFN(InterfaceAFN automata);
    public void addAFN(InterfaceAFN automata);
    public Collection<State> getStates();
    public Alpha getAlpha();
    public Collection<InterfaceStates> epsilonClausure(Collection<InterfaceStates> states);
    public Collection<InterfaceStates> epsilonClausure(InterfaceStates state);
    public Collection<InterfaceStates> goTo(Collection<InterfaceStates> states, Character symbol);
    public Collection<InterfaceStates> goTo(InterfaceStates state, Character symbol);
    public InterfaceStates getCurrentState();
    public Collection<State> getAcceptedStates();
    public void createBasic(Character symbol);
    public void createBasic(Character a,Character b);
    public void associateToken(Integer token);
    public void setCurrentState(InterfaceStates state);
    public Integer getId();
}
