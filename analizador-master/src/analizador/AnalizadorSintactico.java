package analizador;

// @author Isaac

import java.util.Random;

 
public class AnalizadorSintactico {
    
    private AnalizadorLexico lexic = null;    
    private static final int FIN = 0;
    private static final int OR = 10;
    private static final int AND = 20;
    private static final int CERR_TRAN = 30;
    private static final int CERR_KLEE = 40;
    private static final int OPC = 50;
    private static final int PAR_IZQ = 60;
    private static final int PAR_DER = 70;
    private static final int COR_IZQ = 80;
    private static final int COR_DER = 90;
    private static final int GUION = 100;
    //private static final int ISLASH = 110;
    private static final int SIMB = 120;
       
    
    public AnalizadorSintactico(AFD afd, String cadena){
        lexic = new AnalizadorLexico(afd, cadena);        
    }
    
    public Referencia init(){
        int token = -1;
        
        AFN afn = null;
        Referencia ref = new Referencia(afn, true);
        
        if ((ref = E(ref.afn)).flag) {
            token = lexic.obtener_token();
            if(token == FIN){
                return ref;
            }
        }
        return ref;
    }
    
    public Referencia E(AFN f){
        Referencia ref = new Referencia(f, true);
        if((ref = T(ref.afn)).flag){
            if((ref = Ep(ref.afn)).flag){
                ref.flag = true;
                return ref;
            }
        }
        ref.flag = false;
        return ref;
    }
    
    public Referencia Ep(AFN ff){
        Referencia ref = new Referencia(ff, true);
        
        int token = -1;
        token = lexic.obtener_token();
        AFN f2 = null;
        
        Referencia ref_2 = new Referencia(f2, true);
        
        if(token == OR){
            if((ref_2 = T(ref_2.afn)).flag){
                AFN auxx = ff.UnirAFN(ref_2.afn);                
                ref.afn = auxx;
                if((ref = Ep(ref.afn)).flag){
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
    
    public Referencia T(AFN f){
        Referencia ref = new Referencia(f, true);
        if( (ref = C(ref.afn)).flag){
            if((ref = Tp(ref.afn)).flag){
                ref.flag = true;                
                return ref;
            }                    
        }
        ref.flag = false;
        return ref;
    }
    
    public Referencia Tp(AFN f){
        Referencia ref = new Referencia(f, true);
        int token = lexic.obtener_token();
        AFN f2 = null;
        Referencia ref_2 = new Referencia(f2, true);
        if(token == AND){
            if((ref_2 = C(ref_2.afn)).flag){
                AFN aux = f.ConcatAFN(ref_2.afn);
                f = aux;
                ref.afn = f;
                if((ref = Tp(ref.afn)).flag){
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
    
    public Referencia C(AFN ff){        
        Referencia ref = new Referencia(ff, true);
        if((ref = F(ref.afn)).flag){
            if((ref = Cp(ref.afn)).flag){
                ref.flag = true;
                return ref;
            }                    
        }
        ref.flag = false;
        return ref;
    }
    
    public Referencia Cp(AFN f){
        int token = lexic.obtener_token();     
        Referencia ref = new Referencia(f, true);
        switch(token){
            case CERR_TRAN:
                ref.afn.CerraduraTrans();
                if( (ref = Cp(ref.afn)).flag){                   
                    return ref;
                }     
                ref.flag = false;
                return ref;
                
            case CERR_KLEE:
                ref.afn.CerraduraKleene();                
                if((ref = Cp(ref.afn)).flag){
                    return ref;
                }             
                ref.flag = false;
                return ref;
                
            case OPC:
                ref.afn.Opcional();                
                if( (ref = Cp(ref.afn)).flag){
                    return ref;
                }
                ref.flag = false;
                return ref;
        }
                
        lexic.regresarToken();
        return ref;
    }
    
    public Referencia F(AFN f){
        int token = lexic.obtener_token();
        String lex1, lex2;
        Referencia ref = new Referencia(f, true);
        
        switch(token){            
            case PAR_IZQ:
                if((ref = E(ref.afn)).flag){
                    token = lexic.obtener_token();
                    if(token == PAR_DER){
                        return ref;
                    }
                }
            ref.flag = false;
            return ref;
            
            case COR_IZQ:
                token = lexic.obtener_token();                        
                if(token == SIMB){
                    lex1 = lexic.get_yytext();
                    token = lexic.obtener_token();                    
                    if(token == GUION){
                        token = lexic.obtener_token();                                                
                        if(token == SIMB){
                            lex2 = lexic.get_yytext();
                            token = lexic.obtener_token();                            
                            if(token == COR_DER){
                                char c_1 = lex1.charAt(0);
                                char c_2 = lex2.charAt(0);
                                ref.afn = ref.afn.CrearBasico(c_1, c_2);                                
                                return ref;
                            }
                        }                                
                    }
                }
            ref.flag = false;
            return ref;
                       
            case SIMB:
                char c_1 = lexic.get_yytext().charAt(0);
                ref.afn = ref.afn.CrearBasico(c_1, c_1);                                
                return ref;
        }
        ref.flag = false;
        return ref;
    }
   
}
