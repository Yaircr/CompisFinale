package analizador;

import LL1.TablaLL1;
import graphviz.GraphViz;
import java.io.File;
import java.util.*;
/* @author yax */

public class Analizador {
        
    static Scanner sc = new Scanner(System.in);
    
    public static int menu(){
        int opc = 0;
        
        System.out.println("********************** Analizador LEXICO **********************");
        System.out.println("\n1. Crear AFN básico \n2. Unir 2 AFN \n3. Concatenar AFNs \n4. Cerraduras \n5. Opcional \n6. Union para Analizador Lexico \n7. Convertir un AFN a AFD"
                + "\n8. Validar cadena \n9. Crear AFN con E.R \n10. Exit");
        System.out.print("\n\t > ");
        
        try {
            opc = sc.nextInt();            
        } catch (Exception e) {
            System.out.println("Se debe ingresar una opción con un número.");
            System.exit(0);
        }
        System.out.println("");
        return opc;        
    }
    
    public static void main(String[] args) {
        Map<Integer, AFN> afns = new HashMap<>(); //Aquí se guardan los AFNS que el usuario cree; tiene la forma {id_AFN : AFN} = {llave : valor}
        Map<Integer, AFD> afds = new HashMap<>(); //Aquí se guardan los AFDS que el usuario cree; tiene la forma {id_AFD : AFD} = {llave : valor}
        int acumKeysAFDs = 0;
         
        while(true){
            switch(menu()){

                case 1: //Generar básico

                    int id_afn = 0;
                    String simbolo_1 = "";    
                    String simbolo_2 = "";    

                    System.out.print("Ingresa el símbolo 1 para generar el AFN básico\n\t > ");
                    simbolo_1 = sc.next();
                    
                    System.out.print("Ingresa el símbolo 2 para generar el AFN básico\n\t > ");
                    simbolo_2 = sc.next();

                    if(simbolo_1.length() == 1 && simbolo_2.length() == 1){
                        /*System.out.print("Ingresa el id ÚNICO para el AFN básico\n\t > ");

                        try {
                            id_afn = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("El id debe de ser númerico");                                                
                            break;                        
                        }

                        if(id_afn == 666){
                            System.out.println("Id exclusivo//no se puede usar");
                            break;
                        }else */ 
                        //if(!afns.containsKey(id_afn)){
                            id_afn = AFN.idSig;
                            AFN afn_1 = AFN.CrearBasico(simbolo_1.charAt(0), simbolo_2.charAt(0));
                            afns.put(id_afn, afn_1);
                            System.out.println("AFN creado con éxito.");
                        //}else {
                          //  System.out.println("Ya has creado un AFN con ese id.");
                            //break;
                        //}
                    }else{
                        System.out.println("la longitud supera el digito requerido");
                    }                                                           
                break;

                case 2: //Unir 2 afns 
                    int opc_1 = 0;
                    int opc_2 = 0;

                    //afns.put(1, AFN.CrearBasico('a', 1));
                    //afns.put(2, AFN.CrearBasico('b', 2));                


                    System.out.print("\n\n****************** ADVERTENCIA ******************\n");
                    System.out.println("Unir dos AFNs no generará copias de uno de ellos.");
                    System.out.println("El resultado será escrito sobre el primer automata seleccionado");
                    System.out.print("*************************************************\n");

                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());         

                    System.out.print("\nIngresa el ID del primer AFN a unir \n\t> ");
                    try {
                        opc_1 = sc.nextInt();                    
                    } catch (Exception e) {
                        System.out.println("El id debe de ser númerico");
                        break;
                        //Reintentar / salir?
                    }
                    System.out.print("Ingresa el ID del segundo AFN a unir \n\t> ");
                    try {
                        opc_2 = sc.nextInt();                    
                    } catch (Exception e) {
                        System.out.println("El id debe de ser númerico");
                        break;
                        //Reintentar / salir?
                    }

                    if(!afns.containsKey(opc_1) || !afns.containsKey(opc_2)){
                        System.out.println("Alguno de los 2 id's no existe. Imposible unirlos");
                        break;
                    }

                    try {
                        afns.get(opc_1).UnirAFN(afns.get(opc_2)); //El resultado prevalece en afn.get(opc_1)
                        afns.remove(opc_2);
                        for(AFN a : afns.values()){
                            a.test();
                        }
                    } catch (Exception e) {
                        System.err.println("ERROR AL UNIR LOS 2 AFNS.");
                        break;
                    }
                    System.out.println("Automatas unidos correctamente.");

                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN restantes                 
                        //entry.getValue().test();
                        System.out.println(entry.getKey() + "    " + entry.getValue());         

                break;

                case 3://Concatenacion
                    opc_1 = 0;
                    opc_2 = 0;

                    //afns.put(1, AFN.CrearBasico('a', 1));
                    //afns.put(2, AFN.CrearBasico('b', 2));                

                    System.out.print("\n\n****************** ADVERTENCIA ******************\n");
                    System.out.println("Concatenar dos AFNs no generará copias de uno de ellos.");
                    System.out.println("El resultado será escrito sobre el primer automata seleccionado");
                    System.out.print("*************************************************\n");

                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());         

                    System.out.print("\nIngresa el ID del primer AFN a concatenar \n\t> ");
                    try {
                        opc_1 = sc.nextInt();                    
                    } catch (Exception e) {
                        System.out.println("El id debe de ser númerico");
                        break;
                        //Reintentar / salir?
                    }
                    System.out.print("Ingresa el ID del segundo AFN a concatenar \n\t> ");
                    try {
                        opc_2 = sc.nextInt();                    
                    } catch (Exception e) {
                        System.out.println("El id debe de ser númerico");
                        break;
                        //Reintentar / salir?
                    }

                    if(!afns.containsKey(opc_1) || !afns.containsKey(opc_2)){
                        System.out.println("Alguno de los 2 id's no existe. Imposible concatenarlos");
                        break;
                    }

                    try {
                        //3 & 1
                        afns.get(opc_1).ConcatAFN(afns.get(opc_2)); //El resultado prevalece en afn.get(opc_1)
                        afns.remove(opc_2);
                    } catch (Exception e) {
                        System.err.println("ERROR AL CONCATENAR LOS 2 AFNS.");
                        break;
                    }
                    System.out.println("Automatas concatenados correctamente.");

                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN restantes                 
                        //entry.getValue().test();
                        System.out.println(entry.getKey() + "    " + entry.getValue());
                break;

                case 4: //Cerraduras
                    opc_1 = 0;
                    int opc_cer = 0;

                    System.out.println("\n1. Transitiva \n2. Kleene");
                    System.out.print("\n\t > ");
                    try {
                        opc_cer = sc.nextInt();                    
                    } catch (Exception e) {
                        System.out.println("Caracter NO numérico.");
                        break;
                    }

                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());

