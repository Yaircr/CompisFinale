package analizador;
import java.util.ArrayList;
/* @author Isaac */
public class Conjunto {
    
    public Conjunto(){}
    
    public ArrayList<Estado> union(ArrayList<Estado> conj_1, ArrayList<Estado> conj_2 ){
        //ArrayList<Estado> resultado = new ArrayList();
        Estado aux = null;
        boolean flag = true;
        
        for (Estado estado : conj_2) {
            for (Estado estado1 : conj_1) {
                if(estado1 == null){
                    break;
                }
                if(estado1.getId() == estado.getId()){
                    flag = false;
                    break;
                }
                aux = estado;
            }
            if(flag && aux != null){
                conj_1.add(aux);                
                aux = null;
            }else if(aux == null && flag){
                conj_1.add(estado);                
                aux = null;
            }
            else{
                flag = true;
            }
        }
        return conj_1;              
    }
    
    public ArrayList<Estado> interseccion(ArrayList<Estado> conj_1, ArrayList<Estado> conj_2){
        ArrayList<Estado> res = new ArrayList();
        for(Estado estado_1 : conj_2){
            for(Estado estado_2 : conj_1){
                if(estado_1.getId() == estado_2.getId()){
                    res.add(estado_2);
                }
            }
        }
        return res;
    }
    
    public ArrayList<String> union_s(ArrayList<String> conj_1, ArrayList<String> conj_2 ){            
        boolean flag = true;
        String aux = null;
        ArrayList<String> res = (ArrayList<String>)conj_1.clone();
        for(String c_1: conj_2){
            for(String c_2: conj_1){
                if(c_2.equals(null) || c_2.equals("") || c_2 == null){
                    break;
                }else if(c_2.equals(c_1)){
                    flag = false;
                    break;
                }
                aux = c_1;
            }
            
            if((flag && aux != null)){
                //conj_1.add(aux);
                res.add(aux);
                aux = null;
            }else if(aux == null && flag){
                //conj_1.add(c_1);
                res.add(c_1);
                aux = null;
            }else
                flag = true;            
        }
    return res;              
    }
    
    public ArrayList<String> interseccion_s(ArrayList<String> conj_1, ArrayList<String> conj_2){
        ArrayList<String> res = new ArrayList();
        for(String estado_1 : conj_2){
            for(String estado_2 : conj_1){
                if(estado_1.equals(estado_2)){
                    res.add(estado_2);
                }
            }
        }
        return res;
    }
}
