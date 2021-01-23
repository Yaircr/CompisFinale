package analizador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/*  @author imanol */

public class AFN {
    
    static int idSig = 1;
    private int id_afn;
    private char[] alfabeto;    
    private Estado edo_incial;
    private ArrayList<Estado> todos_edos;
    private ArrayList<Estado> edos_aceptacion = new ArrayList();
    
   

    public void test(){
        /*for (char c : alfabeto) {
            System.out.println("simbolo: " + c );
        }*/
        System.out.println("Estado inicial: " + edo_incial.getId());
        /*for(Transicion t : edo_incial.getTransiciones()){
            System.out.println("Transición: " + t.getSimbolo());
            for(Estado d : t.getDestinos()){
                System.out.println("Estado destino: " + d.getId());
            }
        }
        /*
        for (Estado e : edos_aceptacion) {
            System.out.println("Aceptación: " + e.getId());            
        }*/
        System.out.println("ID-AFN " + id_afn);
        for(Estado e : todos_edos){
            System.out.println("ESTADO: " + e.getId());
            for(Transicion t : e.getTransiciones()){                
                System.out.println("Transicion simbolo_1:" + t.getSimbolo_1());
                System.out.println("Transicion simbolo_2:" + t.getSimbolo_2());
                for(Estado dest : t.getDestinos()){
                    System.out.println("ID_EDO: " + dest.getId());                    
                }                   
            }
        }
        
        /*for(int i = 0; i < alfabeto.length; i++){
            System.out.print(" " + alfabeto[i] + " ");
        }*/
        
    }
    
    public AFN(int id_afn, Estado edo_ini, ArrayList<Estado> edos_aceptacion, ArrayList<Estado> todos_edos, char[] alfabeto){
        this.id_afn = id_afn;
        this.edo_incial = edo_ini;
        this.edos_aceptacion = edos_aceptacion;
        this.todos_edos = todos_edos;
        this.alfabeto = alfabeto; 
        idSig++;
    }
    
    
    public AFN(int id_afn, Estado edo_ini, Estado edo_aceptacion, ArrayList<Estado> todos_edos, char[] alfabeto){
        this.id_afn = id_afn;
        this.edo_incial = edo_ini;
        edos_aceptacion.add(edo_aceptacion);
        this.todos_edos = todos_edos;
        this.alfabeto = alfabeto;
        idSig++;
    }
    
    public AFN(){
    }
    
    public static AFN CrearBasico(char c_1, char c_2){        
        ArrayList<Estado> estados = new ArrayList();
        Estado e1 = new Estado(Estado.getIdSig(), false, true, 0);//edo inicial
        Estado e2 = new Estado(Estado.getIdSig(), true, false, 0);//edo final
        estados.add(e1);
        estados.add(e2);
        
        char[] alfabeto = null;
        int tam_alf = (c_2 - c_1);                
        
        if(tam_alf == 0){
            alfabeto = new char[1];
            alfabeto[0] = c_1;                    
        }else{
            alfabeto = new char[tam_alf+1];
            for (int i = 0; i <= tam_alf; i++) { //Se modificó para agregar los rangos a las expresiones regulares
                alfabeto[i] = (char)(c_1 + i);
            }
        }

        
        Transicion t = new Transicion(c_1, c_2, e2);
        e1.asignarTransicion(t);
        
        AFN afnN = new AFN(idSig, e1, e2, estados, alfabeto);  
      
        return afnN;
    }
    
    public AFN UnirAFN(AFN afn){
        ArrayList<Estado> iniciales = new ArrayList();
        char[] alf = new char[alfabeto.length + afn.alfabeto.length];
        
        Estado e1 = new Estado(Estado.getIdSig(), false, true, 0);//edo inicial
        Estado e2 = new Estado(Estado.getIdSig(), true, false, 0);//edo final
        
        iniciales.add(edo_incial);
        iniciales.add(afn.edo_incial);
        
        Transicion t1 = new Transicion('ë', 'ë', iniciales); //transicion epsilon a estados iniciales de los 2 afn
        Transicion t2 = new Transicion('ë', 'ë', e2); //transicion epsilon a estado final  
        
        edo_incial.setEdo_ini(false); 
                
        e1.asignarTransicion(t1);
        for(Estado e : edos_aceptacion){
            e.asignarTransicion(t2);
            e.setEdo_fin(false);
        }
        for(Estado e : afn.edos_aceptacion){
            e.asignarTransicion(t2);
            e.setEdo_fin(false);
        }
        
        edos_aceptacion.clear();
        
        System.arraycopy(alfabeto, 0, alf, 0, alfabeto.length);
        System.arraycopy(afn.alfabeto, 0, alf, alfabeto.length, afn.alfabeto.length); //nuevo alfabeto
        
        edo_incial = e1;
        edos_aceptacion.add(e2);
        todos_edos.add(e1);
        todos_edos.add(e2);
        todos_edos.addAll(afn.todos_edos);
        alfabeto = quitarRepetidos(alf);
        
        afn.id_afn = 666;
        
        return this;
    }
    
