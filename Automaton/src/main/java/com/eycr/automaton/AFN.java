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
import com.eycr.utilities.Const;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public class AFN implements InterfaceAFN{
    private Collection<State> acceptedStates;
    private Collection<State> states;
    private InterfaceStates currentState;
    private Alpha alphabet;
    static Integer counterAFN=0;
    private Integer idAFN;
    
    /*
        @return counterAFN contador
    */
    public static Integer getCounterAFN() {
        return counterAFN;
    }
    
    /*
        @param counterAFN establece el indice del contador
    */
    public static void setCounterAFN(Integer counterAFN) {
        AFN.counterAFN = counterAFN;
    }
    
    /*
        Constructor para el AFN basico con dos caracteres
        @param c
        @param c0
    */
    public AFN(char c, char c0) {
        init();
        createBasic(c,c0);
    }
    
    /*
        Metodo para crear un AFN basico con sus estados y transiciones de a -> b
        Con estado actual en el primero y dejando como aceptacion el segundo
        @param a
        @param b
    */
    public void createBasic(Character a,Character b)
    {
        for (int i = a; i <= b; i++)
        {
            alphabet.addElement((char) i);
        }
        
        State state1=new State(false);
        State state2=new State(true);
        Transition t=new Transition(a,b,state2);
        state1.addTransition(t);
        currentState=state1;
        states.add(state1);
        states.add(state2);
        acceptedStates.add(state2);
    }
    
    /*
        Regresa el id del AFN
        @return idAFN
    */
    public Integer getId() {
        return idAFN;
    }
    
    /*
        Establece el id del AFN
        @param idAFN
    */
    public void setIdAFN(Integer idAFN) {
        this.idAFN = idAFN;
    }
    
    /*
        Inicializa un AFN en blanco
    */
    private void init()
    {
        idAFN=counterAFN++;
        acceptedStates=new StateHandlers();
        states=new StateHandlers();
        //currentState=new State(false);
        alphabet=new Alpha();
    }
    /*
        Constructor basico de inicializacion
    */
    public AFN()
    {
        init();
    }
    
    /*
        Genera un automata simple a partir de un solo simbolo
        Se agrega un simbolo al alfabeto, se crean estados inicial y final y se crea la transicion correspondiente
        @param symbol
    */
    public AFN(Character symbol)
    {
        
        init();
        createBasic(symbol);
        
        
    }
    /*
        Constructor de AFN con simbolo y token
        @param symbol
        @param token
    */
    public AFN(Character symbol,Integer token)
    {
        init();
        alphabet.addElement(symbol);
        State state1=new State(false);
        State state2=new State(true);
        state2.setToken(token);
        Transition t=new Transition(symbol,state2);
        state1.addTransition(t);
        currentState=state1;
        states.add(state1);
        states.add(state2);
        acceptedStates.add(state2);
        currentState=state1;
    }
    
    /*
        Asociar token un con un elemento de aceptacion del AFN
        @param token
    */
    public void associateToken(Integer token)
    {
        for(InterfaceStates s:acceptedStates)
        {
            s.setToken(token);
        }
    }
    
    /*
        Operador opcional
        Crea una transicion opcional mediante transiciones epsilon
    */
    @Override
    public void optional() {
        State newInitialState = new State(false);
        State newFinalState = new State(true);
        Transition transition = new Transition(Const.EPSILON, ((State)currentState));
        newInitialState.addTransition(transition);
        this.setInitialState(newInitialState);
        addAcceptedState(this, newFinalState);
        transition = new Transition(Const.EPSILON, newFinalState);
        currentState.addTransition(transition);
        states.add(newInitialState);
        states.add(newFinalState);
    }

    /*
        Concatenar dos AFN
        Une el alfabeto de dos automatas, elige el estado final del automata en caso de que haya mas de uno.
        Quita el caracter del estado final que corresponda al estado anterior de aceptacion
        Agrega una transicion al nuevo estado final y copia las transiciones del estado inicial del primer automata
        Elimina el estado inicial del segundo automata, se agregan los nuevos estados del automata recibido y se configuran los estados finales
        @param automata
    */
    @Override
    public void concatenateAFN(InterfaceAFN automata) {
        Integer selected;  
        State finalState1;
        AFN af=(AFN)automata;
        this.getAlpha().addAlpha(af.getAlpha());
        if(this.acceptedStates.size()>1)
        {
            selected=selectFinalState();
            finalState1=null;
        }
        else
            finalState1=acceptedStates.iterator().next();
        finalState1.setFinalState(false);
        acceptedStates.remove(finalState1);
        finalState1.addTransitions(af.getCurrentState().getTransitions());
        af.getStates().remove(af.currentState);
        this.states.addAll(af.getStates());
        acceptedStates.addAll(af.getAcceptedStates());
        states.add(finalState1);
    }
    /*
        Elige el estado final con un valor de -1
    */
    public Integer selectFinalState()
    {
        return -1;
    }
    
    /*
        Union por Thompson
        @param automata
        Unir los alfabetos de ambos idiomas
        Crear nuevos estados iniciales y finales.
        Crear transiciones epsilon de inicial nuevo al original
        Crear transiciones epsilon de final original al nuevo
    */
    @Override
    public void addAFN(InterfaceAFN automata) {
        
        AFN af=(AFN)automata;
        this.alphabet.addAlpha(af.getAlpha());
        State newIniState=new State(false);
        State newFinalState=new State(true);
        states.add(newIniState);
        states.add(newFinalState);
        State s=(State)currentState;
        System.out.println("This: " +s);
        State s2=(State)af.currentState;
        System.out.println("afn: " +s2);
        Collection<State> states2=af.getStates();
        states.addAll(states2);
        Transition t1=new Transition(Const.EPSILON,s);
        Transition t2=new Transition(Const.EPSILON,s2);
        newIniState.addTransition(t1);
        newIniState.addTransition(t2);
        currentState=newIniState;
        addAcceptedState(this,newFinalState);
        addAcceptedState(af,newFinalState);
        
        //acceptedStates.clear();
        //acceptedStates.add(newFinalState);
    }
    
    /*
        Agregar estados de aceptacion
        
    */
    public void addAcceptedState(AFN afn,State newFinalState)
    {
        Iterator<State> its1=afn.getAcceptedStates().iterator();
        while(its1.hasNext())
        {
            State ss=its1.next();
            ss.addTransition(new Transition(Const.EPSILON,newFinalState));
            ss.setFinalState(false);
        }
        afn.acceptedStates.clear();
        afn.acceptedStates.add(newFinalState);

    }
    
    
    /*
        Cerradura Positiva
        Crear nuevos estados inicial y final
        Crear transiciones epsilon de inicial nuevo al original
        Crear transiciones epsilon de final original al nuevo
    */
    @Override
    public void positiveClosure() {
        State newInitialState = new State(false);
        State newFinalState = new State(true);
        State actualInitialState=(State)currentState;
        Transition transition = new Transition(Const.EPSILON, actualInitialState);
        newInitialState.addTransition(transition);
        for (State actualFinalState : acceptedStates) {
            transition = new Transition(Const.EPSILON, actualInitialState);
            actualFinalState.addTransition(transition);
        }
        this.setInitialState(newInitialState);
        addAcceptedState(this, newFinalState);
        states.add(newInitialState);
        states.add(newFinalState);

    }
    
    /*
        Cerradura de Kleene
        
    */
    @Override
    public void kleenClosure() {
        positiveClosure();
        if(acceptedStates.size()!=1){
            System.out.println("Hay mas de un estado final vacio");
        } else{
            Iterator<State> iterator=this.getAcceptedStates().iterator();
            State actualAcceptedState=iterator.next();
            Transition transition = new Transition(Const.EPSILON, actualAcceptedState);
            currentState.addTransition(transition);
       }
    }
    
    /*
        Analizar si una cadena pertenece al AFN
        @param string
        @return true o false si corresponde
    */
    @Override
    public Boolean analizeString(String string) {
        Collection<InterfaceStates> states;
        states=(Collection<InterfaceStates>) (epsilonClausure(currentState));
        for(char symbol : string.toCharArray()){
            states=goTo(states, new Character(symbol));
        }
        for(InterfaceStates state : states){
            if(state.isFinal()){
                return true;
            }
        }
        return false;
    }
    
    /*
        Recuperar estados de aceptacion
        @return acceptedStates
    */
    public Collection<State> getAcceptedStates() {
        return acceptedStates;
    }
    
    /*
        Establece los estados de aceptacion a partir de una coleccion de estados
        @param acceptedStates
    */
    public void setAcceptedStates(Collection<State> acceptedStates) {
        this.acceptedStates = acceptedStates;
    }
    
    /*
        Recupera los estados en forma de coleccion de estados
        @return states
    */
    public Collection<State> getStates() {
        return states;
    }

    /*
        Define todos los estados a incluir mediante una coleccion de estados
        @param states
    */
    public void setStates(Collection<State> states) {
        this.states = states;
    }
    
    /*
        Recuperar el estado actual
        @return currentState
    */
    public InterfaceStates getCurrentState() {
        return currentState;
    }
    
    /*
        Establece el estado actual
        @param currentState
    */
    public void setCurrentState(InterfaceStates currentState) {
        this.currentState = currentState;
    }
    
    /*
        Recupera el Alfabeto perteneciente al AFN
        @return alphabet
    */
    public Alpha getAlpha() {
        return alphabet;
    }

    /*
        Establece el alfabeto del AFN
        @param alphabet
        @see Alpha
    */
    public void setAlpha(Alpha alphabet) {
        this.alphabet = alphabet;
    }
    
    /*
        Regresa el AFN en formato de cadena
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
            InterfaceStates s=acc.next();
            info+=s.getId()+":"+s.getToken()+",";
        }
        return info;
    }
    
    /*
        Recupera el estado inicial
        @return currentState
    */
    public InterfaceStates getInitialState() {
        return currentState;
    }

    /*
        Establece el estado inicial
        @param currentState
    */
    public void setInitialState(InterfaceStates currentState) {
        this.currentState = currentState;
    }
    
    /*
        Operador Cerradura Epsilon mediante colecciones
        @param states todos los estados agrupados en coleccion de estados
    */
    public Collection<InterfaceStates> epsilonClausure(Collection<InterfaceStates> states){
        Collection<InterfaceStates> c;
        c=new HashSet<>();
        for(InterfaceStates state : states){
            c.addAll(epsilonClausure(state));
        }
        return c;
    }
    
    /*
        Operador Cerradura Epsilon mediante un estado unico
        @param state
    */
    public Collection<InterfaceStates> epsilonClausure(InterfaceStates state){
        Stack<InterfaceStates> stack= new Stack<InterfaceStates>();
        stack.push(state);
        InterfaceStates e=null;
        Collection<InterfaceStates> c;
        c=new HashSet<>();
        while(!stack.isEmpty())
        {
            InterfaceStates s=stack.pop();
            if(!c.contains(s))
            {
                for(InterfaceStates sp:s.epsilonClosure())
                {
                    stack.add(sp);
                }
                c.add(s);
            }
            else
            {
                continue;
            }
        }
        return c;
    }
    
    /*
        Recupera un estado mediante un identificador
        @param id
        @return id
    */
    public InterfaceStates getStateById(int id){
        StateHandlers sc=(StateHandlers)states;
        return (InterfaceStates)sc.get(id);
        
    }
        
    /*
        Recupera los ID de todos los estados
        @param ids
        @return states en formato de coleccion
    */
    public Collection<InterfaceStates> getStatesByIds(Collection<Integer> ids){
        //1System.out.println("Aqui ");
        Collection<InterfaceStates> states;
        states=new HashSet<>();
        for(Integer id:ids){
            states.add(getStateById(id));
        }
        return states;
    }
    
    /*
        Operacion mover
        @param states coleccion
        @param symbol
        @return moveStates
    */
    public Collection<InterfaceStates> move(Collection<InterfaceStates> states, Character symbol){
        Collection<InterfaceStates> moveStates;
        moveStates=new HashSet<>();
        for(InterfaceStates state: states){
            moveStates.addAll(move(state,symbol));
        }
        return moveStates;
    }
    
    /*
        Operacion mover
        @param states 
        @param c
        @return r
    */
    public Collection<InterfaceStates> move(InterfaceStates e,Character c)
    {
        Collection<InterfaceStates> r=new StateHandlers();
        for(Transition t:e.getTransitions())
        {
            if(t.getInitialSymbol()<=c && t.getLastSymbol()>=c)
            {
                r.addAll(t.getNextStates());
            }
        }
        return r;
    }
    
    /*
        Operacion ir A
        @param states
        @param symbol
        @return epsilonClausure
    */
    public Collection<InterfaceStates> goTo(Collection<InterfaceStates> states, Character symbol){
        return epsilonClausure(move(states, symbol));
    }
    
    /*
        Operacion ir A
        @param state
        @param symbol
        @return epsilonClausure
    */
    public Collection<InterfaceStates> goTo(InterfaceStates state, Character symbol){
        return epsilonClausure(move(state, symbol));
    }
    
    /*
        Asignar un codigo Hash
        @return hash
    */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.idAFN);
        return hash;
    }

    /*
        Verificar igualdad
        @param obj
        @return true o false si es igual
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
        final AFN other = (AFN) obj;
        if (!Objects.equals(this.idAFN, other.idAFN)) {
            return false;
        }
        return true;
    }
    
    /*
        Crear un AFN basico a partir de un unico simbolo
        @param symbol
    */
    public void createBasic(Character symbol)
    {
        alphabet.addElement(symbol);
        State state1=new State(false);
        State state2=new State(true);
        Transition t=new Transition(symbol,state2);
        state1.addTransition(t);
        currentState=state1;
        states.add(state1);
        states.add(state2);
        acceptedStates.add(state2);
        currentState=state1;
    }
    
}