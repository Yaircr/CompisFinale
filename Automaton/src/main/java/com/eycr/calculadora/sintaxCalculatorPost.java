/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.calculadora;

import com.eycr.automaton.TableAFD;
import com.eycr.lexic.LexicAnalyzer;

/**
 *
 * @author diego
 */
public class sintaxCalculatorPost {
    LexicAnalyzer lexic;    

    private float res = 0;
    
    public class Number{
        private float valor;
        /**
         * @param valor actualizacion de valor numerico
         */
        public float getValor() {
            return valor;
        }
        /**
         * 
         * @return valor actual
         */
        public void setValor(float valor) {
            this.valor = valor;
        }
        
        public Number(){
            this.valor = 0f;
        }
    }
    private final Number resu = new Number();
    private LexicAnalyzer lexic2;
        
    public sintaxCalculatorPost(TableAFD afd, String s) {
        lexic = new LexicAnalyzer(s, afd);
    }
    
    public boolean E(Number v) {
        if (T(v)) {
            if (Ep(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean Ep(Number v) {
        int token;
        Number v1 = new Number();
        token = lexic.getToken();

        if (token == constCalculadora.ADD || token == constCalculadora.MIN) {
            if (T(v1)) {
                v.setValor(v.getValor() + ((token == constCalculadora.ADD) ? v1.getValor() : -v1.getValor()));

                if (Ep(v)) {
                    return true;
                }
            }
            return false;
        }
        lexic.undoYylex();
        return true;
    }

    public boolean T(Number v) {
        if (P(v)) {
            if (Tp(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean Tp(Number v) {
        int token;
        Number v1 = new Number();
        token = lexic.getToken();
        if (token == constCalculadora.MUL || token == constCalculadora.DIV) {
            if (P(v1)) {
                v.setValor(v.getValor() * ((token == constCalculadora.MUL) ? v1.getValor() : 1f / v1.getValor()));
                if (Tp(v)) {
                    return true;
                }
            }
            return false;
        }
        lexic.undoYylex();
        return true;
    }

    public boolean P(Number v) {
        if (F(v)) {
            if (Pp(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean Pp(Number v) {
        int token;
        Number v1 = new Number();
        token = lexic.getToken();
        if (token == constCalculadora.POWE) {
            if (F(v1)) {
                v.setValor((float) Math.pow(v.getValor(), v1.getValor()));
                if (Pp(v)) {
                    return true;
                }
            }
            return false;
        }
        lexic.undoYylex();
        return true;
    }

    public boolean F(Number v) {
        int token = lexic.getToken();
        System.out.println("Token en F: " + token);
        System.out.println("Cadena obtenida en F" + lexic.getS());
        System.out.println("Caracter actual" + lexic.getActualCharacter());
        System.out.println("Init lexem" + lexic.getIniLexeme());
        switch (token) {
        case constCalculadora.PAR_I:
            if (E(v)) {
                token = this.lexic.getToken();
                if (token == constCalculadora.PAR_D) {
                    return true;
                }
            }
            return false;
        case constCalculadora.SIN:
            token = lexic.getToken();
            if (verificarParentesis(token, v)) {
                v.setValor((float) Math.sin(v.getValor()));
                return true;
            }
            return false;
        case constCalculadora.COS:
            token = lexic.getToken();
            if (verificarParentesis(token, v)) {
                v.setValor((float) Math.cos(v.getValor()));
                return true;
            }
            return false;

        case constCalculadora.TAN:
            token = lexic.getToken();
            if (verificarParentesis(token, v)) {
                v.setValor((float) Math.tan(v.getValor()));
                return true;
            }
            return false;

        case constCalculadora.EXP:
            token = lexic.getToken();
            if (verificarParentesis(token, v)) {
                v.setValor((float) Math.exp(v.getValor()));
                return true;
            }
            return false;

        case constCalculadora.LOG:
            token = lexic.getToken();
            if (verificarParentesis(token, v)) {
                v.setValor((float) Math.log10(v.getValor()));
                return true;
            }
            return false;

        case constCalculadora.LN:
            token = lexic.getToken();
            if (verificarParentesis(token, v)) {
                v.setValor((float) Math.log(v.getValor()));
                return true;
            }
            return false;

        case constCalculadora.NUM:
            v.setValor(Float.parseFloat(lexic.getLexeme()));
            return true;
        }
        return false;
    }

    public boolean verificarParentesis(int tok, Number v) {
        if (tok == constCalculadora.PAR_I) {
            if (E(v)) {
                int token = lexic.getToken();
                if (token == constCalculadora.PAR_D) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean validate(Number v) {
        int token;
        
        if (E(v)) {
            token = lexic.getToken();
            System.out.println("Token de E: " + token);
            if (token == constCalculadora.END) {
                return true;
            }
        }
        System.out.println("Funion validate");
        return false;
    }

    public float evaluate() {
        if (validate(this.resu)) {
            return this.resu.getValor();
        }
        System.out.println("Eror");
        return -1f;
    }
    
    /*public InterfaceCalcPost ini() {
        int token = -1;
        
        String res = "";
        InterfaceCalcPost ref = new InterfaceCalcPost(res, true);
        
        if((ref = E(ref.res)).flag){
            token = lexic.getToken();
            if (token == constCalculadora.END) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }*/

    /*public InterfaceCalcPost E(String res) {
        InterfaceCalcPost ref = new InterfaceCalcPost(res, false);
        if ((ref = T(ref.res)).flag) {
            if ((ref = Ep(ref.res)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public InterfaceCalcPost Ep(String res) {
        InterfaceCalcPost ref = new InterfaceCalcPost(res, false);
        int token;
        String resAux = "";
        InterfaceCalcPost ref_2 = new InterfaceCalcPost(resAux, false);
        token = lexic.getToken();
        if (token == constCalculadora.ADD) {
            if ((ref_2 = T(ref_2.res)).flag) {
                res = res + ref_2.res + "+";
                ref.res = res;
                if ((ref = Ep(ref.res)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        } else if (token == constCalculadora.MIN) {
            if ((ref_2 = T(ref_2.res)).flag) {
                res = res + ref_2.res + "-";
                ref.res = res;
                if ((ref = Ep(ref.res)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        }
        lexic.undoYylex();
        ref.flag = true;
        return ref;
    }

    public InterfaceCalcPost T(String res) {
        InterfaceCalcPost ref = new InterfaceCalcPost(res, false);
        if ((ref = P(ref.res)).flag) {
            if ((ref = Tp(ref.res)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public InterfaceCalcPost Tp(String res) {
        InterfaceCalcPost ref = new InterfaceCalcPost(res, false);
        int token;
        String resAux = "";
        InterfaceCalcPost ref_2 = new InterfaceCalcPost(resAux, false);
        token = lexic.getToken();
        if (token == constCalculadora.MUL) {
            if ((ref_2 = P(ref_2.res)).flag) {
                res = res + ref_2.res + "*";
                ref.res = res;
                if ((ref = Tp(ref.res)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        }
        else if(token == constCalculadora.constCalculadora.DIV){
            if ((ref_2 = P(ref_2.res)).flag) {
                res = res + ref_2.res + "/";
                ref.res = res;
                if ((ref = Tp(ref.res)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
        }
        lexic.undoYylex();
        ref.flag = true;
        return ref;
    }

    public InterfaceCalcPost P(String res) {
        InterfaceCalcPost ref = new InterfaceCalcPost(res, false);
        if ((ref = F(ref.res)).flag) {
            if ((ref = Pp(ref.res)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public InterfaceCalcPost Pp(String res) {
        int token;
        String resAux = "";
        InterfaceCalcPost ref = new InterfaceCalcPost(res, false);
        InterfaceCalcPost ref_2 = new InterfaceCalcPost(resAux, false);
        token = lexic.getToken();
        if (token == constCalculadora.POWE) {
            if ((ref_2 = F(ref_2.res)).flag) {
                res = res + ref_2.res + "^";
                ref.res = res;
                if ((ref = Pp(ref.res)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        }
        lexic.undoYylex();
        ref.flag = true;
        return ref;
    }

    public InterfaceCalcPost F(String res) {
        int token;
        token = lexic.getToken();
        InterfaceCalcPost ref = new InterfaceCalcPost(res, false);
        switch (token) {
            case constCalculadora.constCalculadora.PAR_I:
                if ((ref = E(ref.res)).flag) {
                    token = lexic.getToken();
                    if (token == constCalculadora.constCalculadora.PAR_D) {
                        ref.flag = true;
                        return ref;
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.constCalculadora.SIN:
                token = lexic.getToken();
                if (token == constCalculadora.constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.constCalculadora.PAR_D) {
                            res = res + "sin()";
                            ref.res = res;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.constCalculadora.COS:
                token = lexic.getToken();
                if (token == constCalculadora.constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.constCalculadora.PAR_D) {
                            res = res + "cos()";
                            ref.res = res;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.TAN:
                token = lexic.getToken();
                if (token == constCalculadora.constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.constCalculadora.PAR_D) {
                            res = res + "tan()";
                            ref.res = res;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.LN:
                token = lexic.getToken();
                if (token == constCalculadora.constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.constCalculadora.PAR_D) {
                            res = res + "ln()";
                            ref.res = res;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.LOG:
                token = lexic.getToken();
                if (token == constCalculadora.constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.constCalculadora.PAR_D) {
                            res = res + "log()";
                            ref.res = res;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.NUM:
                ref.res = lexic.getLexeme();
                ref.flag = true;
                return ref;
        }
        ref.flag = false;
        return ref;
    }*/
}
