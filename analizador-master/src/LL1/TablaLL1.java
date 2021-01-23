package LL1;

import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

// @author Isaac
 
public class TablaLL1 {
    
    private ArrayList<Nodo> lista;   
    private AnalizadorSintactico analizadorSintactico;
    
    public TablaLL1(AnalizadorSintactico analizador_sintactico){
        this.lista = analizador_sintactico.getListaLista();
        this.analizadorSintactico = analizador_sintactico;
    }
    
    public Set<String> first(Nodo l){
        Nodo aux = null;
        Set<String> R = new LinkedHashSet<>();
        aux = l;
        
        if(aux.no_terminal == false || aux.simbolo.equalsIgnoreCase("ë")){
            R.add(aux.simbolo);
            return R;
        }
        
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).simbolo.equals(aux.simbolo)){
                if(aux.simbolo.equals(lista.get(i).nodo_1.simbolo)){
                    continue;
                }
                Set<String> set_aux = first(lista.get(i).nodo_1);
                for(String s: set_aux){
                    R.add(s);
                }
            }
        }
        if(R.contains("ë") && l.nodo_1 != null){
            R.remove("ë");
            Set<String> set_aux = first(l.nodo_1);
                for(String s: set_aux){
                    R.add(s);
                }
                return R;
        }
        return R;
    }
    
    public Set<String> follow(String A){
        Set<String> R = new LinkedHashSet<>();
        Set<String> aux = new LinkedHashSet<>();
        Nodo N = null;
        if(A.equals(lista.get(0).simbolo)){ //El simbolo incial debe de ser el primer en la lista_lista{
           R.add("$");           
        }
        for(int i = 0; i < lista.size(); i++){
            N = buscar_simbolo(A, i);
            if(N == null){
                continue;
            }
            if(N.nodo_1 == null){
                if(A.equals(lista.get(i).simbolo)){
                    continue;
                }
                Set<String> set_aux = follow(lista.get(i).simbolo);
                for(String s : set_aux)
                    R.add(s);
                    
            }else{
                aux = first(N.nodo_1);
                if(aux.contains("ë")){
                    aux.remove("ë");
                    for(String s: aux)
                        R.add(s);
                    if(!A.equals(lista.get(i).simbolo)){
                        Set<String> set_aux = follow(lista.get(i).simbolo);
                        for(String s : set_aux){
                            R.add(s);
                        }
                    }
                }else{
                    for(String s: aux)
                        R.add(s);
                }
            }
        }
        return R;
    }
    
    public Nodo buscar_simbolo(String A, int i){
        Nodo n = lista.get(i);
        Nodo sig = n.nodo_1;
        while(sig != null){
            if(sig.simbolo.equals(A)){
                return sig;
            }
            sig = sig.nodo_1;
        }       
        return null;
    }
    
    public String[][] crear_tablaLL1(){
        int fila = analizadorSintactico.simbolos_no_terminales.size() + 2;
        int colu = analizadorSintactico.simbolos_terminales.size() + 2;
        
        String[][] tabla = new String[fila][colu];
        for(int i = 1; i < fila; i++){        
            Object[] terminales_arry = analizadorSintactico.simbolos_no_terminales.toArray();
            if(i <= terminales_arry.length)
                tabla[i][0] = (String)terminales_arry[i-1];                        
        }
        tabla[fila-1][0] = "$";
        
        for(int i = 1; i < colu; i++){            
            Object[] terminales_arry = analizadorSintactico.simbolos_terminales.toArray();
            if(i <= terminales_arry.length)
                tabla[0][i] = (String)terminales_arry[i-1]; 
        }
        tabla[0][colu-1] = "$";
        

        

        for(int jj = 0; jj < lista.size(); jj++){
            Set<String> resultado = new LinkedHashSet();
            Set<String> fst = first(lista.get(jj).nodo_1);
            if(fst.contains("ë")){
                fst.remove("ë");
                Set<String> fllow = follow(lista.get(jj).simbolo);
                for(String f : fllow){
                    resultado.add(f);
                }
            }else{
                for(String f : fst){
                    resultado.add(f);
                }
            }
            
            Object[] terminales_arry = analizadorSintactico.simbolos_terminales.toArray();
            ArrayList<Integer> indices = buscar_simbolo(terminales_arry, resultado);
            
            terminales_arry = analizadorSintactico.simbolos_no_terminales.toArray();
            ArrayList<Integer> indx = buscar_simbolo_izq(terminales_arry, lista.get(jj).simbolo);
            
            for(int xxc : indices){
                tabla[indx.get(0)+1][xxc+1] = (jj+1) + "";
            }
                                                                  
        }
                
        for(int i = 0; i < fila; i++){
            for(int j = 0; j < colu; j++){
                System.out.print(" "+tabla[i][j]+" ");
            }
            System.out.println("");
        }
        return tabla;
    }
    
    public ArrayList<Integer> buscar_simbolo(Object[] terminales, Set<String> resultado){
        ArrayList<Integer> indices = new ArrayList();
        for(String x : resultado){
            for(int i = 0; i < terminales.length; i++){                
                if(terminales[i].equals(x)){
                   indices.add(i);
                }      
                
            }     
            if(x.equals("$")){
                    indices.add(terminales.length);
                } 
        }
        return indices;
        
    }
    
    public ArrayList<Integer> buscar_simbolo_izq(Object[] no_terminales, String lado_izq){
        ArrayList<Integer> indices = new ArrayList();
        for(int i = 0; i < no_terminales.length; i++){
            if(lado_izq.equals(no_terminales[i]))
                indices.add(i);
        }
        return indices;
        
    }
}
