package analizador;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* @author Isaac */
public class AFD {
    
    static int idSig = 1;
    private int id_afd;
    private Estado edo_incial;
    private ArrayList<Estado> todos_edos;
    private ArrayList<Estado> edos_aceptacion;
    private List<List<Integer>> tabla;
    private char[] alfabeto;

    public AFD(Estado edo_ini, ArrayList<Estado> edos_aceptacion, char[] alfabeto){
        this.edo_incial = edo_ini;
        this.edos_aceptacion = edos_aceptacion;
        this.alfabeto = alfabeto;
        id_afd = idSig;
        idSig++;
    }
    
    public Estado getEdo_incial() {
        return edo_incial;
    }
    
    public void test(){
        System.out.println("Estado inicial: " + edo_incial.getId());

        //System.out.println("ID-AFN " + );
        for(Estado e : todos_edos){
            System.out.println("ESTADO: " + e.getId());
            System.out.println("ACEPTACION? " + e.isEdo_fin());
            for(Transicion t : e.getTransiciones()){                
                System.out.println("Transicion simbolo_1:" + t.getSimbolo_1());
                System.out.println("Transicion simbolo_2:" + t.getSimbolo_2());
                for(Estado dest : t.getDestinos()){
                    System.out.println("ID_EDO: " + dest.getId());                    
                }                   
            }
        }
    }
    
    public void imprimir_tabla(){        
        for(int i = 0; i < tabla.size(); i++){
            for(int j = 0; j < tabla.get(0).size(); j++){
                System.out.print("  " + tabla.get(i).get(j) + "  ");
            }
            System.out.println("");
        }
    }
    
    public void escribirArchivo(){
        FileWriter fichero = null;
        PrintWriter pw = null;
        
        String sDirectorio = "Tablas";
        File f = new File(sDirectorio);
        if(f.exists()){
            try{
                File[] ficheros = f.listFiles();
                
                fichero = new FileWriter("Tablas/tabla"+ficheros.length+".txt");
                pw = new PrintWriter(fichero);
                
                ArrayList linea = new ArrayList();
                String aux= "";
                for(int i = 0; i < tabla.size(); i++){
                    for(int j = 0; j < tabla.get(0).size(); j++)
                        aux += tabla.get(i).get(j) + " ";
                    linea.add(aux);
                    aux = ""; 
                }
                for (int i = 0; i < linea.size(); i++)
                    pw.println(linea.get(i));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
               try {
               if (null != fichero)
                  fichero.close();
               } catch (Exception e2) {
                  e2.printStackTrace();
               }
            }
        }else{
        
        }
    }

    public void setEdo_incial(Estado edo_incial) {
        this.edo_incial = edo_incial;
    }

    public ArrayList<Estado> getTodos_edos() {
        return todos_edos;
    }

    public void setTodos_edos(ArrayList<Estado> todos_edos) {
        this.todos_edos = todos_edos;
    }

    public ArrayList<Estado> getEdos_aceptacion() {
        return edos_aceptacion;
    }

    public void setEdos_aceptacion(ArrayList<Estado> edos_aceptacion) {
        this.edos_aceptacion = edos_aceptacion;
    }

    public List<List<Integer>> getTabla() {
        return tabla;
    }

    public void setTabla(List<List<Integer>> tabla) {
        this.tabla = tabla;
    }

    public char[] getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(char[] alfabeto) {
        this.alfabeto = alfabeto;
    }
    
    private Transicion validar_transicion(Estado e, char simbolo){
        ArrayList<Transicion> trans = e.getTransiciones();    
        for (Transicion tran : trans) {
            if(simbolo >= tran.getSimbolo_1() && simbolo <= tran.getSimbolo_2()){ //Se modificó para los rangos en las expresiones regulares
                return tran;    
            }
        }
        return null;
    }
    
    public boolean validar_cadena(Estado sig, String cadena, int i){        
        if(cadena.charAt(i) == '\n'){
            if(sig.isEdo_fin())
                return true;
            else
                return false;
        }
        Transicion t = validar_transicion(sig, cadena.charAt(i));
        if(t == null){
            return false;
        }else{
            i = i + 1;
            return validar_cadena(t.getDestinos().get(0), cadena, i);
        }        
    }

    /**
     * Genera un string que puede ser utilizado para graficar el automata usando GraphViz
     * Ejemplo:
     * digraph test123 {
     *         a -> b -> c;
     *         a -> {x y};
     *         b [shape=box];
     *         c [label="hello\nworld",color=blue,fontsize=24,
     *              fontname="Palatino-Italic",fontcolor=red,style=filled];
     *         a -> z [label="hi", weight=100];
     *         x -> z [label="multi-line\nlabel"];
     *         edge [style=dashed,color=red];
     *         b -> x;
     *         {rank=same; b x}
     * }
     * 
     * @return String formateado
     */
    public String strDotGraph(){
        String res_header = "Digraph AFD {\n" +
                           "\trankdir=LR;\n" +
                           "\toverlap=scale;\n";
        String res_nodos = "";
        String res_edges = "";
        for (Estado origen : this.todos_edos) {
            String shape = "circle";
            if(origen.isEdo_fin())
                shape = "doublecircle";
            res_nodos += origen.getId() + " [shape=" + shape +"];\n";
            shape = "circle";
            for (Transicion tran : origen.getTransiciones()) {
                for (Estado dest : tran.getDestinos()){
                    String label = "";
                    if(tran.getSimbolo_1() == tran.getSimbolo_2())
                        label = "" + tran.getSimbolo_1();
                    else
                        label = "["+ tran.getSimbolo_1() + "-" 
                                + tran.getSimbolo_2() + "]";
                    res_edges += origen.getId() + " -> " + dest.getId() 
                              + " [label = \"" + label + "\" ];\n"; 
                }
            }
        }
        String resultado = res_header + res_nodos + res_edges + "}";
        return resultado;
    }

    public int getId_afd() {
        return id_afd;
    }

    public void setId_afd(int id_afd) {
        this.id_afd = id_afd;
    }
    
    
    
}
