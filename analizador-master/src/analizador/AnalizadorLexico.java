package analizador;

//@author Isaac

import java.util.Stack;

 
public class AnalizadorLexico implements Cloneable  {
    private String cadena;        
    private int ini_lexema;
    private int fin_lexema;    
    private int car_actual = 0;    
    private AFD afd = null;        
    private String yytext = "";
    private Stack<Integer> stack = new Stack();

    
    public AnalizadorLexico(AFD afd, String cadena){    
        this.cadena = cadena;
        this.afd = afd;        
    }
    
    public AnalizadorLexico(AFD afd){
        this.afd = afd;
    }
    
    private int estado_transicion(int fila, char simbolo){
        int id_si = -1;
        int columna = -1;
        for(int i = 0; i < afd.getAlfabeto().length; i++)
            if(afd.getAlfabeto()[i] == simbolo) //El simbolo se encuentra en el alfabeto
                columna = i;
        
        if(columna != -1){
            id_si = afd.getTabla().get(fila).get(columna);
            return id_si;
        }                    
        return -1;
    }
    
    public int obtener_token(){
        
        int token = 0;
        int fila_actual = 0;
        int fila_proxim = 0;
        
        ini_lexema = car_actual;        
        fin_lexema = -1;
        yytext = "";
        
        
        boolean estado_aceptacion_visto = false;
                
        if(car_actual == cadena.length()) {
            return 0;
        }
        
        while(car_actual < cadena.length()) {            
            
            //fila_proxim = ( estado_transicion( fila_actual, cadena.charAt(car_actual) ) ); //Verificamos que exista transicion

            if( (fila_proxim = estado_transicion( fila_actual, cadena.charAt(car_actual) ) ) != -1 ){ //En la tabla, tenemos transición con ese simbolo                                                               
                
                fila_actual = fila_proxim;
                car_actual = car_actual + 1;  
                                
                if(afd.getTabla().get(fila_actual).get(afd.getTabla().get(fila_actual).size()-1) != 0){ //Verificamos que en la fila, la ultima columna tenga un numero != de 0
                    stack.push(car_actual);
                    estado_aceptacion_visto = true;                    
                    token = afd.getTabla().get(fila_actual).get(afd.getTabla().get(fila_actual).size()-1);                         
                    fin_lexema = car_actual;                
                }
                
            }else{
                if (!estado_aceptacion_visto) {
                    //Existe un error
                    car_actual = ini_lexema + 1;    
                    fila_actual = fila_proxim = 0;
                    return -10;
                }else{
                    for(int i = ini_lexema; i < fin_lexema; i++)
                        yytext = yytext + "" + cadena.charAt(i);                    
                    //Return yytext?
                    System.out.println("yytext = " + yytext);
                    
                    //analisis continua en fin_lex + 1
                    car_actual = fin_lexema;
                    
                    return token;
                }
            }            
        }
        return token;
    }
    
    public void regresarToken(){        
        if(!stack.empty()){
            stack.pop();
            if(!stack.empty())
                car_actual = stack.peek();    
        }
    }
    
    public String get_yytext(){
        //String j = "\\* --";
        if(yytext.length() > 1)
            /*if(yytext.contains(">")){
                yytext.substring(0, yytext.length()-1);
            }*/
        yytext = yytext.substring(1);
        return yytext;
    }
    
    public void setCadena(String cadena){
        this.cadena = cadena;
    }    
    public Object clone() throws CloneNotSupportedException { 
        return super.clone(); 
    } 
    
}