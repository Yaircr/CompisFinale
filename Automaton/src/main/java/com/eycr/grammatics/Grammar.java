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
package com.eycr.grammatics;

import java.util.ArrayList;

public class Grammar {
    private ArrayList<DerivationNode> rules;
    
    public Grammar(){
        rules=new ArrayList<DerivationNode>();
    }
    
    public void add(DerivationNode node){
        rules.add(node);
    }    
}
