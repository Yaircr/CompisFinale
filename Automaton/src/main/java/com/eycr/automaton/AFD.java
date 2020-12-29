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

import com.eycr.utilities.StateHandlers;
import java.util.Collection;
import java.util.Iterator;

public class AFD implements InterfaceAFD{

    private StateHandlers acceptedStates;
    private StateHandlers states;
    private InterfaceStates currentState;
    private InterfaceStates initialState;
    private Alpha alphabet;
    private TableAFD afdTable;

    public TableAFD getTable() {
        return afdTable;
    }

    public void setTableAFD(TableAFD afdTable) {
        this.afdTable = afdTable;
    }

    public AFD(StateHandlers acceptedStates, StateHandlers states, InterfaceStates currentState, InterfaceStates initialState, Alpha alphabet) {
        this.acceptedStates = acceptedStates;
        this.states = states;
        this.currentState = currentState;
        this.initialState = initialState;
        this.alphabet = alphabet;
        afdTable=new TableAFD(this);
        
    }
    
    public AFD(StateHandlers sc,Alpha alphabet)
    {
        init();
        this.alphabet=alphabet;
        Iterator it=sc.iterator();
        while(it.hasNext())
        {
            State statep=(State)it.next();
            if(statep.isFinal())
            {
                acceptedStates.add(statep);
            }
            states.add(statep);
        }
        currentState=(State)sc.get(0);
        afdTable=new TableAFD(this);
    }
     

    AFD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public Collection<State> getAcceptedStates() {
        return acceptedStates;
    }

    public void setAcceptedStates(StateHandlers acceptedStates) {
        this.acceptedStates = acceptedStates;
    }

    public Collection<State> getStates() {
        return states;
    }

    public void setStates(StateHandlers states) {
        this.states = states;
    }

    public InterfaceStates getCurrentState() {
        return currentState;
    }

    public void setCurrentState(InterfaceStates currentState) {
        this.currentState = currentState;
    }

    public InterfaceStates getInitialState() {
        return initialState;
    }

    public void setInitialState(InterfaceStates initialState) {
        this.initialState = initialState;
    }

    public Alpha getAlpha() {
        return alphabet;
    }

    public void setAlpha(Alpha alphabet) {
        this.alphabet = alphabet;
    }
    
    
   
    @Override
    public void positiveClosure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void kleenClosure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean analizeString(String string) {
         Integer actualToken=-1;
         //Integer nextState=currentState.getId();
         Integer nextState=0;
         for (int i = 0; i < string.length(); i++)
         {
             
            nextState=afdTable.nextState(nextState,string.charAt(i));
            currentState=(InterfaceStates)states.get(nextState);
           
            if(nextState==-1)
            {   
                actualToken=-1;
                break;
            }
             //System.out.println("Estado "+nextState+" con "+string.charAt(i)+" y token "+currentState.getToken());
            if((actualToken=currentState.getToken())!=-1)
            {
                //System.out.println("TOKEN:"+currentState.getToken());
            }
            
        }
         
         return (actualToken!=-1)?true:false;
    }
    private void init()
    {
        acceptedStates=new StateHandlers();
        states=new StateHandlers();
        //currentState=new State(false);
        alphabet=new Alpha();
    }
    
    @Override
    public String toString()
    {
        String info="AUTOMATA::\n";
        afdTable.print();
        info+="--ALPHABET--\n";
        info+=alphabet.getAlpha()+"\n";
        info+="--STATES--\n";
        Iterator<State> statesIt=this.states.iterator();
        while(statesIt.hasNext())
        {
            info+=statesIt.next().description();
        }
        info+="--CURRENT STATE--\n";
        info+=currentState.toString()+"\n";
        info+="--FINAL STATES--\n";
        Iterator<State> acc=this.acceptedStates.iterator();
        while(acc.hasNext())
        {
            info+=acc.next().getId()+",";
        }
        return info;
    }
    
}
