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