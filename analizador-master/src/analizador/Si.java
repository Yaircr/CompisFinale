package analizador;

import java.util.ArrayList;

/* @author Isaac */
public class Si extends Estado{
    private int indice;
    private boolean check;
    private ArrayList<Estado> mis_estados;
    
    public Si(boolean edofin, boolean edoin, int token, int indice, boolean check, ArrayList<Estado> mis_estados){
        super(indice, edofin, edoin, token);
        this.indice = indice;
        this.check = check;
        this.mis_estados = mis_estados;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public ArrayList<Estado> getMis_estados() {
        return mis_estados;
    }

    public void setMis_estados(ArrayList<Estado> mis_estados) {
        this.mis_estados = mis_estados;
    }
    
    
}
