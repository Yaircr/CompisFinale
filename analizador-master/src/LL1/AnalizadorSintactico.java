package LL1;
/**
 *
 * @author Isaac
 */
import analizador.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnalizadorSintactico {
      
    private ArrayList<Nodo> lista_lista = new ArrayList<>();
    Set<String> simbolos_no_terminales = new LinkedHashSet<String>(); 
    Set<String> simbolos_terminales = new LinkedHashSet<String>(); 
    private AnalizadorLexico lexic;
    
    public AnalizadorSintactico() { /*nothing*/}
    
    public AnalizadorSintactico(AFD afd, String cadena){
        lexic = new AnalizadorLexico(afd, cadena);        
    }
    
    public AnalizadorSintactico(AFD afd){
        lexic = new AnalizadorLexico(afd);
    }
        
    public void asignarCadena(String cadena){
        lexic.setCadena(cadena);
    }
   
    
    public boolean init(){
        int token = -1;
        if(G()){
            token = lexic.obtener_token();
            if(token == Tokens.FIN){
                return true;
            }
        }
       
        return false;
    }
    
    public boolean G(){
        if(Reglas()){
            return true;
        }
        return false;
    }

    public boolean Reglas(){
      int token = -1;
      if(Regla()){
        token = lexic.obtener_token();
        if(token == Tokens.PUNTO_Y_COMA){
          if(Reglas_p()){
            return true;
          }
        }
      }
      return false;
    }

  public boolean Reglas_p(){
    int token = -1;
    AnalizadorLexico aux = null;
      try {
        aux = (AnalizadorLexico)lexic.clone();
      } catch (Exception e) {
          System.err.print("ERROR AL INTENTAR CLONAR EL OBJETO LEXIC");
      }
    
    if(Regla()){
      token = lexic.obtener_token();
      if(token == Tokens.PUNTO_Y_COMA){
        if(Reglas_p()){
          return true;
        }
      }
      return false;
    }
    lexic = aux; //Simulando el set_edo
    return true;
  }

  public boolean Regla(){
    int token = -1;
    String simbolo_izquierdo = "";
    Referencia r = null;
    if( (r = LadoIzq(simbolo_izquierdo)).bandera ){
      token = lexic.obtener_token();
      if(token == Tokens.FLECHA){
        if(LadosDer(r.simbolo)){
          return true;
        }
      }
    }
    return false;
  }

  
  public Referencia LadoIzq(String s){
    int token = -1;
    token = lexic.obtener_token();
    Referencia r_1 = new Referencia();
    
    if(token == Tokens.SIMBOLO){
      s = lexic.get_yytext().substring(0, lexic.get_yytext().length());
      r_1.simbolo = s;
      r_1.bandera = true;      
      return r_1;
    }
    r_1.bandera = false;      
    return r_1;
  }

  public boolean LadosDer(String simbolo_izq){
    Nodo nodo_simbolo = null;    
    ReferenciaN r_n = null;
    if((r_n = LadoDer(nodo_simbolo)).flag){
      Nodo auxiliar = new Nodo(simbolo_izq, true, r_n.nodo_s);
      lista_lista.add(auxiliar);
      simbolos_no_terminales.add(simbolo_izq);
      if(LadosDer_p(simbolo_izq)){
        return true;
      }
    }
    return false;
  }

  public boolean LadosDer_p(String s){
    int token = -1;
    token = lexic.obtener_token();
    Nodo nodo_simbolo = null;
    ReferenciaN r_n = null;
    if(token == Tokens.OR){
      if((r_n = LadoDer(nodo_simbolo)).flag){
        Nodo auxiliar = new Nodo(s, true, r_n.nodo_s);
        lista_lista.add(auxiliar);
        if(LadosDer_p(s)){
          return true;
        }
      }
      return false;
    }

    lexic.regresarToken();
    return true;
  }

  public ReferenciaN LadoDer(Nodo nodo_simbolo){
    ReferenciaN r_n = null; 
    if((r_n = ListaSimbolos(nodo_simbolo)).flag){
      r_n.flag = true;
      //r_n.nodo_s = nodo_simbolo;
      return r_n;
    }
    r_n.flag = false;
    return r_n;
  }

  public ReferenciaN ListaSimbolos(Nodo nodo_simbolo){
    
    int token = -1;
    Nodo nodo_aux = null;
    token = lexic.obtener_token();
    ReferenciaN r_n = null;
    if(token == Tokens.SIMBOLO){
        nodo_simbolo = new Nodo(lexic.get_yytext().substring(0, lexic.get_yytext().length()), true, nodo_aux);        
      if((r_n = ListaSimbolos_p(nodo_aux)).flag){
          nodo_simbolo.nodo_1 = r_n.nodo_s;
          r_n.flag = true;
          r_n.nodo_s = nodo_simbolo;
          return r_n;
      }
    }
    r_n = new ReferenciaN();
    r_n.flag = false;
    return r_n;
  }

  public ReferenciaN ListaSimbolos_p(Nodo nodo_simbolo){
    int token = -1;
    Nodo nodo_aux = null;
    token = lexic.obtener_token();
    ReferenciaN r_n = null;
    if(token == Tokens.SIMBOLO){
      nodo_simbolo = new Nodo(lexic.get_yytext().substring(0, lexic.get_yytext().length()), true, nodo_aux);
      if((r_n = ListaSimbolos_p(nodo_aux)).flag){
          nodo_simbolo.nodo_1 = r_n.nodo_s;
          r_n.flag = true;
          r_n.nodo_s = nodo_simbolo;
          return r_n;
      }
      r_n.flag = false;
      return r_n;
    }
    nodo_simbolo = null;    
    lexic.regresarToken();
    r_n = new ReferenciaN();
    r_n.nodo_s = nodo_simbolo;    
    r_n.flag = true;
    return r_n;
  }
    
  public ArrayList<Nodo> getListaLista(){      
      return lista_lista;
  }
  
  public Set<String> get_conj_no_terminales(){
      return simbolos_no_terminales;
  }
    
  public Set<String> get_conj_terminales(){
      return simbolos_terminales;
  }
  
  public void encender_banderas(){
      for(Nodo n: lista_lista){
          for(String s1: get_conj_no_terminales()){
              if(s1.equals(n.simbolo)){
                  n.no_terminal = true;
              }
          }
          boolean bandera_pasado = false;
          
         
        Nodo n1 = n.nodo_1;       
        while(n1 != null){ 
             for(String s1: get_conj_no_terminales()){
                if(n1.simbolo.equals(s1)){
                    bandera_pasado = true;
                    break;
                }else{
                    bandera_pasado = false;                  
                }                
            }
            if(bandera_pasado == true && n1 != null){
                n1.no_terminal = true;
                
            }else if(bandera_pasado == false && n1 != null){
                n1.no_terminal = false;   
                if(!n1.simbolo.equals("ë"))
                    simbolos_terminales.add(n1.simbolo);
            }
            n1 = n1.nodo_1;
        }  
      }
  }

}
