/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.calculadora;

import com.eycr.lexic.LexicAnalyzer;

/**
 *
 * @author Jared
 */
public class analizadorSintactico {
    
    private LexicAnalyzer lexic;

    static final int FIN = 0;
    static final int MAS = 10;
    static final int MENOS = 20;
    static final int PROD = 30;
    static final int DIV = 40;
    static final int POT = 50;
    static final int PAR_I = 60;
    static final int PAR_D = 70;
    static final int NUM = 80;
    static final int SIN = 90;
    static final int COS = 100;
    static final int TAN = 110;
    static final int LN = 120;
    static final int LOG = 130;

    private float resultado = 0;

    SintaxCalculadoraPostfijo(AFD afd, String s) {
        lexic = new AnalizadorLexico(afd, s);
    }

    public ReferenciaCP ini() {
        int token = -1;
        
        String resultado = "";
        ReferenciaCP ref = new ReferenciaCP(resultado, true);
        
        if((ref = E(ref.resultado)).flag){
            token = lexic.obtener_token();
            if (token == FIN) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public ReferenciaCP E(String resultado) {
        ReferenciaCP ref = new ReferenciaCP(resultado, false);
        if ((ref = T(ref.resultado)).flag) {
            if ((ref = Ep(ref.resultado)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public ReferenciaCP Ep(String resultado) {
        ReferenciaCP ref = new ReferenciaCP(resultado, false);
        int token;
        String resAux = "";
        ReferenciaCP ref_2 = new ReferenciaCP(resAux, false);
        token = lexic.obtener_token();
        if (token == MAS) {
            if ((ref_2 = T(ref_2.resultado)).flag) {
                resultado = resultado + ref_2.resultado + "+";
                ref.resultado = resultado;
                if ((ref = Ep(ref.resultado)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        } else if (token == MENOS) {
            if ((ref_2 = T(ref_2.resultado)).flag) {
                resultado = resultado + ref_2.resultado + "-";
                ref.resultado = resultado;
                if ((ref = Ep(ref.resultado)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        }
        lexic.regresarToken();
        ref.flag = true;
        return ref;
    }

    public ReferenciaCP T(String resultado) {
        ReferenciaCP ref = new ReferenciaCP(resultado, false);
        if ((ref = P(ref.resultado)).flag) {
            if ((ref = Tp(ref.resultado)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public ReferenciaCP Tp(String resultado) {
        ReferenciaCP ref = new ReferenciaCP(resultado, false);
        int token;
        String resAux = "";
        ReferenciaCP ref_2 = new ReferenciaCP(resAux, false);
        token = lexic.obtener_token();
        if (token == PROD) {
            if ((ref_2 = P(ref_2.resultado)).flag) {
                resultado = resultado + ref_2.resultado + "*";
                ref.resultado = resultado;
                if ((ref = Tp(ref.resultado)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        }
        else if(token == DIV){
            if ((ref_2 = P(ref_2.resultado)).flag) {
                resultado = resultado + ref_2.resultado + "/";
                ref.resultado = resultado;
                if ((ref = Tp(ref.resultado)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
        }
        lexic.regresarToken();
        ref.flag = true;
        return ref;
    }

    public ReferenciaCP P(String resultado) {
        ReferenciaCP ref = new ReferenciaCP(resultado, false);
        if ((ref = F(ref.resultado)).flag) {
            if ((ref = Pp(ref.resultado)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public ReferenciaCP Pp(String resultado) {
        int token;
        String resAux = "";
        ReferenciaCP ref = new ReferenciaCP(resultado, false);
        ReferenciaCP ref_2 = new ReferenciaCP(resAux, false);
        token = lexic.obtener_token();
        if (token == POT) {
            if ((ref_2 = F(ref_2.resultado)).flag) {
                resultado = resultado + ref_2.resultado + "^";
                ref.resultado = resultado;
                if ((ref = Pp(ref.resultado)).flag) {
                    ref.flag = true;
                    return ref;
                }
            }
            ref.flag = false;
            return ref;
        }
        lexic.regresarToken();
        ref.flag = true;
        return ref;
    }

    public ReferenciaCP F(String resultado) {
        int token;
        token = lexic.obtener_token();
        ReferenciaCP ref = new ReferenciaCP(resultado, false);
        switch (token) {
            case PAR_I:
                if ((ref = E(ref.resultado)).flag) {
                    token = lexic.obtener_token();
                    if (token == PAR_D) {
                        ref.flag = true;
                        return ref;
                    }
                }
                ref.flag = false;
                return ref;
            case SIN:
                token = lexic.obtener_token();
                if (token == PAR_I) {
                    if ((ref = E(ref.resultado)).flag) {
                        token = lexic.obtener_token();
                        if (token == PAR_D) {
                            resultado = resultado + "sin()";
                            ref.resultado = resultado;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case COS:
                token = lexic.obtener_token();
                if (token == PAR_I) {
                    if ((ref = E(ref.resultado)).flag) {
                        token = lexic.obtener_token();
                        if (token == PAR_D) {
                            resultado = resultado + "cos()";
                            ref.resultado = resultado;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case TAN:
                token = lexic.obtener_token();
                if (token == PAR_I) {
                    if ((ref = E(ref.resultado)).flag) {
                        token = lexic.obtener_token();
                        if (token == PAR_D) {
                            resultado = resultado + "tan()";
                            ref.resultado = resultado;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case LN:
                token = lexic.obtener_token();
                if (token == PAR_I) {
                    if ((ref = E(ref.resultado)).flag) {
                        token = lexic.obtener_token();
                        if (token == PAR_D) {
                            resultado = resultado + "ln()";
                            ref.resultado = resultado;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case LOG:
                token = lexic.obtener_token();
                if (token == PAR_I) {
                    if ((ref = E(ref.resultado)).flag) {
                        token = lexic.obtener_token();
                        if (token == PAR_D) {
                            resultado = resultado + "log()";
                            ref.resultado = resultado;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case NUM:
                ref.resultado = lexic.get_yytext();
                ref.flag = true;
                return ref;
        }
        ref.flag = false;
        return ref;
    }    
}
