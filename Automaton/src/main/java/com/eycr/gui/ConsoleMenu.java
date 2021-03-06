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
import com.eycr.calculadora.InterfaceCalcPost;
import com.eycr.calculadora.analizadorSintactico;
import com.eycr.calculadora.sintaxCalculatorPost;
import com.eycr.regex.ERAutomataEnhanced_TESTING;
import com.eycr.lexic.LexicAnalyzer;
import com.eycr.utilities.IOops;
import com.eycr.utilities.Special;
import com.eycr.grammatics.ERGrammar;
import com.eycr.calculadora.ERAutomataCalculadora;

import com.eycr.utilities.readFile;
import java.io.File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            System.out.println("11.-Crear automata a partir de E.R desde un archivo.");
            System.out.println("12.-LL1");

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
                        System.out.println("La cadena es valida");
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
                    f.toString();
                    System.out.println("Ingresa un token para este automata =)");
                    f.associateToken(io.askForToken());
                    
                    break;
                case 10:
                    Special us=new Special();
                    us.unir(afns);
                    
                    System.out.println("Se unieron todos los AFNs generados");
                    break;
                    
                case 11:
                    String path;
                    String carpeta = "..\\Automaton\\Expresiones y gramaticas";
                    readFile archivo = new readFile();
                    File carpetaLista = new File(carpeta);
                    String[] listado = carpetaLista.list();
                    if (listado == null || listado.length == 0) {
                        System.out.println("No hay elementos dentro de la carpeta actual");
                        return;
                    }
                    else {
                        System.out.println("Archivos disponibles: ");
                        for (String listado1 : listado) {
                            System.out.println(listado1);
                        }
                    }
                    System.out.println("¿Qué archivo quiere leer?");
                    Scanner sc1 = new Scanner(System.in);
                    path = sc1.nextLine();
                    List<String> renglones = new ArrayList<String>();  
                    renglones = archivo.lineasArchivoLista(path);
                    //System.out.println(renglones);
                    try{
                        /*MANEJO DE ARCHIVO PARA REGEX*/
                         AFN ffile=new AFN();
			for(int i = 0; i < renglones.size(); i++){
                               
                                /*SI ES LINEA IMPAR ES REGEX*/
                                if(i%2 == 0){
                                    String cadenaRegex = renglones.get(i).toString();
                                    ERAutomataEnhanced_TESTING erFF=new ERAutomataEnhanced_TESTING();
                                    LexicAnalyzer lexicFF=new LexicAnalyzer(cadenaRegex,erFF.getAfd().getTable());
                                    ERGrammar erGFF=new ERGrammar(lexicFF);
                                    
                                    erGFF.E(ffile);
                                    afns.put(ffile.getId(), ffile);
                                }else{
                                    /*SEGUNDA LINEA LEIDA CORRESPONDE A SU TOKEN*/
                                    int tok = Integer.valueOf(renglones.get(i).toString());
                                    ffile.associateToken(tok);
                                    ffile=new AFN();
                                }
                                
			}
			
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Special usff=new Special();
                    usff.unir(afns);
                    
                    System.out.println("Se unieron todos los AFNs generados");
                    break;    
                /*case 12:
                    System.out.println("**Calculadora**");
                    System.out.println("Ingresa operación");
                    String cad2=sc.next();
                    System.out.println("Cadena="+cad2);
                    afd.getTable().print();
                    sintaxCalculatorPost sintaxCalc;
                    sintaxCalc = new sintaxCalculatorPost(afd.getTable(), cad2);
                    InterfaceCalcPost Result = new InterfaceCalcPost("0",false);
                    Result = sintaxCalc.E(cad2);
                    if(Result.flag){
                        System.out.println("El resultado es: "+Result.res);
                    }else{
                        System.out.println("La cadena NO es valida");
                    }
                    
                    break;*/
                    
                case 13:
                    String path2;
                    String carpeta2 = "..\\Automaton\\Expresiones y gramaticas";
                    readFile archivo2 = new readFile();
                    File carpetaLista2 = new File(carpeta2);
                    String[] listado2 = carpetaLista2.list();
                    if (listado2 == null || listado2.length == 0) {
                        System.out.println("No hay elementos dentro de la carpeta actual");
                        return;
                    }
                    else {
                        System.out.println("Archivos disponibles: ");
                        for (String listado3 : listado2) {
                            System.out.println(listado3);
                        }
                    }
                    System.out.println("¿Qué archivo quiere leer?");
                    Scanner sc2 = new Scanner(System.in);
                    path = sc2.nextLine();
                    List<String> renglones2 = new ArrayList<String>();  
                    renglones2 = archivo2.lineasArchivoLista(path);
                    //System.out.println(renglones);
                    try{
                        /*MANEJO DE ARCHIVO PARA REGEX*/
                         AFN ffile=new AFN();
			for(int i = 0; i < renglones2.size(); i++){
                               
                                /*SI ES LINEA IMPAR ES REGEX*/
                                if(i%2 == 0){
                                    String cadenaRegex = renglones2.get(i).toString();
                                    ERAutomataCalculadora erFF=new ERAutomataCalculadora();
                                    LexicAnalyzer lexicFF=new LexicAnalyzer(cadenaRegex,erFF.getAfd().getTable());
                                    ERGrammar erGFF=new ERGrammar(lexicFF);
                                    
                                    erGFF.E(ffile);
                                    afns.put(ffile.getId(), ffile);
                                }else{
                                    /*SEGUNDA LINEA LEIDA CORRESPONDE A SU TOKEN*/
                                    int tok = Integer.valueOf(renglones2.get(i).toString());
                                    ffile.associateToken(tok);
                                    ffile=new AFN();
                                }
                                
			}
			
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Special usff2=new Special();
                    usff2.unir(afns);
                    afns.toString();
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