                    if (opc_cer == 1) {
                        System.out.println("Ingresa el ID del automáta al que quieres aplicarle la operación.");
                        System.out.print("\n\t > ");
                        opc_1 = sc.nextInt();

                        if(!afns.containsKey(opc_1)){
                            System.err.println("El ID que ingresaste no existe en los automatas previamente creados");
                            break;
                        }

                        try {
                            afns.get(opc_1).CerraduraTrans(); //Aplicamos la cerradura Trasitiva
                        } catch (Exception e) {
                            System.err.println("ERROR AL APLICAR CERRADURA TRANSITIVA");
                            break;
                        }

                        System.out.println("Cerradura transitiva aplicada correctamente.");                    
                    }else if(opc_cer == 2){

                        System.out.println("Ingresa el ID del automáta al que quieres aplicarle la operación.");
                        System.out.print("\n\t > ");
                        opc_1 = sc.nextInt();

                        if(!afns.containsKey(opc_1)){
                            System.err.println("El ID que ingresaste no existe en los automatas previamente creados");
                            break;
                        }

                        try {
                            afns.get(opc_1).CerraduraKleene();//Aplicamos la cerradura de Kleene
                            for(AFN a : afns.values()){
                                a.test();
                            }
                        } catch (Exception e) {
                            System.err.println("ERROR AL APLICAR CERRADURA DE KLEENE");
                            break;
                        }

                        System.out.println("Cerradura de Kleene aplicada correctamente.");      

                    }else{
                        System.out.println("Opción inválida.");
                        break;                    
                    }
                break;

