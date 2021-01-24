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
package com.eycr.lexic;

import com.eycr.automaton.TableAFD;
import com.eycr.utilities.Const;
import java.util.Stack;

/*
    Clase para el analizador lexico
    Permite trabajar todo el analizador léxico para el proyecto    
*/

public class LexicAnalyzer {
    private Stack<Integer> p;
    private TableAFD afdTable;
    String s;
    Boolean hadAcceptedState;
    Integer token;
    Integer actualCharacter;
    Integer iniLexeme;
    Integer lastLexeme;
    private String lexeme;
    /*
        Constructor para el analizador léxico a partir de los siguientes parametros
        @param s: Cadena a analizar
        @param afdTabla: Tabla de AFD 
        @param p: Pila que almacena los carácteres 
        @param actualCharacter: Carácter en el que se encuentra
        @param iniLexeme: Inicio del lexema
        @param lastLexeme: Fin del lexema        
        Recibe cadena y tabla AFD
        @see AFD
        
        @return No hay
    */
    public LexicAnalyzer(String cad){
        s=cad+"$\0";
        p=new Stack<>();
        actualCharacter=0;
        iniLexeme=0;
        lastLexeme=0;
    }
    
    public LexicAnalyzer(String cad,TableAFD afdTable)
    {
        s=cad+"$\0";
        this.afdTable=afdTable;
        p=new Stack<>();
        actualCharacter=0;
        iniLexeme=0;
        lastLexeme=0;
    }
    /*
        2o Posible constructor para el analizador léxico a partir de los siguientes parametros
        @param s: Cadena a analizar
        @param afdTabla: Tabla de AFD 
        @param p: Pila que almacena los carácteres 
        @param actualCharacter: Carácter en el que se encuentra
        @param iniLexeme: Inicio del lexema
        @param lastLexeme: Fin del lexema        
        @param token: Valor que se le da al símbolo
        @param lexeme: Lexema que se analiza
        @param hadAcceptedState: Bandera que indica si tenía estado de aceptación
        Recibe pila p, afdTable, cadena s, hadAcceptedState, token, actualCharacter, iniLexeme, LastLexeme y lexeme
        @see AFD
        
        @return No hay
    */
    public LexicAnalyzer(Stack<Integer> p, TableAFD afdTable, String s, Boolean hadAcceptedState, Integer token, Integer actualCharacter, Integer iniLexeme, Integer lastLexeme, String lexeme) {
        this.p = p;
        this.afdTable = afdTable;
        this.s = s;
        this.hadAcceptedState = hadAcceptedState;
        this.token = token;
        this.actualCharacter = actualCharacter;
        this.iniLexeme = iniLexeme;
        this.lastLexeme = lastLexeme;
        this.lexeme = lexeme;
    }
    /*
        Método que solicita el token (valor tipo int)
        @params tok: Valor entero del token
    */
    public Integer getToken()
    {
        Integer tok=yyLex();//REVISAR
        //System.out.println("Analiza con "+tok+" y lexema "+lexeme);
        return tok;
    }
    /*
        Método que regresa el token solicitado
    */
    public void undoYylex()
    {
        actualCharacter=p.pop();
    }
    public Character getLexeme(Integer i)
    {
        return 'a';
    }
    /*
        Método que se encarga de buscar el token en la tabla de AFD el carácter analizado
        @return 0 si no hay nada, cualquier número si encuentra el token del carácter analizado
        
    */
    public Integer yyLex()
    {
        Integer actualState=0;
        hadAcceptedState=false;
        System.out.println("Caracter actual yyLex: " + actualCharacter);
        if(s.charAt(actualCharacter)=='\0')
        {
            
            return 0;
        }
        p.push(actualCharacter);
        iniLexeme=actualCharacter;
        while(s.charAt(actualCharacter)!='\0')
        {
            Integer indiceAlfabeto=-1;
            if(afdTable.getSymbols().containsKey(s.charAt(actualCharacter)))
            {
                indiceAlfabeto=afdTable.getSymbols().get(s.charAt(actualCharacter));
            }
            if(indiceAlfabeto==-1)
            {
                return asteriscoVerde();
            }
             //System.out.print("EVALUA '"+s.charAt(actualCharacter)+"' con i="+actualState+" y j="+indiceAlfabeto);
            actualState=afdTable.getTableTransition()[actualState][indiceAlfabeto];
            //System.out.println(" y nos da:"+actualState);
            if(actualState!=-1)
            {
                int x=afdTable.getTableTransition()[actualState][afdTable.getSymbols().size()];
                if(x!=-1)
                {
                    
                    token=x;
                    hadAcceptedState=true;
                    lastLexeme=actualCharacter;
                    //System.out.println("LEXEMA DETECTADO: "+s.substring(iniLexeme,lastLexeme+1)+ " CON TOKEN "+token);
                }
                actualCharacter++;               
            }
            else
            {                                
                return asteriscoVerde();
            }
        }
        return 0;
    }
    /*
        Método que valida si no había un estado de aceptación previo
        @return ERROR o token Si no existía un estado de aceptación previo
    */
    private Integer asteriscoVerde()
    {
        if(!hadAcceptedState)
        {
            lexeme=s.substring(actualCharacter, actualCharacter+1);
            actualCharacter=++iniLexeme;
            return Const.ERROR;
        }
        lexeme=s.substring(iniLexeme,lastLexeme+1);
        actualCharacter=++lastLexeme;
        return token;
    }
    public LexicAnalyzer getStatus()
    {
        return new LexicAnalyzer(p, afdTable, s, hadAcceptedState, token, actualCharacter, iniLexeme, lastLexeme, lexeme);
    }
    public void setStatus(LexicAnalyzer lexic)
    {
        this.p=lexic.getP();
        this.afdTable=lexic.getTableAFD();
        this.s=lexic.getS();
        this.hadAcceptedState=lexic.getHadAcceptedState();
        this.token=lexic.token;
        this.actualCharacter=lexic.getActualCharacter();
        this.iniLexeme=lexic.getIniLexeme();
        this.lastLexeme=lexic.getLastLexeme();
        this.lexeme=lexic.getLexeme();
    }

    public Stack<Integer> getP() {
        return p;
    }

    public void setP(Stack<Integer> p) {
        this.p = p;
    }

    public TableAFD getTableAFD() {
        return afdTable;
    }

    public void setTableAFD(TableAFD afdTable) {
        this.afdTable = afdTable;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Boolean getHadAcceptedState() {
        return hadAcceptedState;
    }

    public void setHadAcceptedState(Boolean hadAcceptedState) {
        this.hadAcceptedState = hadAcceptedState;
    }

    public Integer getActualCharacter() {
        return actualCharacter;
    }

    public void setActualCharacter(Integer actualCharacter) {
        this.actualCharacter = actualCharacter;
    }

    public Integer getIniLexeme() {
        return iniLexeme;
    }

    public void setIniLexeme(Integer iniLexeme) {
        this.iniLexeme = iniLexeme;
    }

    public Integer getLastLexeme() {
        return lastLexeme;
    }

    public void setLastLexeme(Integer lastLexeme) {
        this.lastLexeme = lastLexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }
    
}