    public AFN UnirAFN(ArrayList<AFN> afns){ 
        ArrayList<Estado> iniciales = new ArrayList();
        int tamañoA = alfabeto.length;
        
        iniciales.add(edo_incial);
        
        for(AFN a :afns){
            iniciales.add(a.edo_incial);
            a.edo_incial.setEdo_ini(false);
            tamañoA += a.alfabeto.length;
            edos_aceptacion.addAll(a.edos_aceptacion);
            todos_edos.addAll(a.todos_edos);
            a.id_afn = 666;
        }
        
        char[] alf = new char[tamañoA];
       
        Estado e = new Estado(Estado.getIdSig(), false, true, 0);//edo inicial
        
        Transicion t = new Transicion('ë', 'ë', iniciales);
        
        e.asignarTransicion(t);
        todos_edos.add(e);
        
        System.arraycopy(alfabeto, 0, alf, 0, alfabeto.length);
        int posicion = alfabeto.length;
        
        for(AFN afn : afns) {
            System.arraycopy(afn.alfabeto, 0, alf, posicion, afn.alfabeto.length);
            posicion += afn.alfabeto.length;
        }
       
        edo_incial = e;
        alfabeto = quitarRepetidos(alf);
                
        return this;
    }
    
    //3 Y 1
    public AFN ConcatAFN(AFN afn){
        char[] alf = new char[alfabeto.length+afn.alfabeto.length];
        ArrayList trans = afn.edo_incial.getTransiciones(); //obtenemos las transiciones del 2do afn
                
        for(Estado e : edos_aceptacion){
            e.asignarTransiciones(trans);//le asignamos las trancisiones del 2do afn al primer afn
            e.setEdo_fin(false);
        }
        
        System.arraycopy(alfabeto, 0, alf, 0, alfabeto.length);
        System.arraycopy(afn.alfabeto, 0, alf, alfabeto.length, afn.alfabeto.length); //nuevo alfabeto
       
        for(int i = 0; i < afn.todos_edos.size();i++){
            if(afn.edo_incial.getId() == afn.todos_edos.get(i).getId()){
                afn.todos_edos.remove(i);
                break;
            }
        }
        
        edos_aceptacion = afn.edos_aceptacion;
        todos_edos.addAll(afn.todos_edos);
        alfabeto = quitarRepetidos(alf); 
        
        afn.id_afn = 666;
        
        return this;
    }
    
    public AFN CerraduraTrans(){
        Estado e1 = new Estado(Estado.getIdSig(), false, true, 0);//edo inicial
        Estado e2 = new Estado(Estado.getIdSig(), true, false, 0);//edo final
        
        Transicion t1 = new Transicion('ë', 'ë',edo_incial); //transicion de la cerradura
        Transicion t2 = new Transicion('ë', 'ë', e2);
        //asignamos transiciones
        e1.asignarTransicion(t1); 
        for(Estado e : edos_aceptacion){
            e.asignarTransicion(t1);
            e.asignarTransicion(t2);
            e.setEdo_fin(false);
        }
        edo_incial.setEdo_ini(false);
        edos_aceptacion.clear();
        
        edo_incial = e1;
        edos_aceptacion.add(e2);
        todos_edos.add(e1);
        todos_edos.add(e2);
        
        return this;
    }
    
