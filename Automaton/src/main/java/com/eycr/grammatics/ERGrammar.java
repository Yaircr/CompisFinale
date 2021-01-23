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
package com.eycr.grammatics;

import com.eycr.automaton.AFN;
import com.eycr.automaton.InterfaceAFN;
import com.eycr.lexic.LexicAnalyzer;
import com.eycr.utilities.Const;
import com.eycr.utilities.IOops;

/*
   
    Analizador Sintactico para E.R
    Incluye los métodos E, Ep, T, Tp, C, Cp y F
    @throws UnsopportedOperationException en caso de que se solicite que la interfaz realice alguna operacion que no este codificada

*/

public class ERGrammar {
    private LexicAnalyzer lexic;
    public ERGrammar(LexicAnalyzer lexic)
    {
        this.lexic=lexic;
    }
    /*
        Método que busca si se cumple T y Ep
        
        @return true o false si T y Ep se cumplen
    */
    public Boolean E(InterfaceAFN f)
    {
        //System.out.println("Llama a E");
        if(T(f))
        {
            if(Ep(f))
            {
                return true;
            }
        }
        return false;
    }
    /*
        Método que obtiene el token y valida si se cumple T, de ser así se agrega el AFN generado
        @return true o false si se valida T y Ep
    */
    public Boolean Ep(InterfaceAFN f)
    {
        //System.out.println("Llama a Ep");
        int token;
        InterfaceAFN f2=new AFN();
        token=lexic.getToken();
        if(token==Const.OR)
        {
            if(T(f2))
            {
                f.addAFN(f2);//REVISAR
                if(Ep(f))
                {
                    return true;
                }
            }
            return false;
        }
        lexic.undoYylex();
        return true;
    }
    /*
        Método que valida si se cumplen C y Tp
        @return true o false si C y Tp se cumplen
    */
    public Boolean T(InterfaceAFN f)//CUIDADO CON EL RETURN
    {
        //System.out.println("Llama a T");
        if(C(f))
        {
            if(Tp(f))
            {
                return true;
            }
        }
        return false;
    }
    /*
        Método que obtiene el token evaluado y verifica si es correcto, después evalua en C y Tp
        @params token: Valor númerico correspondiente al carácter
        @return true o false si se cumplen C y Tp
    */
    public Boolean Tp(InterfaceAFN f)
    {
        //System.out.println("Llama a Tp");
        int token;
        InterfaceAFN f2=new AFN();
        token=lexic.getToken();
        if(token==Const.CONC)
        {
            if(C(f2))
            {
                f.concatenateAFN(f2);
                //System.out.println("CONCATENA "+f.getAcceptedStates() +" con "+f2.getAcceptedStates());
                if(Tp(f))
                {
                    return true;
                }
            }
            return false;
        }
        lexic.undoYylex();
        return true;
    }
    /*
        Método que evalua si se cumplen F y Cp
        @return true o false si F y Cp se cumplen
    */
    public Boolean C(InterfaceAFN f)
    {
        //System.out.println("Llama a C");
        if(F(f))
        {
            //System.out.println("AUTOMATA EN C:"+f.toString());
            if(Cp(f))
            {
                return true;
            }
        }
        return false;
    }
    /*
        Método que obtiene el token y valida su opción (que símbolo es)
        Al final devuelve el lexico
        @params token: Valor númerico correspondiente al carácter
        @return true o false dependiendo las evaluaciones 
    */
    public Boolean Cp(InterfaceAFN f)
    {
        //System.out.println("Llama a Cp");
        int token;
        token=lexic.getToken();
        //System.out.println("LLEGA AUTOMATA---------------------");
        //System.out.println(f.toString());
        switch(token)
        {

            case Const.PROD:
                f.kleenClosure();
                if(Cp(f))
                {
                    return true;
                }
                return false;
            
            case Const.ADD:
                f.positiveClosure();
                if(Cp(f))
                    
                {
                    return true;
                }
                return false;
            case Const.OPT:
                f.optional();
                if(Cp(f))
                {
                    return true;
                }
                return false;
        }
        lexic.undoYylex();
        return true;
    }
    /*
        Método que obtiene un token y verifica si es parentesis, simb o corchete izq.
        @params token: Valor númerico correspondiente al carácter
        @return true o false dependiendo las evaluaciones.
    */
    public Boolean F(InterfaceAFN f)
    {
        //System.out.println("Llama a F");
        Integer token;
        token=lexic.getToken();
        switch(token)
        {
            case Const.PAR_I:
                if(E(f))
                {
                    token=lexic.getToken();
                    if(token==Const.PAR_D)
                    {
                        return true;
                    }
                }
                return false;
            case Const.SIMB:
                IOops io=new IOops();
                f.createBasic(lexic.getLexeme().charAt(0));
                //f.associateToken(io.askForToken());
                return true;
            case Const.COR_I:
                
                if(token==Const.COR_I)
                {
                    return corchetes(f);
                }
                
        }
        return false;
    }
    /*
        Método que obtiene los token y verifica si el carácter es un corchete derecho
        @params a: Carácter
        @parms b: Carácter
        @params token: Valor númerico correspondiente al carácter
        @return true o false si es o no corchete derecho
    */
    public Boolean corchetes(InterfaceAFN f)
    {
        System.out.println("ENTRA 1");
        Character a,b;
        Integer token = lexic.getToken();
        if(token==Const.SIMB)
        {
            System.out.println("ENTRA 2");
            a=lexic.getLexeme().charAt(0);
            if(lexic.getToken()==Const.GUI)
            {
                System.out.println("ENTRA 3");
                if(lexic.getToken()==Const.SIMB)
                {
                    System.out.println("ENTRA 4");
                    b=lexic.getLexeme().charAt(0);
                    f.createBasic(a,b);
                    System.out.println("Crea basic con 2:");
                    System.out.println(f.toString());
                    if(lexic.getToken()==Const.COR_D)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
