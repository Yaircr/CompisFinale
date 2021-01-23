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
 * @author Jared
 */
public class analizadorSintactico {
    
    private LexicAnalyzer lexic;

    private float res = 0;

    analizadorSintactico(TableAFD afd, String s) {
        lexic = new LexicAnalyzer(s, afd);
    }

    public InterfaceCalc ini() {
        int token = -1;
        
        double res = 0;
        InterfaceCalc ref = new InterfaceCalc(res, true);
        
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

    public InterfaceCalc E(double res) {
        InterfaceCalc ref = new InterfaceCalc(res, false);
        if ((ref = T(ref.res)).flag) {
            if ((ref = Ep(ref.res)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public InterfaceCalc Ep(double res) {
        InterfaceCalc ref = new InterfaceCalc(res, false);
        int token;
        float resAux = 0;
        InterfaceCalc ref_2 = new InterfaceCalc(resAux, false);
        token = lexic.getToken();
        if (token == constCalculadora.ADD) {
            if ((ref_2 = T(ref_2.res)).flag) {
                res += ref_2.res;
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
                res -= ref_2.res;
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

    public InterfaceCalc T(double res) {
        InterfaceCalc ref = new InterfaceCalc(res, false);
        if ((ref = P(ref.res)).flag) {
            if ((ref = Tp(ref.res)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public InterfaceCalc Tp(double res) {
        InterfaceCalc ref = new InterfaceCalc(res, false);
        int token;
        float resAux = 0;
        InterfaceCalc ref_2 = new InterfaceCalc(resAux, false);
        token = lexic.getToken();
        if (token == constCalculadora.PROD) {
            if ((ref_2 = P(ref_2.res)).flag) {
                res *= ref_2.res;
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
                res /= ref_2.res;
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

    public InterfaceCalc P(double res) {
        InterfaceCalc ref = new InterfaceCalc(res, false);
        if ((ref = F(ref.res)).flag) {
            if ((ref = Pp(ref.res)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public InterfaceCalc Pp(double res) {
        int token;
        float resAux = 0;
        InterfaceCalc ref = new InterfaceCalc(res, false);
        InterfaceCalc ref_2 = new InterfaceCalc(resAux, false);
        token = lexic.getToken();
        if (token == constCalculadora.POWE) {
            if ((ref_2 = F(ref_2.res)).flag) {
                res = (float) Math.pow(res, ref_2.res);
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

    public InterfaceCalc F(double res) {
        int token;
        token = lexic.getToken();
        InterfaceCalc ref = new InterfaceCalc(res, false);
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
                            res = (float) Math.sin(res);
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
                            res = (float) Math.cos(res);
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
                            res = (float) Math.tan(res);
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
                            res = (float) Math.log(res);
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
                            res = (float) Math.log10(res);
                            ref.res = res;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case constCalculadora.NUM:
                ref.res = Double.parseDouble(lexic.getLexeme()); //(?)
                ref.flag = true;
                return ref;
        }
        ref.flag = false;
        return ref;
    }
}
