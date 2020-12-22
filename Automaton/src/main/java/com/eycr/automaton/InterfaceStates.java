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
public interface InterfaceStates
{
  public Boolean isFinal();
  public Collection<InterfaceStates> epsilonClosure();
  public Boolean addTransition(Transition t);
  public Collection<Transition> getTransitions();
  public Integer getId();
  public Integer getToken();
  public void setToken(Integer token);
  
}