package analizador;

import java.util.ArrayList;

public class Transicion{
    private char simbolo_1;
    private char simbolo_2; //Para agregar los rangos en las expresiones regulares
    private ArrayList<Estado> destinos = new ArrayList(); // >= 1
    
        
    public Transicion(char simbolo, char simbolo_2, ArrayList<Estado> destinos){
    	this.simbolo_1 = simbolo;
        this.simbolo_2 = simbolo_2;
    	this.destinos = destinos;
    } 
    
    public Transicion(char simbolo, char simbolo_2, Estado destino){
    	this.simbolo_1 = simbolo;
        this.simbolo_2 = simbolo_2;
    	destinos.add(destino);
    }
    
    public char getSimbolo_1() {
        return simbolo_1;
    }

    public void setSimbolo_1(char simbolo) {
        this.simbolo_1 = simbolo;
    }
    
    public char getSimbolo_2() {
        return simbolo_2;
    }

    public void setSimbolo_2(char simbolo) {
        this.simbolo_2 = simbolo;
    }

    public ArrayList<Estado> getDestinos() {
        return destinos;
    }

    public void setDestinos(ArrayList<Estado> destinos) {
        this.destinos = destinos;
    }

}
