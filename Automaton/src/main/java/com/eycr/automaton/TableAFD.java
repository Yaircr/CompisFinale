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

import java.util.HashMap;

public class TableAFD {
    Integer[][] tableTransition;
    HashMap<Character,Integer> symbols;
    Integer numberOfStates;
    public Integer[][] getTableTransition() {
        return tableTransition;
    }

    public void setTableTransition(Integer[][] tableTransition) {
        this.tableTransition = tableTransition;
    }

    public HashMap<Character,Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(HashMap<Character,Integer> symbols) {
        this.symbols = symbols;
    }
    

    public TableAFD(Integer[][] tableTransition, HashMap<Character,Integer> symbols) {
        this.tableTransition = tableTransition;
        this.symbols = symbols;
    }

    public TableAFD(AFD afd) {
        numberOfStates=afd.getStates().size();
        this.tableTransition = new Integer[afd.getStates().size()][afd.getAlpha().getSymbols().size()+1];
        //System.out.println("Se inicializa");
        //symbols = new char[afd.getAlpha().getSymbols().size()];
        symbols=new HashMap();
        for(State state : afd.getStates()){
            //info+=state.getId()+"\t";
            int j=0;//Es la columna
            for(Character c: afd.getAlpha().getSymbols()){
                if(state.getId()==0){
                    //symbols[j]=c;
                    symbols.put(c, j);
                    //i++;
                }
                tableTransition[state.getId()][j]=(state.hastransition(c)!=null)?state.hastransition(c).getId():-1;
                //System.out.println("["+state.getId()+"]["+j+"]="+tableTransition[state.getId()][j]);
                j++;
            }
            tableTransition[state.getId()][j]=state.getToken();
        }
    }
    public Integer nextState(Integer actualState,Character c)
    {
        return tableTransition[actualState][symbols.get(c)];
    }
    public void print()
    {
        System.out.println("Tabla:");
        String info = "State\t";
        for(Character c: symbols.keySet()){
            info+=c+"(Symbol)\t";
        }
        info+="Final";
        System.out.println(info);
        for(int i=0;i<numberOfStates;i++){
            System.out.print(i+"\t");
            for(int j=0; j<symbols.size()+1; j++){
                System.out.print(tableTransition[i][j]+"\t\t");
            }
            System.out.println("");
        }
    }
    
    
}
