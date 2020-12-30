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

/*
    Interfaz para el Automata Finito Determinista
    Incluye los metodos a usar para generacion y manejo, se construye a partir de estados y su alfabeto
    @version 2.0
    @throws UnsopportedOperationException en caso de que se solicite que la interfaz realice alguna operacion que no este codificada
    
*/
public class AFD implements InterfaceAFD{

    private StateHandlers acceptedStates;
    private StateHandlers states;
    private InterfaceStates currentState;
    private InterfaceStates initialState;
    private Alpha alphabet;
    private TableAFD afdTable;

    /*
        @return Tabla de AFD
    */
    public TableAFD getTable() {
        return afdTable;
    }

    /*
        @param Tabla de AFD a partir de un objeto
        @see TableAFD
    */
    public void setTableAFD(TableAFD afdTable) {
        this.afdTable = afdTable;
    }
    
    /*
        Constructor para el AFD a partir de los siguientes parametros
        @param acceptedStates: estados de aceptacion
        @param states: todos los posibles estados de transicion
        @see utilities/StateHandlers
    
        @param currentState: el estado donde se encuentra el analizador que invoca el metodo
        @param initialState: estado inicial del AFD
        @see InterfaceStates
    
        @param alphabet: el alfabeto correspondiente al AFD
        @see Alpha
        
        @return No hay
        
    */
    public AFD(StateHandlers acceptedStates, StateHandlers states, InterfaceStates currentState, InterfaceStates initialState, Alpha alphabet) {
        this.acceptedStates = acceptedStates;
        this.states = states;
        this.currentState = currentState;
        this.initialState = initialState;
        this.alphabet = alphabet;
        afdTable=new TableAFD(this);
        
    }
    
    /*
        Segundo posible constructor para un AFD
        @param sc: manejador de estados
        @see StateHandlers
        @param alphabet
        @see Alpha
    */
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
     
    /*
        Constructor vacio
    */
    AFD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
        @return acceptedStates estados aceptados en formato de coleccion
    */
    public Collection<State> getAcceptedStates() {
        return acceptedStates;
    }
    
    /*
        @param acceptedStates: estados de aceptacion a configurar en nuestro AFD
        @see StateHandlers
    */
    public void setAcceptedStates(StateHandlers acceptedStates) {
        this.acceptedStates = acceptedStates;
    }
    
    /*
        @return states todos los estados en la coleccion
    */
    public Collection<State> getStates() {
        return states;
    }
    
    /*
        @param states establece los estados del AFD
    */
    public void setStates(StateHandlers states) {
        this.states = states;
    }
    
    /*
        @return currentState estado actual donde se pueda encontrar el AFD
    */
    public InterfaceStates getCurrentState() {
        return currentState;
    }
    
    /*
        @param currentState 
    */
    public void setCurrentState(InterfaceStates currentState) {
        this.currentState = currentState;
    }

    /*
        @return initialState estado inicial para el analisis
    */
    public InterfaceStates getInitialState() {
        return initialState;
    }

    /*
        @param initialState establece el estado inicial
    */
    public void setInitialState(InterfaceStates initialState) {
        this.initialState = initialState;
    }

    /*
        @return alphabet regresa el alfabeto perteneciente al AFD
    */
    public Alpha getAlpha() {
        return alphabet;
    }
    
    /*
        @param alphabet define el alfabeto a usar por el AFD
    */
    public void setAlpha(Alpha alphabet) {
        this.alphabet = alphabet;
    }
    
    
    /* ESTO NO LO COMENTE PORQUE NO SE USA PARA NADA*/
    @Override
    public void positiveClosure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void kleenClosure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*
        Analiza si una cadena pertenece al AFD
        @param string
        @return true o false si pertenece al AFD
    */
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
    /*
        Inicializa un AFD completamente vacio
    */
    private void init()
    {
        acceptedStates=new StateHandlers();
        states=new StateHandlers();
        //currentState=new State(false);
        alphabet=new Alpha();
    }
    
    /*
        Regresa el AFD en formato de cadena
        @return una cadena con la sintaxis de 
        AUTOMATA::
        (TABLA AFD)
        --STATES--
        (ESTADOS)
        --CURRENT STATE--
        (ESTADO ACTUAL)
        --FINAL STATES--
        (ESTADOS FINALES DE ACEPTACION)
    */
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
