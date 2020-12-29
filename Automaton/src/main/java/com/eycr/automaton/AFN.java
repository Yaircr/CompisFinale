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

    public static Integer getCounterAFN() {
        return counterAFN;
    }

    public static void setCounterAFN(Integer counterAFN) {
        AFN.counterAFN = counterAFN;
    }

    public AFN(char c, char c0) {
        init();
        createBasic(c,c0);
    }
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

    public Integer getId() {
        return idAFN;
    }

    public void setIdAFN(Integer idAFN) {
        this.idAFN = idAFN;
    }
    
    private void init()
    {
        idAFN=counterAFN++;
        acceptedStates=new StateHandlers();
        states=new StateHandlers();
        //currentState=new State(false);
        alphabet=new Alpha();
    }
    public AFN()
    {
        init();
    }
    public AFN(Character symbol)
    {
        /*
        * Genera un automata simple a partir de un solo simbolo
        * 1.- Agregamos el simbolo al alfabeto
        * 2.- Creamos los estados incial y final
        * 3.- Creamos la transición del estado 1 al 2
        */
        init();
        createBasic(symbol);
        
        
    }
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

    public void associateToken(Integer token)
    {
        for(InterfaceStates s:acceptedStates)
        {
            s.setToken(token);
        }
    }
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

    @Override
    public void concatenateAFN(InterfaceAFN automata) {
        /*
        * Cerradura positiva
        * 1.- Unimos los alfabetos de los 2 automatas
        * 2.- Elegimos el estado final de este automata en caso de que haya mas de uno
        * 3.- Quitamos el caracter de estado fina al ese estado del paso 2
        * 4.- Agregamos transiion al estado final elegido opiando las transiiones del estado iniial del automata reibido
        * 5.- Eliminamos estado iniial del automata reibido
        * 6.- Anadimos estados del automata 2
        * 7.- Anadimos estados finales del automata                                         
        */
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
    public Integer selectFinalState()
    {
        return -1;
    }
    @Override
    public void addAFN(InterfaceAFN automata) {
        /*
        * Unir un automata con el actual por Thompson
        * 1.- Unimos los alfabetos de los 2 automatas
        * 2.- Creamos un nuevo estado inicial
        * 3.- Creamons un nuevo estado final
        * 4.- Creamos transiciones epsilon del estado inicial a los estados iniciales anteriores
        * 5.- Creamos transiciones epsilon de los estados finales anteriores al nuevo estado final
        */
        AFN af=(AFN)automata;
        this.alphabet.addAlpha(af.getAlpha());
        State newIniState=new State(false);
        State newFinalState=new State(true);
        states.add(newIniState);
        states.add(newFinalState);
        State s=(State)currentState;
        
        State s2=(State)af.currentState;
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
        /*
        *Un AFnD puede tener más de un estado de aceptación, por lo tanto si es general me parece que 
        *quitarle los estados de aceptación, pero si es un automata de Thompson es correcto
        */

    }

    @Override
    public void positiveClosure() {
        /*
        * Cerradura positiva
        * 1.- Creamos un nuevo estado inicial
        * 2.- Creamons un nuevo estado final
        * 3.- Creamos transiciones epsilon del nuevo estado inicial al estado inicial anterior
        * 4.- Creamos transiciones epsilon de los estados finales anteriores al nuevo estado final
        */
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

    @Override
    public void kleenClosure() {
        positiveClosure();
        if(acceptedStates.size()!=1){
            System.out.println("More than one final state or the collection is empty");
        } else{
            Iterator<State> iterator=this.getAcceptedStates().iterator();
            State actualAcceptedState=iterator.next();
            Transition transition = new Transition(Const.EPSILON, actualAcceptedState);
            currentState.addTransition(transition);
       }
    }

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

    public Collection<State> getAcceptedStates() {
        return acceptedStates;
    }

    public void setAcceptedStates(Collection<State> acceptedStates) {
        this.acceptedStates = acceptedStates;
    }

    public Collection<State> getStates() {
        return states;
    }

    public void setStates(Collection<State> states) {
        this.states = states;
    }

    public InterfaceStates getCurrentState() {
        return currentState;
    }

    public void setCurrentState(InterfaceStates currentState) {
        this.currentState = currentState;
    }

    public Alpha getAlpha() {
        return alphabet;
    }

    public void setAlpha(Alpha alphabet) {
        this.alphabet = alphabet;
    }
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
    
    
    public InterfaceStates getInitialState() {
        return currentState;
    }

    public void setInitialState(InterfaceStates currentState) {
        this.currentState = currentState;
    }
    
    public Collection<InterfaceStates> epsilonClausure(Collection<InterfaceStates> states){
        Collection<InterfaceStates> c;
        c=new HashSet<>();
        for(InterfaceStates state : states){
            c.addAll(epsilonClausure(state));
        }
        return c;
    }
    
    public Collection<InterfaceStates> epsilonClausure(InterfaceStates state){
        //System.out.println("Entra a letodo epsilon");
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
        //System.out.println("Termina");
        return c;
    }
    
    public InterfaceStates getStateById(int id){
        StateHandlers sc=(StateHandlers)states;
        return (InterfaceStates)sc.get(id);
        
    }
    
    public Collection<InterfaceStates> getStatesByIds(Collection<Integer> ids){
        //1System.out.println("Aqui ");
        Collection<InterfaceStates> states;
        states=new HashSet<>();
        for(Integer id:ids){
            states.add(getStateById(id));
        }
        return states;
    }
    
    public Collection<InterfaceStates> move(Collection<InterfaceStates> states, Character symbol){
        Collection<InterfaceStates> moveStates;
        moveStates=new HashSet<>();
        for(InterfaceStates state: states){
            moveStates.addAll(move(state,symbol));
        }
        return moveStates;
    }
    
     
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
    
    public Collection<InterfaceStates> goTo(Collection<InterfaceStates> states, Character symbol){
        return epsilonClausure(move(states, symbol));
    }
    
    public Collection<InterfaceStates> goTo(InterfaceStates state, Character symbol){
        return epsilonClausure(move(state, symbol));
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.idAFN);
        return hash;
    }

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