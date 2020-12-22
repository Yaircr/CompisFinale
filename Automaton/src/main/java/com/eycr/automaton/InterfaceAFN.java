/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.automaton;

import java.util.Collection;

/**
 *
 * @author firem
 */
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
