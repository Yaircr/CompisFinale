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
package com.eycr.gui;


import com.eycr.automaton.AFD;
import com.eycr.automaton.AFN;
import com.eycr.automaton.Converter;
import com.eycr.automaton.InterfaceAFN;
import com.eycr.automaton.PlaceState;
import com.eycr.regex.ERAutomataEnhanced_TESTING;
import com.eycr.lexic.LexicAnalyzer;
import com.eycr.utilities.IOops;
import com.eycr.utilities.Special;
import com.eycr.grammatics.ERGrammar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
    Clase para mostrar el menu
*/
public class ConsoleMenu {
    private HashMap<Integer,AFN> afns; //HASH de AFNS
    private AFD afd;    //AFD
    
/*
    Constructor del menu de consola
    Crea el Hash Map para los AFN'S
*/    
    public ConsoleMenu(){
        afns=new HashMap<>();
        
    }
/*
    Metodo ejecutable
    Se crea menu y posteriormente se muestra
*/    
    public static void main(String[] args)
    {
        ConsoleMenu menu=new ConsoleMenu();
        menu.show();
    }
/*
    Metodo para mostrar el Menu
*/
    public void show()
    {
        Scanner sc=new Scanner(System.in);
        AFN afn1,afn2;
        while(true)
        {
            System.out.println("1.-Crear un afn");
            System.out.println("2.-Unir afns");
            System.out.println("3.-Concatenar afns");
            System.out.println("4.-Cerradura +");
            System.out.println("5.-Cerradura *");
            System.out.println("6.-Operador opcional");
            System.out.println("7.-Convertir a AFD");
            System.out.println("8.-Analizar cadena con AFD");
            System.out.println("9.-Crear automata a partir de E.R.");
            System.out.println("10.-Unir todos los automatas");

            switch(sc.nextInt())
            {
                case 1:
                    System.out.println("Introduce un caracter");
                    AFN afn=new AFN(sc.next().charAt(0));
                    System.out.println("AFN "+afn.getId()+" creado");
                    
                    afns.put(afn.getId(),afn);
                    asignarTokenAutomata(afn);
                    System.out.println(afn.toString());
                    break;
                case 2:
                    System.out.println("Selecciones los AFN a unir");
                    System.out.println("**AFN 1**");
                    afn1=selectAutomata();
                    System.out.println("**AFN 2**");
                    afn2=selectAutomata();
                    afn1.addAFN(afn2);
                    System.out.println("AFN "+afn1.getId()+" unido con "+afn2.getId());
                    afns.remove(afn2.getId());
                    System.out.println(afn1.toString());
                    break;
                case 3:
                    System.out.println("Selecciones los AFN a concatenar");
                    System.out.println("**AFN 1**");
                    afn1=selectAutomata();
                    afn1.associateToken(-1);
                    System.out.println("**AFN 2**");
                    afn2=selectAutomata();
                    afn1.concatenateAFN(afn2);
                    System.out.println("AFN "+afn1.getId()+" concatenado con "+afn2.getId());
                    afns.remove(afn2.getId());
                    System.out.println(afn1.toString());
                    break;
                case 4:
                    System.out.println("**Escoge AFN para cerradura positiva**");
                    afn1=selectAutomata();
                    afn1.positiveClosure();
                    System.out.println("AFN  "+afn1.getId()+" con cerradura positiva");
                    System.out.println(afn1.toString());
                    break;
                case 5:
                    System.out.println("**Escoge AFN para cerradura de Kleen**");
                    afn1=selectAutomata();
                    afn1.kleenClosure();
                    System.out.println("AFN  "+afn1.getId()+" con cerradura de Kleen");
                    System.out.println(afn1.toString());
                    break;
                case 6:
                    System.out.println("**Escoge AFN para opearador opcional**");
                    afn1=selectAutomata();
                    afn1.optional();
                    System.out.println("AFN  "+afn1.getId()+" con operador opcional");
                    System.out.println(afn1.toString());
                    break;
                case 7:
                    System.out.println("**Escoge un AFN para convertirlo**");
                    afn1=selectAutomata();
                    /*System.out.println("PlaceStates:");
                    for(PlaceState s: afn1.generatePlaceStates()){
                        System.out.println(s.toString());
                    }*/
                    Converter afnConverter = new Converter();
                    afd=afnConverter.convertAFN(afn1);
                    afd.getTable().print();
                    
                    break;
                case 8:
                    System.out.println("**Analiza una cadena**");
                    System.out.println("Ingresa  una cadena:");
                    String cad=sc.next();
                    System.out.println("Cadena="+cad);
                    afd.getTable().print();
                    if(afd.analizeString(cad)){
                        System.out.println("La caena es valida");
                    }else{
                        System.out.println("La caena NO es valida");
                    }
                    LexicAnalyzer lexic2=new LexicAnalyzer(cad,afd.getTable());
                    while(true)
                    {
                        Integer yyLexValue=lexic2.yyLex();
                        if(yyLexValue==0)
                        {
                            break;
                        }

                        System.out.println("yyLex:"+yyLexValue+" con lexema "+lexic2.getLexeme());
                    }
                    break;
                case 9:
                    IOops io=new IOops();
                    System.out.println("Proporciona una expresión de regular");
                    String s=sc.next();
                    ERAutomataEnhanced_TESTING erA=new ERAutomataEnhanced_TESTING();
                    LexicAnalyzer lexic=new LexicAnalyzer(s,erA.getAfd().getTable());
                    ERGrammar erG=new ERGrammar(lexic);
                    AFN f=new AFN();
                    erG.E(f);
                    afns.put(f.getId(), f);
                    System.out.println("Ingresa un token para este automata =)");
                    f.associateToken(io.askForToken());
                    break;
                case 10:
                    Special us=new Special();
                    us.unir(afns);
                    
                    System.out.println("Se unieron todos los AFNs generados");
                    break;
                default: System.exit(0);
            }    
        }
    }
    
    public AFN selectAutomata()
    {
        Scanner sc=new Scanner(System.in);
        afns.forEach((k,v)->System.out.println("Automata "+k));
        System.out.println("Selecciones un indice:");
        return afns.get(sc.nextInt());
    }
    public void asignarTokenAutomata(AFN afn){
        IOops io=new IOops();
        System.out.println("Seleccione el automata a agregar el token");
        selectAutomata().associateToken(io.askForToken());
    }
    
}
