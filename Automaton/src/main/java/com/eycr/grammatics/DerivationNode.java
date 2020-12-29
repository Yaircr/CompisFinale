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

public class DerivationNode {
    private char derivationSybol;
    private boolean finalSymbol;
    private DerivationNode derivA;
    private DerivationNode derivB;
    
    public DerivationNode(){
       
    }

    public char getDerivationSybol() {
        return derivationSybol;
    }

    public void setDerivationSybol(char derivationSybol) {
        this.derivationSybol = derivationSybol;
    }

    public boolean isFinalSymbol() {
        return finalSymbol;
    }

    public void setFinalSymbol(boolean finalSymbol) {
        this.finalSymbol = finalSymbol;
    }

    public DerivationNode getDerivA() {
        return derivA;
    }

    public void setDerivA(DerivationNode derivA) {
        this.derivA = derivA;
    }

    public DerivationNode getDerivB() {
        return derivB;
    }

    public void setDerivB(DerivationNode derivB) {
        this.derivB = derivB;
    }
    
    
}