    public AFN CerraduraKleene(){
        Estado e1 = new Estado(Estado.getIdSig(), false, true, 0);//edo inicial
        Estado e2 = new Estado(Estado.getIdSig(), true, false, 0);//edo final
        
        Transicion t1 = new Transicion('ë', 'ë', edo_incial); //transicion de la cerradura
        Transicion t2 = new Transicion('ë', 'ë',e2);

        //asignamos transiciones
        e1.asignarTransicion(t1); 
        e1.asignarTransicion(t2);
        
        for(Estado e : edos_aceptacion){
            e.asignarTransicion(t1);
            e.asignarTransicion(t2);
            e.setEdo_fin(false);
        }
        edo_incial.setEdo_ini(false);
        edos_aceptacion.clear();
        
        edo_incial = e1;
        edos_aceptacion.add(e2);
        todos_edos.add(e1);
        todos_edos.add(e2);
        
        return this;
    }
    
    public AFN Opcional(){
        Estado e1 = new Estado(Estado.getIdSig(), false, true, 0);//edo inicial
        Estado e2 = new Estado(Estado.getIdSig(), true, false, 0);//edo final
        
        Transicion t1 = new Transicion('ë', 'ë', edo_incial); //transicion de la cerradura
        Transicion t2 = new Transicion('ë', 'ë', e2);

        //asignamos transiciones
        e1.asignarTransicion(t1); 
        e1.asignarTransicion(t2);
        for(Estado e : edos_aceptacion){
            e.asignarTransicion(t2);
            e.setEdo_fin(false);
        }
        edo_incial.setEdo_ini(false);
        edos_aceptacion.clear();
        
        edo_incial = e1;
        edos_aceptacion.add(e2);
        todos_edos.add(e1);
        todos_edos.add(e2); 
        
        return this;
    }
     
    public static ArrayList<Estado> CerraduraEps(Estado e){
        Estado aux = null;
        Stack<Estado> stk = new Stack(); //Pila vacía
        ArrayList<Estado> res = new ArrayList(); //Conjunto resultado vacío
        
        stk.push(e);         
        
        while(!stk.empty()){
            aux = stk.pop();
            res.add(aux);
            for (Transicion t : aux.getTransiciones()) {
                if(t.getSimbolo_1() == 'ë' && t.getSimbolo_2() == 'ë'){ //epsilon = ë = alt+137
                    for(Estado dest : t.getDestinos()){
                        if(!res.contains(dest)){ //¿Podemos asegurarlo?
                            stk.push(dest);
                        }
                    }
                }
            }
        }        
        return res;
    }
    
    
    public ArrayList<Estado> CerraduraEps(ArrayList<Estado> conj_edos){
        ArrayList<Estado> res = new ArrayList(); //vacio
        Conjunto conj = new Conjunto();
        for(Estado e : conj_edos){
            res = conj.union(res, CerraduraEps(e));
        }
        return res;
    }

    
    public ArrayList<Estado> Mover(Estado e, char simbolo){
        ArrayList<Estado> res = new ArrayList();
        ArrayList<Estado> aux = new ArrayList();
        Conjunto conj = new Conjunto();
        for (Transicion t : e.getTransiciones()) {
            if(simbolo >= t.getSimbolo_1() && simbolo <= t.getSimbolo_2()){
                for(Estado ex : t.getDestinos()){
                    aux.add(ex);
                }
            }
            res = conj.union(res, aux);
        }
        return res;
    }
    
    public ArrayList<Estado> Mover(ArrayList<Estado> conj_edos, char simbolo){
        ArrayList<Estado> res = new ArrayList();
        Conjunto conj = new Conjunto();
        for (Estado e : conj_edos) {
            res = conj.union(res, Mover(e, simbolo));
        }
        return res;
    }
    
    
    public ArrayList<Estado> Ir_A(ArrayList<Estado> conj_edos, char simbolo){        
        return CerraduraEps(Mover(conj_edos, simbolo));
    }
    
