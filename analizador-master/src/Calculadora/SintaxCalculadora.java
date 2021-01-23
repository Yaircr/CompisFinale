package Calculadora;

import analizador.*;

//@author yax
 
public class SintaxCalculadora{
    AnalizadorLexico lexic;

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

    SintaxCalculadora(AFD afd, String s) {
        lexic = new AnalizadorLexico(afd, s);
    }

    public ReferenciaC ini() {
        int token = -1;
        
        double resultado = 0;
        ReferenciaC ref = new ReferenciaC(resultado, true);
        
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

    public ReferenciaC E(double resultado) {
        ReferenciaC ref = new ReferenciaC(resultado, false);
        if ((ref = T(ref.resultado)).flag) {
            if ((ref = Ep(ref.resultado)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public ReferenciaC Ep(double resultado) {
        ReferenciaC ref = new ReferenciaC(resultado, false);
        int token;
        float resAux = 0;
        ReferenciaC ref_2 = new ReferenciaC(resAux, false);
        token = lexic.obtener_token();
        if (token == MAS) {
            if ((ref_2 = T(ref_2.resultado)).flag) {
                resultado += ref_2.resultado;
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
                resultado -= ref_2.resultado;
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

    public ReferenciaC T(double resultado) {
        ReferenciaC ref = new ReferenciaC(resultado, false);
        if ((ref = P(ref.resultado)).flag) {
            if ((ref = Tp(ref.resultado)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public ReferenciaC Tp(double resultado) {
        ReferenciaC ref = new ReferenciaC(resultado, false);
        int token;
        float resAux = 0;
        ReferenciaC ref_2 = new ReferenciaC(resAux, false);
        token = lexic.obtener_token();
        if (token == PROD) {
            if ((ref_2 = P(ref_2.resultado)).flag) {
                resultado *= ref_2.resultado;
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
                resultado /= ref_2.resultado;
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

    public ReferenciaC P(double resultado) {
        ReferenciaC ref = new ReferenciaC(resultado, false);
        if ((ref = F(ref.resultado)).flag) {
            if ((ref = Pp(ref.resultado)).flag) {
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }

    public ReferenciaC Pp(double resultado) {
        int token;
        float resAux = 0;
        ReferenciaC ref = new ReferenciaC(resultado, false);
        ReferenciaC ref_2 = new ReferenciaC(resAux, false);
        token = lexic.obtener_token();
        if (token == POT) {
            if ((ref_2 = F(ref_2.resultado)).flag) {
                resultado = (float) Math.pow(resultado, ref_2.resultado);
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

    public ReferenciaC F(double resultado) {
        int token;
        token = lexic.obtener_token();
        ReferenciaC ref = new ReferenciaC(resultado, false);
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
                            resultado = (float) Math.sin(resultado);
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
                            resultado = (float) Math.cos(resultado);
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
                            resultado = (float) Math.tan(resultado);
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
                            resultado = (float) Math.log(resultado);
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
                            resultado = (float) Math.log10(resultado);
                            ref.resultado = resultado;
                            ref.flag = true;
                            return ref;
                        }
                    }
                }
                ref.flag = false;
                return ref;
            case NUM:
                ref.resultado = Double.parseDouble(lexic.get_yytext()); //(?)
                ref.flag = true;
                return ref;
        }
        ref.flag = false;
        return ref;
    }
}