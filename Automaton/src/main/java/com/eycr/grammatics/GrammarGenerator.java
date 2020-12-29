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

public class GrammarGenerator {
    private LexicAnalyzer lexic;
    boolean G(Grammar g){
	if(listaReglas(g)){
		return true;
	}
	return false;
    }

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

    boolean ladoIzq(DerivationNode r){
        int token;
        token=lexic.getToken();
        if(token==Const.SIMB){
            r.setDerivationSybol(lexic.getLexeme(token));//revisar parametros de lexeme
            return true;//Ojoooooo
        }
        return false;
    }

    boolean ladosDerechos(DerivationNode r){
        if(listaSimbolos(r)){
            if(ladosDerechosP(r)){
                return true;
            }
        }
        return false;
    }

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