                case 5: //Opcional
                    opc_1 = 0;
                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());

                    System.out.println("Ingresa el ID del automata al que quieres aplicarle el operador opcional");
                    System.out.print("\n\t > ");
                    try {
                        opc_1 = sc.nextInt();                                        
                    } catch (Exception e) {
                        System.err.println("La opción debe de ser numérica.");
                        break;
                    }

                    if(!afns.containsKey(opc_1)){
                        System.err.println("El ID que ingresaste no existe en los automatas que has generado.");
                        break;
                    }

                    try {
                        afns.get(opc_1).Opcional();
                    } catch (Exception e) {
                        System.err.println("ERROR AL APLICAR EL OPERADOR OPCIONAL");
                        break;
                    }                
                    System.out.println("Operador opcional aplicado correctamente");
                break;

                case 6: //Unir AFNS para convertir a AFD
                    opc_1 = 0;
                    String ids = "";
                    String[] array_ids = null;
                    ArrayList<AFN> afns_a_convertir = new ArrayList();

                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());

                    System.out.println("Ingresa los IDs de los automatas que quieres unir SEPARADOS por UNA COMA");    
                    System.out.println("EJEMPLO: 1,2,3,4,5");
                    System.out.println("El resultado quedara escrito en el primer automata descrito, los demas seran eliminados");
                    System.out.print("\n\t > ");

                    try {
                        ids = sc.next();                                        
                        array_ids = ids.split(",");
                    } catch (Exception e) {
                        System.err.println("ERROR AL INTENTAR LEER LOS IDS");
                        break;
                    }

                    for (String y : array_ids) 
                        System.out.println(y);

                    for (String y : array_ids) {                    
                        if(!afns.containsKey(Integer.parseInt(y))){
                            System.out.println("El id " + y + " no existe en los automatas previamente creados");
                            System.out.println("Proceso cancelado");
                            break;
                        }                    
                        afns_a_convertir.add(afns.get(Integer.parseInt(y))); //Agregamos los AFNS a un arraylist                        
                    }
                    
                    opc_1 = Integer.parseInt(array_ids[0]);
                    afns_a_convertir.remove(0);
                    
                    
                    
                                                            
                    try {
                        afns.get(opc_1).UnirAFN(afns_a_convertir); //El resultado prevalece en afn.get(opc_1)                        
                    } catch (Exception e) {
                        System.err.println("ERROR AL UNIR AFNS PARA CONVERTIR A AFD");
                        break;
                    }   
                    for(int i = 1; i < array_ids.length; i++)
                        afns.remove(Integer.parseInt(array_ids[i]));
                break;

                case 7: //Convertir afn a afd
                    opc_1 = -1;
                    int id_afd = 0;
                    AFN afz = new AFN();
                    
                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());

                    System.out.println("Ingresa el ID del automata (AFN) que quieres convertir a AFD");
                    System.out.print("\n\t > ");
                    try {
                        opc_1 = sc.nextInt();                                        
                    } catch (Exception e) {
                        System.err.println("La opción debe de ser numérica.");                    
                        break;
                    }

                    if(!afns.containsKey(opc_1)){
                        System.err.println("El ID que ingresaste no existe en los automatas que has generado.");
                        break;
                    }
                    try {
                       /* System.out.print("Ingresa el id ÚNICO para el nuevo AFD \n\t > ");
                        try {
                            id_afd = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("El id debe de ser númerico");
                            break;
                        }
                        
                        if (!afds.containsKey(id_afd)) {*/
                            id_afd= AFD.idSig;
                            afds.put(id_afd, afz.ConvertirAFD(afns.get(opc_1)));
                            
                            //genGraphAFD(afds.get(id_afd)); prueba de gen de afd
                            
                            System.out.println("AFD creado con éxito.");
                        /*} else {
                            System.out.println("Ya has creado un AFD con ese id.");
                            break;
                        }*/
                    } catch (Exception e) {
                        System.err.println("ERROR AL CONVERTIR AFN A AFD");
                        break;
                    }                
                    System.out.println("Conversión hecha con éxito");
                    afds.get(id_afd).test(); 
                    afds.get(id_afd).imprimir_tabla();
                break;

                case 8: //Validar cadena 
                    opc_1 = -1;
                    int opc_val_c = 0;
                    String cadena_validar = "";

                    System.out.println("\n1. Validar con un AFN \n2. Validar con un AFD");
                    System.out.print("\n\t > ");

                    try {
                        opc_val_c = sc.nextInt();                                        
                    } catch (Exception e) {
                        System.err.println("La opción debe de ser numérica.");                    
                        break;
                    }

                    System.out.println("Ingresa la cadena que deseas validar");
                    System.out.print("\n\t > ");
                    cadena_validar = sc.next();

                    if(opc_val_c == 1){
                        System.out.println("Ingresa el ID del automata (AFN) que quieres usar para validar la cadena");                    
                        System.out.print("\n\t > ");

                        try {
                            opc_1 = sc.nextInt();                                        
                        } catch (Exception e) {
                            System.err.println("La opción debe de ser numérica.");                    
                            break;
                        }

                        if(!afns.containsKey(opc_1)){
                            System.err.println("El ID que elegiste no existe en los automatas que ya has creado.");
                            break;
                        }

                        try {
                            int t = afns.get(opc_1).AnalizarCadena(cadena_validar); //Este metodo regresa un entero, validar eso
                            if(t != 0){
                                System.out.println("LA CADENA " + cadena_validar + " ES VÁLIDA");
                            }else
                                System.out.println("LA CADENA " + cadena_validar + " NO ES VÁLIDA");
                        } catch (Exception e) {
                            System.out.println("ERROR AL VALIDA LA CADENA CON EL AFN");
                            break;
                        }

                    }else if (opc_val_c == 2) {
                        System.out.println("Ingresa el ID del automata (AFD) que quieres usar para validar la cadena");                    
                        System.out.print("\n\t > ");

                        try {
                            opc_1 = sc.nextInt();                                        
                        } catch (Exception e) {
                            System.err.println("La opción debe de ser numérica.");                    
                            break;
                        }

                        if(!afds.containsKey(opc_1)){
                            System.err.println("El ID que elegiste no existe en los automatas que ya has creado.");
                            break;
                        }

                        try {
                            cadena_validar = cadena_validar + '\n';
                            boolean f = afds.get(opc_1).validar_cadena(afds.get(opc_1).getEdo_incial(),cadena_validar , 0);
                            if(f){
                                System.out.println("LA CADENA " + cadena_validar + " ES VÁLIDA");
                            }else{
                                System.out.println("LA CADENA " + cadena_validar + " NO ES VÁLIDA");
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR AL VALIDA LA CADENA CON EL AFD");
                            break;
                        }
                    }else{
                        System.err.println("Opción inválida");
                        break;
                    }                
                break;
                
                /*
                case 9:  
                    
                    /*Crear los afns con los simbolos 

                    ArrayList<Estado> aux = new ArrayList();
                    ArrayList<AFN> aux_afns = new ArrayList();
                    
                    AFN f_1 = AFN.CrearBasico('|', '|'); //OR 
                    aux = f_1.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(10);
                    
                    AFN f_2 = AFN.CrearBasico('&', '&'); //and                    
                    aux = f_2.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(20);
                    
                    
                    AFN f_3 = AFN.CrearBasico('*', '*'); //cerr_klee                    
                    aux = f_3.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(40);
                    
                    AFN f_4 = AFN.CrearBasico('+', '+'); //cerr_tran
                    aux = f_4.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(30);
                    
                    AFN f_5 = AFN.CrearBasico('?', '?'); //opc
                    aux = f_5.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(50);
                    
                    AFN f_6 = AFN.CrearBasico('(', '('); //par_izq
                    aux = f_6.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(60);
                    
                    
                    AFN f_7 = AFN.CrearBasico(')', ')'); //par_der
                    aux = f_7.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(70);
                    
                    
                    AFN f_8 = AFN.CrearBasico('[', '['); //cor_izq
                    aux = f_8.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(80);
                    
                    
                    AFN f_9 = AFN.CrearBasico(']', ']'); //cor_der
                    aux = f_9.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(90);
                    
                    
                    AFN f_10 = AFN.CrearBasico('-', '-'); //guion
                    aux = f_10.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(100);
                                        
                    
                    AFN f_11 = AFN.CrearBasico('a', 'z'); // a-z
                    AFN f_12 = AFN.CrearBasico('A', 'Z'); // A-Z
                    f_11.UnirAFN(f_12);
                    AFN f_13 = AFN.CrearBasico('0', '9'); // 0-9
                    f_11.UnirAFN(f_13);
                    AFN f_14 = AFN.CrearBasico('\\', '\\'); // a-z      
                    AFN f_15 = AFN.CrearBasico((char)32, (char)254); // ascii
                    f_14.ConcatAFN(f_15);
                    f_11.UnirAFN(f_14);
                    
                    aux = f_11.getEdos_aceptacion();
                    for (Estado estado : aux) estado.setToken(120);
                    
                    aux_afns.add(f_2); aux_afns.add(f_3); aux_afns.add(f_4); aux_afns.add(f_5);
                    aux_afns.add(f_6); aux_afns.add(f_7); aux_afns.add(f_8); aux_afns.add(f_9); aux_afns.add(f_10); 
                    aux_afns.add(f_11);
                    
                    f_1.UnirAFN(aux_afns);
                    
                    AFD afd_aux = new AFN().ConvertirAFD(f_1);
                    afd_aux.test();
                    afd_aux.imprimir_tabla();
                    afd_aux.escribirArchivo();
                               

                    int opca= 0;
                    String sDirectorio = "Archivos";
                    File fi = new File(sDirectorio);

                    if (fi.exists()){ 
                        File[] ficheros = fi.listFiles();
                        for(int i=0;i < ficheros.length; i++){
                            System.out.println((i+1)+") "+ficheros[i].getName());
                        }
                        System.out.println("Introduce el numero del archivo a leer");
                        try {
                            opca = sc.nextInt();            
                        } catch (Exception e) {
                            System.out.println("Se debe ingresar una opción numerica");
                            System.exit(0);
                        }

                        if(opca <= ficheros.length && opca > 0){
                            LeerArchivo la = new LeerArchivo();

                            la.Leer(ficheros[opca-1].getName());

                            ArrayList exreg = la.getER();
                            ArrayList tokens = la.getTokens();

                            for(int i = 0; i < exreg.size(); i++){
                                System.out.println(exreg.get(i));
                            }
                            for(int i = 0; i < tokens.size(); i++){
                                System.out.println(tokens.get(i));
                            }
                            
                            
                            
                            AFN creado = null;
                            for(int i = 0; i < exreg.size(); i++){
                                String cadena = (String)exreg.get(i);
                                cadena = cadena + ' ';
                                AnalizadorSintactico sintx = new AnalizadorSintactico(afd_aux, cadena);
                                creado = sintx.init().afn;
                                creado.test();
                                //Poner tokens
                                System.out.println("----------------------------------------------------");
                                cadena = "";
                                genGraphAFN(creado);
                            }
                        } else {
                            System.out.println("Opcion invalida");
                        }
                    }else { 
                        System.out.println("Carpeta no existe");
                    }  
                     
                    /*AFN afn1 = AFN.CrearBasico('L','L',1);
                    AFN afn2 = AFN.CrearBasico('D','D',2);
                    AFN afn3 =  AFN.CrearBasico('L','L',3);
                    afn1.UnirAFN(afn2);
                    afn1.CerraduraKleene();
                    afn3.ConcatAFN(afn1);

                    AFD prueba = afn3.ConvertirAFD(afn3);
                    prueba.imprimir_tabla();
                    prueba.escribirArchivo();
                break;
                */

                case 10: 
                    System.out.println("Adiós.");
                    System.exit(0);
                break;
                
                case 11:
                        AFN obj = new AFN();
                        for (AFN f : afns.values()) {
                            f.test();
                    }
                break;
                
                case 12:
                    //Opcion para asignar tokens a AFNS en un solo movimiento 
                    opc_1 = 0;
                    int token_asignado = 0;
                    
                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());
                    
                    System.out.println("Ingresa el ID del automata (AFN) al que quieres asignarle un Token a su estado final");
                    System.out.print("\n\t > ");
                    try {
                        opc_1 = sc.nextInt();                                        
                    } catch (Exception e) {
                        System.err.println("La opción debe de ser numérica.");                    
                        break;
                    }
                    
                    
                    ArrayList<Estado> aceptacion = afns.get(opc_1).getEdos_aceptacion();
                    //Como es un afn creado a partir de una expresion regular por el metodo de Thompson solo debe de tener 1 estado de aceptacion                                        
                    System.out.println("Ingresa el token");                                        
                    System.out.print("\n\t > ");
                    try {
                        token_asignado = sc.nextInt();                                        
                    } catch (Exception e) {
                        System.err.println("La opción debe de ser numérica.");                    
                        break;
                    }
                    aceptacion.get(0).setToken(token_asignado);                    
                break;
                
                case 13:
                    //Pruebas para el yytext
                    //DD.DDMD.DPDD.DD
                    int r;
                    opc_1 = 0;
                    String cadena_obtener_token = "";
                    
                    for (Map.Entry<Integer, AFD> entry : afds.entrySet()) //Imprimimos los AFN que se han generado                 
                        System.out.println(entry.getKey() + "    " + entry.getValue());
                    
                    System.out.println("Ingresa el ID del automata (AFD) para usar el YYTEXT");
                    System.out.print("\n\t > ");
                    try {
                        opc_1 = sc.nextInt();                                        
                    } catch (Exception e) {
                        System.err.println("La opción debe de ser numérica.");                    
                        break;
                    }
                    
                    System.out.println("Ingresa la cadena para obtener sus tokens");
                    System.out.print("\n\t > ");
                    cadena_obtener_token = sc.next();
                    
                    AnalizadorLexico lexic = new AnalizadorLexico(afds.get(opc_1), cadena_obtener_token);
                    while( (r = lexic.obtener_token()) != 0){
                        System.out.println("El token obtenido fue: " + r);
                    }
                    System.out.println(r);
                break;
                
                case 14:
                    /**
                     * Pruebas para generar graficas con los afns 
                     * 
                     */
                    opc_1 = -1;
                    id_afd = 0;
                    afz = new AFN();

                    for (Map.Entry<Integer, AFN> entry : afns.entrySet()) //Imprimimos los AFN que se han generado                 
                    {
                        System.out.println(entry.getKey() + "    " + entry.getValue());
                    }

                    System.out.println("Ingresa el ID del automata (AFN) que quieres generar");
                    System.out.print("\n\t > ");
                    try {
                        opc_1 = sc.nextInt();
                    } catch (Exception e) {
                        System.err.println("La opción debe de ser numérica.");
                        break;
                    }

                    if (!afns.containsKey(opc_1)) {
                        System.err.println("El ID que ingresaste no existe en los automatas que has generado.");
                        break;
                    }
                    try {
                        id_afd = AFD.idSig;
                        //afds.put(id_afd, afz.ConvertirAFD(afns.get(opc_1)));
                        genGraphAFN(afns.get(opc_1));
                        System.out.println("AFN creado con éxito.");
                    } catch (Exception e) {
                        System.err.println("ERROR AL GENERAR");
                        break;
                    }
                    System.out.println("Conversión hecha con éxito");
                    //afds.get(id_afd).test();
                    //afds.get(id_afd).imprimir_tabla();
                break;
                
                case 15:  
                    
                    /*Crear los afns con los simbolos */
                    
                    ArrayList<AFN> aux_afns = new ArrayList();
                    
                    AFN f_1 = AFN.CrearBasico('|', '|'); //OR 
                    f_1 = f_1.set_tokens_todos(f_1, 10);
                                        
                    AFN f_2 = AFN.CrearBasico('&', '&'); //and                    
                    f_2 = f_2.set_tokens_todos(f_2, 20);                    
                    
                    
                    AFN f_3 = AFN.CrearBasico('*', '*'); //cerr_klee                    
                    f_3 = f_3.set_tokens_todos(f_3, 40);                                        
                    
                    AFN f_4 = AFN.CrearBasico('+', '+'); //cerr_tran
                    f_4 = f_4.set_tokens_todos(f_4, 30);                   
                    
                    AFN f_5 = AFN.CrearBasico('?', '?'); //opc
                    f_5 = f_5.set_tokens_todos(f_5, 50);                    
                    
                    AFN f_6 = AFN.CrearBasico('(', '('); //par_izq
                    f_6 = f_6.set_tokens_todos(f_6, 60);                                       
                    
                    AFN f_7 = AFN.CrearBasico(')', ')'); //par_der
                    f_7 = f_7.set_tokens_todos(f_7, 70);                                        
                    
                    AFN f_8 = AFN.CrearBasico('[', '['); //cor_izq
                    f_8 = f_8.set_tokens_todos(f_8, 80);                                        
                    
                    AFN f_9 = AFN.CrearBasico(']', ']'); //cor_der
                    f_9 = f_9.set_tokens_todos(f_9, 90);                                       
                    
                    AFN f_10 = AFN.CrearBasico('-', '-'); //guion
                    f_10 = f_10.set_tokens_todos(f_10, 100);
                                                            
                    
                    AFN f_11 = AFN.CrearBasico('a', 'z'); // a-z
                    AFN f_12 = AFN.CrearBasico('A', 'Z'); // A-Z
                    f_11.UnirAFN(f_12);
                    AFN f_13 = AFN.CrearBasico('0', '9'); // 0-9
                    f_11.UnirAFN(f_13);
                    AFN f_14 = AFN.CrearBasico('\\', '\\'); // a-z      
                    AFN f_15 = AFN.CrearBasico((char)32, (char)254); // ascii
                    f_14.ConcatAFN(f_15);
                    f_11.UnirAFN(f_14);
                    
                    f_11 = f_11.set_tokens_todos(f_11, 120);
                    
                    aux_afns.add(f_2); aux_afns.add(f_3); aux_afns.add(f_4); aux_afns.add(f_5);
                    aux_afns.add(f_6); aux_afns.add(f_7); aux_afns.add(f_8); aux_afns.add(f_9); aux_afns.add(f_10); 
                    aux_afns.add(f_11);
                    
                    f_1.UnirAFN(aux_afns);
                    
                    AFD afd_aux = new AFN().ConvertirAFD(f_1);
                    afd_aux.test();
                    afd_aux.imprimir_tabla();
                    afd_aux.escribirArchivo();
                               

                    int opca= 0;
                    String sDirectorio = "Archivos";
                    File fi = new File(sDirectorio);

                    if (fi.exists()){ 
                        File[] ficheros = fi.listFiles();
                        for(int i=0;i < ficheros.length; i++){
                            System.out.println((i+1)+") "+ficheros[i].getName());
                        }
                        System.out.println("Introduce el numero del archivo a leer");
                        try {
                            opca = sc.nextInt();            
                        } catch (Exception e) {
                            System.out.println("Se debe ingresar una opción numerica");
                            System.exit(0);
                        }

                        if(opca <= ficheros.length && opca > 0){
                            LeerArchivo la = new LeerArchivo();

                            la.Leer(ficheros[opca-1].getName());

                            ArrayList exreg = la.getER();
                            ArrayList tokens = la.getTokens();

                            for(int i = 0; i < exreg.size(); i++){
                                System.out.println("Expresiones Regulares: "+exreg.get(i));
                            }
                            for(int i = 0; i < tokens.size(); i++){
                                System.out.println(tokens.get(i));
                            }
                            
                            ArrayList<AFN> afnsa = new ArrayList<>();
                            
                            AFN creado = null;
                            for(int i = 0; i < exreg.size(); i++){
                                String cadena = (String)exreg.get(i);
                                cadena = cadena + ' ';
                                AnalizadorSintactico sintx = new AnalizadorSintactico(afd_aux, cadena);
                                creado = sintx.init().afn;
                                creado.test();
                                //Poner tokens
                                creado = creado.set_tokens_todos(creado, Integer.parseInt(tokens.get(i).toString()));
                                System.out.println("----------------------------------------------------");
                                cadena = "";
                                afnsa.add(creado);
                                //genGraphAFN(creado);
                            }
                            AFN afnx = afnsa.get(0);
                            afnsa.remove(0);
                            afnx.UnirAFN(afnsa);
                            AFD afdx = afnx.ConvertirAFD(afnx);
                            System.out.println("***********************************");
                            System.out.println("Este es el AFD");
                            System.out.println("***********************************");
                            afdx.imprimir_tabla();
                            //ArrayList<String> listaString = new LeerArchivo().Leer2("Expresiones3.txt");
                            //ArrayList<String> listaString = new LeerArchivo().Leer2("Gramatica1.txt");
                            //ArrayList<String> listaString = new LeerArchivo().Leer2("Expresiones3.txt");
                            ArrayList<String> listaString = new LeerArchivo().Leer2("Gramatica1.txt");
                            //ArrayList<String> listaString = new LeerArchivo().Leer2("Gramatica3.txt");
                            LL1.AnalizadorSintactico ll = new LL1.AnalizadorSintactico(afdx);
                            
                            String reglas_grama = "";
                            for(String s: listaString){
                                reglas_grama = reglas_grama + "" + s;
                            }
                            System.out.println("Reglas cadena completa: " + reglas_grama + "\n");
                            ll.asignarCadena(reglas_grama);
                            if(ll.init())
                                System.out.println("exito");                                
                            System.out.println("---------------");
                            
                            ll.encender_banderas();
                            for(LL1.Nodo n: ll.getListaLista()){
                                System.out.println("SimboloIzquierdo: " + n.simbolo + " Bandera: " + n.no_terminal);
                                LL1.Nodo sig = n.nodo_1;
                                while(sig != null){
                                    System.out.println("SimboloDer: " +sig.simbolo + " Bandera: " + sig.no_terminal);
                                    sig = sig.nodo_1;
                                }
                            }
                            System.out.println("No terminales " + ll.get_conj_no_terminales());
                            System.out.println("Sí terminales " + ll.get_conj_terminales());
                            
                            LL1.TablaLL1 tabla_ll = new LL1.TablaLL1(ll);
                            tabla_ll.crear_tablaLL1();
                            
                        } else {
                            System.out.println("Opcion invalida");
                        }
                    }else { 
                        System.out.println("Carpeta no existe");
                    }  

                break;
                
                default:
                    System.out.println("Opción inválida, adiós.");
            }
        } 
    }
    
    public static void genGraphAFN(AFN fn){
        GraphViz gv = new GraphViz();
        gv.add(fn.strDotGraph());
        System.out.println(gv.getDotSource());
        String stout = "out_afd_" + fn.getId_afn() + ".png";
        File out = new File(stout);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
    }
    
    public static void genGraphAFD(AFD fd){
        GraphViz gv = new GraphViz();
        gv.add(fd.strDotGraph());
        System.out.println(gv.getDotSource());
        String stout = "out_afd_" + fd.getId_afd() +".png";
        File out = new File(stout);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
    }
}
