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
package com.eycr.automaton;

import java.util.Collection;
import java.util.HashSet;

public class Alpha
{
  private Collection<Character> symbols;
  
  /*
    Constructor para el alfabeto
  */
  public Alpha()
  {
    symbols=new HashSet<>();
  }
  
  /*
    Agregar alfabeto al existente
    @param alphabet2
    @return Coleccion nueva de simbolos
  */
  public Boolean addAlpha(Alpha alphabet2)
  {
      Collection symbols2=alphabet2.getSymbols();
      return this.symbols.addAll(symbols2);
  }
  
  /*
    Constructor de alfabeto con caracteres inicial y final
    @param initialSymbol
    @param lastSymbol
    
  */
  public Alpha(Character initialSymbol,Character lastSymbol)
  {
      symbols=new HashSet<>();
      for(int i=initialSymbol;i<=lastSymbol;i++)
      {
          symbols.add((char)i);
      }
  }
  
  /*
    Quitar un simbolo
    @param symbol
    @return Coleccion de simbolos con un elemento menos
  */
  public Boolean removeElement(Character symbol)
  {
      return symbols.remove(symbol);
  }
  
  /*
    Agregar elemento
    @param symbol
    @return tru o false si se logro agregar
  */
  public Boolean addElement(Character symbol)
  {
    if(!symbols.add(symbol))
    {
      System.out.println("ERROR! "+symbol+" no se pudo agregar");
      return false;
    }
    return true;
  }
  
  /*
    Verifica si un simbolo ya existe en la coleccion del alfabeto
    @param symbol
    
  */
  public Boolean verifySymbol(Character symbol)
  {
    return symbols.contains(symbol);
  }
  
  /*
    Recuperar tamanio de la coleccion
    @return tamanio de la coleccion
  */
  public int size()
  {
    return symbols.size();
  }
  /*
    Recuperar todo el alfabeto
    @return la coleccion de simbolos en una unica cadena
  */
  public String getAlpha()
  {
    return symbols.toString();
  }
    /*
        Recuperar los simbolos
        @return symbols
    */
    public Collection<Character> getSymbols() {
        return symbols;
    }
    /*
        Establece los simboloes
        @param symbols coleccion de caracteres
    */
    public void setSymbols(Collection<Character> symbols) {
        this.symbols = symbols;
    }
  
}
