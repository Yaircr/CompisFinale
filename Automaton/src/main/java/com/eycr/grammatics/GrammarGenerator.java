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

import com.eycr.lexic.LexicAnalyzer;
import com.eycr.utilities.Const;
/*
    Interfaz para la gramática de gramaticas
    Incluye los metodos a usar para generacion de reglas, ladoIzq, ladosDerechos,ladosDerechosP, listaSimbolos, listaSimbolosP        
    
*/

public class GrammarGenerator {
    private LexicAnalyzer lexic;
    /*     
        @return true o false si genera el arreglo de reglas
    */
    boolean G(Grammar g){
	if(listaReglas(g)){
		return true;
	}
	return false;
    }
    /*       
        @param g        
        @return true o false si valida las reglas   
    */
    boolean listaReglas(Grammar g){
        int token;
        if(regla(g)){
            token=lexic.getToken();
            if(token == Const.PC){
                if(listaReglasP(g)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    /*       
        @param g        
        @return true o false si valida las reglas   
    */
    boolean listaReglasP(Grammar g){
        LexicAnalyzer E;
        int token;
        E=lexic.getStatus();
        if(regla(g)){
            token=lexic.getToken();
            if(token==Const.PC){
                if(listaReglasP(g))
                    return true;
                return false;
            }
        }
        lexic.setStatus(E);
        return true;
    }
 /*       
        @param g        
        @return true o false si valida las reglas   
    */
    boolean regla(Grammar g){
        DerivationNode r = new DerivationNode();
        int token;
        if(ladoIzq(r)){
            token=lexic.getToken();
            if(token==Const.FLECHA){
                if(ladosDerechos(r)){
                    g.add(r);
                    return true;
                }
            }
        }
        return false;
    }
 /*       
        Agrega símbolo al nodo 
        @param g        
        @return true o false si valida las reglas   
    */
    boolean ladoIzq(DerivationNode r){
        int token;
        token=lexic.getToken();
        if(token==Const.SIMB){
            r.setDerivationSybol(lexic.getLexeme(token));//Revisar parametros de lexeme
            return true;//Cuidado
        }
        return false;
    }
 /*       
        @param g        
        @return true o false si valida las reglas   
    */
    boolean ladosDerechos(DerivationNode r){
        if(listaSimbolos(r)){
            if(ladosDerechosP(r)){
                return true;
            }
        }
        return false;
    }
 /*       
        Agrega símbolo al nodo
        @param g        
        @return true o false si valida las reglas   
    */
    boolean ladosDerechosP(DerivationNode r){
        DerivationNode n2 = r.getDerivA();
        int token;
        token=lexic.getToken();
        if(token==Const.OR){
            if(listaSimbolos(r)){
                n2.setDerivA(r.getDerivA());
                r.setDerivA(n2);
                if(ladosDerechosP(r)){
                    return true;
                }
            }
        }
        lexic.undoYylex();
        return true;
    }

     /*       
        Agrega símbolo al nodo
        @param g        
        @return true o false si valida las reglas   
    */
    boolean listaSimbolos(DerivationNode r){
        DerivationNode n = new DerivationNode();
        int token;
        token=lexic.getToken();
        if(token==Const.SIMB){
            n.setDerivationSybol(lexic.getLexeme(token));//revisar
            if(listaSimbolosP(n)){
                r.setDerivA(n);
                return true;
            }
        }
        return false;
    }

     /*       
        Agrega símbolo al nodo
        @param g        
        @return true o false si valida las reglas   
    */    
    public GrammarGenerator() {
    }

    boolean listaSimbolosP(DerivationNode n1){
        int token;
        DerivationNode n = new DerivationNode();
        token=lexic.getToken();
        if(token==Const.SIMB){
            n.setDerivationSybol(lexic.getLexeme(token));
            n1.setDerivA(n);
            if(listaSimbolosP(n)){
                return true;
            }
        }
        lexic.undoYylex();
        return false;
    }
}
