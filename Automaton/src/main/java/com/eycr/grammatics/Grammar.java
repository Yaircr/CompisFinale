/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.grammatics;

import java.util.ArrayList;

/**
 *
 * @author art
 */
public class Grammar {
    private ArrayList<DerivationNode> rules;
    
    public Grammar(){
        rules=new ArrayList<DerivationNode>();
    }
    
    public void add(DerivationNode node){
        rules.add(node);
    }    
}
