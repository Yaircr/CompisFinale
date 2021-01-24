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
import com.eycr.utilities.readFile;
import java.util.Scanner;
import java.io.File;

/**
 *
 * @author Jared
 */
public class GramarTest {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path;
        String carpeta = "..\\Automaton\\Expresiones y gramaticas";
        readFile archivo = new readFile();
        File carpetaLista = new File(carpeta);
        String[] listado = carpetaLista.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        }
        else {
            for (String listado1 : listado) {
                System.out.println(listado1);
            }
        }
        System.out.print("¿Qué archivo quiere leer?");
        Scanner sc = new Scanner(System.in);
        path = sc.nextLine();
        System.out.print("Direccion: " + path);
    }
    
}
