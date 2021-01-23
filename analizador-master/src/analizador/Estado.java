package analizador;

import java.util.ArrayList;

public class Estado{
    private int token,id;
    private static int idSig; //Para llevar un id consecutivo    
    private ArrayList<Transicion> transiciones = new ArrayList();
    private boolean edo_fin, edo_ini;
    
    public Estado(int id, boolean edofin, boolean edoin, int token){
    	this.id = id;
    	this.edo_fin = edofin;
    	this.edo_ini = edoin;
    	this.token = token;
        setIdSig();
    } 
    
    public void asignarTransiciones(ArrayList<Transicion> transiciones){
        this.transiciones.addAll(transiciones);
    }
    
    public void asignarTransicion(Transicion trans){
        transiciones.add(trans);
    }
    
    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public static int getIdSig() {
        return idSig;
    }
    
    public static void setIdSig() {
        idSig += 1;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }

    public boolean isEdo_fin() {
        return edo_fin;
    }

    public void setEdo_fin(boolean edo_fin) {
        this.edo_fin = edo_fin;
    }

    public boolean isEdo_ini() {
        return edo_ini;
    }

    public void setEdo_ini(boolean edo_ini) {
        this.edo_ini = edo_ini;
    }
    

}