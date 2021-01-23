package analizador;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author yax
 */
public class LeerArchivo {
    public ArrayList er = new ArrayList();
    public ArrayList tokens = new ArrayList();    
    
    public LeerArchivo(){
    
    }
    public void Leer(String nombre) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File ("Archivos/"+nombre);//"C:\\archivo.txt"
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            int i = 0;
            
            while((linea=br.readLine())!=null){                
                //String[] partes = linea.split("");
                if(i%2 == 0){
                    er.add(linea);                    
                }else if(i%2 ==1){
                    tokens.add(linea);
                }    
                else{
                    System.out.println("Error al leer la linea " + i +": "+ linea +", verifique que este en el formato correcto");
                    System.out.println("Por conveniencia esta linea se omite");
                }
                i++;
            }
        }
        catch(Exception e){
            System.out.println("Archivo no existe");
            e.printStackTrace();
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
        }
    }
    
    public ArrayList<String> Leer2(String nombre) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList<String> list = new ArrayList<>();
        
        try {
            archivo = new File ("Archivos/"+nombre);//"C:\\archivo.txt"
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            
            while((linea=br.readLine())!=null){                
                //String[] partes = linea.split("");
                list.add(linea);
            }
        }
        catch(Exception e){
            System.out.println("Archivo no existe");
            e.printStackTrace();
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
        }
        return list;
    }
    
    public ArrayList getER(){
        return er;
    } 
    public ArrayList getTokens(){
        return tokens;
    }
}