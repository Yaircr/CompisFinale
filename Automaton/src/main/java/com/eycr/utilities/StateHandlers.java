/*
                    INSTITUTO POLITECNICO NACIONAL
                     ESCUELA SUPERIOR DE COMPUTO
                            COMPILADORES
                           PROYECTO FINAL
                                3CM6
                              ALUMNOS:
                    CUELLAR RIVERA EDUARDO YAIR
                     GARCIA VERA JARED ALBERTO
                     HERNANDEZ MEJIA DIEGO YAIR
                                
*/
package com.eycr.utilities;

import com.eycr.automaton.InterfaceStates;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/*
    Clase para la creación de un HASHMAP
*/
public class StateHandlers implements Collection,List{
    private HashMap<Integer,InterfaceStates> content; //HASH de Interfaces
/*
    Constructor del HASH
*/    
    public StateHandlers()
    {
        content=new HashMap<>();
    }
    
/*
    Metodo para obtener el tamaño del HASH
    @return content.size(): Tamaño del HASH
*/    
    @Override
    public int size() {
        return content.size();
    }
/*
    Metodo para saber si el HASH esta vacio
    @return content.size(): TRUE o FALSE dependiendo si el HASH esta vacio o no
*/    
    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }
/*
    Metodo para saber si una clave ya existe en el HASH
    @return content.containsKey(s.getId()): TRUE o FALSE dependiendo si la clave ya existe o no en el HASH
*/ 
    @Override
    public boolean contains(Object o) {
        if(!(o instanceof InterfaceStates))
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        InterfaceStates s=(InterfaceStates)o;
        return content.containsKey(s.getId());
    }
/*
    Metodo para obtener valores de iteradores dentro del HASH
    @return content.values().iterator(): Valores de los iteradores de HASH
*/
    @Override
    public Iterator iterator() {
       return content.values().iterator();
        
        
     }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray(Object[] ts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(Object e) {
        if(!(e instanceof InterfaceStates))
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        InterfaceStates s=(InterfaceStates)e;
        if(content.containsKey(s.getId()))
            return false;
        else
            content.put(s.getId(), s);
        return true;
    }

    @Override
    public boolean remove(Object o) {
       if(!(o instanceof InterfaceStates))
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        InterfaceStates s=(InterfaceStates)o;
        return content.remove(s.getId(),s);
    }

    @Override
    public boolean containsAll(Collection clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection clctn) {
        Iterator<InterfaceStates> clctnIt=clctn.iterator();
        while(clctnIt.hasNext())
        {
            InterfaceStates s=clctnIt.next();
            if(!content.containsKey(s.getId()))
                content.put((s.getId()), s);
            
        }
        return true;
    }
/*
    Metodo para borrar objetos del HASH
    @return TRUE al terminar de borrar todos los objetos del HASH
*/
    @Override
    public boolean removeAll(Collection clctn) {
        Iterator<InterfaceStates> clctnIt=clctn.iterator();
        while(clctnIt.hasNext())
        {
            InterfaceStates s=clctnIt.next();
            content.remove(s.getId(), s);
        }
        return true;
    }
/*
    Metodo para compara objetos usando el metodo equals
    @return TRUE si lo encontro
*/
    @Override
    public boolean retainAll(Collection clctn) {
        Iterator<InterfaceStates> clctnIt=clctn.iterator();
        while(clctnIt.hasNext())
        {
            InterfaceStates s=clctnIt.next();
        }
        return true;
    }
/*
    Metodo para borrar el Hash map
*/
   
    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public boolean addAll(int i, Collection clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    Metodo para obtener el objeto dentro del HASH
    @param i: Clave del elemento 
    @return content.get(i): Elemento con la clase i
*/
    @Override
    public Object get(int i) {
        return content.get(i);
    }

    @Override
    public Object set(int i, Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(int i, Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object remove(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator listIterator(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List subList(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    Metodo para imprimir
    return hash: Imprime el HASH (String)
*/
    @Override
    public String toString()
    {
        return content.values().toString();
    }
/*
    Metodo para obtener clave de HASH
    @return hash: Clave del HASH (int)
*/
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
/*
    Metodo para comparar
    @return TRUE o FALSE dependiendo si es igual o no (boolean)
*/
    @Override
    public boolean equals(Object obj) {
        System.out.println("USO EQUALS");
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StateHandlers other = (StateHandlers) obj;
        if(this.content.values().equals(other.content.values()))
            return true;
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }
    
    
}
