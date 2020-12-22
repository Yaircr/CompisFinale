/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.grammatics;

/**
 *
 * @author art
 */
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
