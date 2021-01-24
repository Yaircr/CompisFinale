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

    public sintaxCalculatorPost(TableAFD afd, String s) {
        lexic = new LexicAnalyzer(s, afd);
    }

    public InterfaceCalcPost ini() {
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
    }

    public InterfaceCalcPost E(String res) {
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
        else if(token == constCalculadora.DIV){
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
            case constCalculadora.PAR_I:
                if ((ref = E(ref.res)).flag) {
                    token = lexic.getToken();
                    if (token == constCalculadora.PAR_D) {
                        ref.flag = true;
                        return ref;
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.SIN:
                token = lexic.getToken();
                if (token == constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.PAR_D) {
                            res = res + "sin()";
                            ref.res = res;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.COS:
                token = lexic.getToken();
                if (token == constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.PAR_D) {
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
                if (token == constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.PAR_D) {
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
                if (token == constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.PAR_D) {
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
                if (token == constCalculadora.PAR_I) {
                    if ((ref = E(ref.res)).flag) {
                        token = lexic.getToken();
                        if (token == constCalculadora.PAR_D) {
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
    }
}
