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
    
    /*
        Regresa la tabla de transiciones
        @return tableTransition arreglo bidimensional de integers
    */
    public Integer[][] getTableTransition() {
        return tableTransition;
    }

    /*
        Define las transiciones de la tabla
        @param tableTransition
    */
    public void setTableTransition(Integer[][] tableTransition) {
        this.tableTransition = tableTransition;
    }

    /*
        Recupera simbolos segun el hash
        @return symbols
    */
    public HashMap<Character,Integer> getSymbols() {
        return symbols;
    }

    /*
        Define los simbolos
        @param symbols en un hashmap
    */
    public void setSymbols(HashMap<Character,Integer> symbols) {
        this.symbols = symbols;
    }
    

    /*
        Constructor de la tabla
        @param tableTransition
        @param symbols
    */
    public TableAFD(Integer[][] tableTransition, HashMap<Character,Integer> symbols) {
        this.tableTransition = tableTransition;
        this.symbols = symbols;
    }

    /*
        Constructor de la tabla
        @param afd un AFD completo
        Segun los estados, sus simbolos y transiciones se recorre el AFD para crear la table en las posiciones que correspondan
    */
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
                System.out.println("["+state.getId()+"]["+j+"]="+tableTransition[state.getId()][j]);
                j++;
            }
            tableTransition[state.getId()][j]=state.getToken();
        }
    }
    
    /*
        Obtiene el siguiente estado
        @param actualState
        @param c
        @return tableTransition
    */
    public Integer nextState(Integer actualState,Character c)
    {
        /*FUNCIONA
            Pero si se prueba con un carácter que no este dentro del alfabeto del automata, truena el programa, ya que accede a un null pointer
        */
        return tableTransition[actualState][symbols.get(c)];
    }
    
    /*
        Imprimir la tabla
    */
    public void print()
    {
        System.out.println("Tabla:");
        String info = "Estado\t";
        for(Character c: symbols.keySet()){
            info+=c+"(Simbolo)\t";
        }
        info+="Es Final";
        System.out.println(info);
        for(int i=0;i<numberOfStates;i++){
            System.out.print(i+"\t");
            for(int j=0; j<symbols.size()+1; j++){
                System.out.print(tableTransition[i][j]+"\t\t");
            }
            System.out.println("");
        }
    }
    public String printConsola()
    {
        String r ="";
        r+="Tabla:\n";
        String info = "Estado\t";
        for(Character c: symbols.keySet()){
            info+=c+"(Simbolo)\t";
        }
        info+="Es Final\n";
        r+=info;
        for(int i=0;i<numberOfStates;i++){
            r+=i+"\t";
            for(int j=0; j<symbols.size()+1; j++){
                r+=tableTransition[i][j]+"\t\t";
            }
            r+="\n";
        }
        return r;
    }
    
    
}
