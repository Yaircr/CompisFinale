/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.utilities;

import com.eycr.automaton.InterfaceStates;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 *
 * @author firem
 */
public class StateHandlers implements Collection,List{
    private HashMap<Integer,InterfaceStates> content;
    
    public StateHandlers()
    {
        content=new HashMap<>();
    }
    @Override
    public int size() {
        return content.size();
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if(!(o instanceof InterfaceStates))
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        InterfaceStates s=(InterfaceStates)o;
        return content.containsKey(s.getId());
    }

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

    @Override
    public boolean retainAll(Collection clctn) {
        Iterator<InterfaceStates> clctnIt=clctn.iterator();
        while(clctnIt.hasNext())
        {
            InterfaceStates s=clctnIt.next();
        }
        return true;
    }

    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public boolean addAll(int i, Collection clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
    @Override
    public String toString()
    {
        return content.values().toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

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
