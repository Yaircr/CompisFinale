package LL1;

/**
 *
 * @author Isaac
 */
public class Nodo {
    
    public String simbolo;
    public boolean no_terminal = false;
    public Nodo nodo_1;
    
    public Nodo(String simbolo, boolean no_terminal, Nodo nodo_1){
        this.simbolo = simbolo;
        this.no_terminal = no_terminal;
        this.nodo_1 = nodo_1;                
    }
    
}
