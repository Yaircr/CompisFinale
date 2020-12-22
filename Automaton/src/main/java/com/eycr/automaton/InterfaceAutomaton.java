/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.automaton;

/**
 *
 * @author firem
 */
public interface InterfaceAutomaton{
    
    public void positiveClosure();
    public void kleenClosure();
    public Boolean analizeString(String string);
    
}