    public AFD ConvertirAFD(AFN afn){ // Devuelve la conversion
        
        List<List<Integer>> tabla = new ArrayList();
        int indice = 0; //indice para el S_i
        int fila = 0; //incrementa filas de la tabla
        
        // S0 Es el estado inicial del afd. Para asignar sus estados: aplica cerradura, ordena y luego elimina repetidos.
        Si s0 = new Si(false, true, 0, indice++, false, quitarEdosRepetidos(ordenaEstados(CerraduraEps(afn.getEdo_incial()))));

        AFD afdz = new AFD(s0, null, afn.getAlfabeto());
        tabla.add(new ArrayList<>()); // Crea la primera fila de la tabla para S0
        
        /*siAux guarda el Si que sale de cola y newSi guarda la referencia al nuevo Si que se genere al recorrer los Ir_A*/
        Si siAux, newSi;  
        ArrayList<Estado> conj_edos_aux; // Guarda conjunto de estados del siAux que sale de cola
        ArrayList<Si> test = new ArrayList(); // Guarda los Si para comparar con existentes, aunque no hayan sido analizados
        ArrayList<Si> analizados = new ArrayList();
        Queue<Si> colaSi = new LinkedList(); // Cola vacia de edos Si (Sin analizar)
        colaSi.add(s0);
        test.add(s0);
        
        while(!colaSi.isEmpty()){
            siAux = colaSi.poll();
            for(char c : afn.getAlfabeto()){
                
                conj_edos_aux = quitarEdosRepetidos(ordenaEstados(Ir_A(siAux.getMis_estados(), c)));
                
                if((yaExisteSi(test, conj_edos_aux) == null)){ 
                    if(!conj_edos_aux.isEmpty()){ 
                        /*
                            Si no esta vacio, mete id a tabla y añade transicion.
                            No existe el Si entonces lo añade a la cola. Indice aumenta cuando salen nuevos Si.
                        */
                        colaSi.add(newSi = new Si(false, false, 0, indice++, false, conj_edos_aux));
                        test.add(newSi);
                        tabla.add(new ArrayList<>()); // Se crea una nueva fila para la tabla
                        tabla.get(fila).add(newSi.getId());
                        siAux.asignarTransicion(new Transicion(c, c,newSi));
                    }
                    else{
                        // Si esta vacio, añade un -1 a la fila y columna correspondiente de la tabla.
                        tabla.get(fila).add(-1);
                    } 
                }
                else{ 
                    // Ya existe el Si, solo añade la transicion en tabla y afd
                    tabla.get(fila).add(yaExisteSi(test, conj_edos_aux).getId());
                    siAux.asignarTransicion(new Transicion(c, c, yaExisteSi(test, conj_edos_aux)));
                }
            }
            /*Aqui tendria que agregar la columna del token*/
            tabla.get(fila).add(devToken(siAux, afn));
            
            if(tieneAcept(siAux, afn))
                siAux.setEdo_fin(true);
            
            siAux.setCheck(true);
            analizados.add(siAux);
            fila++; // Cambiamos de fila
        }
        afdz.setTodos_edos(ConversionSi_Estado(analizados));
        afdz.setEdos_aceptacion(getEdosAcepAFD(analizados, afn));
        afdz.setTabla(tabla);
        return afdz;
    }
    
    /* Verifica si el conjunto de estados, ya se encuentra definido por un Si, si ya existe, devuelve el estado */
    public Estado yaExisteSi(ArrayList<Si> analizados, ArrayList<Estado> conj_edos){
        if(!analizados.isEmpty()){
            for(Si s : analizados){
                if(comparaConjuntos(s.getMis_estados(), conj_edos)){
                    return s;
                }
            }
        }
        return null;
    }
    
    /*Compara conjuntos a nivel de objetos*/
    public boolean comparaConjuntos(ArrayList<Estado> e1, ArrayList<Estado> e2){
        if(e1.size() == e2.size()){
            for(int i = 0; i < e1.size(); i++){
                if(e1.get(i) != e2.get(i)){
                    return false;
                }
            }
        }
        else
            return false;
        return true;
    }
    
    /*Ordena de menor a mayor los estados internos de los Si por insercion*/
    public ArrayList<Estado> ordenaEstados(ArrayList<Estado> original){
        ArrayList<Estado> aux = original;
        Estado clave;
        int j;
        for(int i = 1; i < aux.size(); i++){
            clave = aux.get(i);
            j = i - 1;
            // Comparando con todos los anteriores
            while(j >= 0 && aux.get(j).getId() > clave.getId()){
                aux.set(j+1, aux.get(j));
                j = j - 1;
            }
            aux.set(j+1, clave);
        }
        return aux;
    }
    
