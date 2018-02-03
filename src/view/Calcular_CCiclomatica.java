/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Horizon
 */
public class Calcular_CCiclomatica {

    public Calcular_CCiclomatica() {
    }
    
    //Función se le pasa el fichero y devuelve la complejida ciclomática.
    public int calcular_CC (File f) {
        String cadena = f.getAbsolutePath();
        String leer = "";
        int resul = 0;
        int numIf = 0, numWhile = 0, numDo = 0, numFor = 0, numSwitch = 0;
        
        //Carga el contenido del fichero en el textarea
        try {
            BufferedReader in = new BufferedReader(new FileReader(cadena));
            //Leemos el fichero linea por linea
            while ((leer = in.readLine()) != null) {
                if (leer.contains(" switch")) {
                    numSwitch += 1;
//                    System.out.println("switch encontrado: " + leer);
                }
                if (leer.indexOf(" do{") != -1) {
                    numDo += 1;
//                    System.out.println("do encontrado: " + leer);
                }
                if (leer.indexOf(" if") != -1) {
                    numIf += 1;
//                    System.out.println("if encontrado: " + leer);
                }
                if (leer.indexOf(" while") != -1) {
                    numWhile += 1;
//                    System.out.println("while encontrado: " + leer);
                }
                if (leer.indexOf(" for(") != -1) {
                    numFor += 1;
//                    System.out.println("for encontrado: " + leer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//Calculo complejidad ciclomática por números de nodos condicionales
        resul = numIf + numDo + numWhile + numSwitch + numFor + 1;
        return resul;
    }
}