    /*Asegura que no haya estados internos repetidos*/
    public ArrayList<Estado> quitarEdosRepetidos(ArrayList<Estado> original){
        ArrayList<Estado> aux = new ArrayList();
        for(Estado edo : original){
            if(!aux.contains(edo)){
                aux.add(edo);
            }
        }
        return aux;
    }
    
    /* Comprueba si el Si contiene estados de aceptacion del AFN original */
    public boolean tieneAcept(Si s, AFN afn){ 
        ArrayList<Estado> aux = new ArrayList();
        Conjunto conj = new Conjunto();
        aux = conj.interseccion(s.getMis_estados(), afn.getEdos_aceptacion());
        /*Si la interseccion esta vacia devuelve true, asi tiene coherencia el nombre del metodo*/
        return !aux.isEmpty(); 
    }
    
    /*Devuelve el conjunto completo de estados de aceptacion para el conjunto de Si*/
    public ArrayList<Estado> getEdosAcepAFD(ArrayList<Si> lista, AFN afn){
        ArrayList<Estado> aux = new ArrayList();
        for(Si s : lista){
            if(tieneAcept(s, afn))
                aux.add(s);
        }
        return aux;
    }
    
    /* Devuelve el token asociado al estado final*/
    public int devToken(Si s, AFN afn){
        if(tieneAcept(s,afn)){
            for(Estado edo_afn : afn.getEdos_aceptacion()){
                for(Estado edo_s : s.getMis_estados()){
                    if(edo_afn.equals(edo_s))
                        return edo_afn.getToken();
                }
            }
        }
        return 0;
    }
    
    /*Convierte array de Sis a array de Estados*/
    public ArrayList<Estado> ConversionSi_Estado(ArrayList<Si> lista){
        ArrayList<Estado> aux = new ArrayList();
        for(Si s : lista)
            aux.add(s);
        return aux;
    }
    
    public int AnalizarCadena(String cadena){ // Regresa Token
        int token = 0;
        ArrayList<Estado> E = CerraduraEps(edo_incial);
        Conjunto conj = new Conjunto();
        for (int i = 0; i < cadena.length(); i++) {
            if ( (E = Ir_A(E, cadena.charAt(i))).isEmpty() ) {
                return 0;
            }
        }
        if((E = conj.interseccion(E, edos_aceptacion)).isEmpty()){
            return 0;
        }
        
        //Será el primer en agregar en la interserccion ¿?
        return E.get(0).getToken();
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
        String res_header = "Digraph AFN {\n" +
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
    
    public void setId_afn(int id_afn) {
        this.id_afn = id_afn;
    }

    public void setAlfabeto(char[] alfabeto) {
        this.alfabeto = alfabeto;
    }

    public void setEdo_incial(Estado edo_incial) {
        this.edo_incial = edo_incial;
    }

    public void setTodos_edos(ArrayList<Estado> todos_edos) {
        this.todos_edos = todos_edos;
    }

    public void setEdos_aceptacion(ArrayList<Estado> edos_aceptacion) {
        this.edos_aceptacion = edos_aceptacion;
    }
            
    public int getId_afn() {
        return id_afn;
    }

    public char[] getAlfabeto() {
        return alfabeto;
    }

    public Estado getEdo_incial() {
        return edo_incial;
    }

    public ArrayList<Estado> getTodos_edos() {
        return todos_edos;
    }

    public ArrayList<Estado> getEdos_aceptacion() {
        return edos_aceptacion;
    }
    
    public char[] quitarRepetidos(char[] alf){
        ArrayList<String> aux = new ArrayList();
        aux.add(Character.toString(alf[0]));
        int dif = 0;
        for(int i = 0; i < alf.length;i++){
            for(int j = 0; j< aux.size();j++){
                if(!Character.toString(alf[i]).equals(aux.get(j))){
                    dif++;
                }
            }
            if(dif == aux.size())
                aux.add(Character.toString(alf[i]));
            dif = 0;
        }
        char[] nalf = new char[aux.size()];
        for(int i = 0; i < nalf.length;i++){
            nalf[i]= aux.get(i).charAt(0);
        }
        return nalf;
    }
    
    public AFN set_tokens_todos(AFN afn, int token){
        if(afn != null){
            ArrayList<Estado> aux = afn.edos_aceptacion;
            for(Estado acept: aux){
                acept.setToken(token);
            }
        }        
        return afn;
    }
    

}