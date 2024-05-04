package Controller;

import Model.MMU;

import java.io.BufferedReader;
import java.io.File;  
import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private Controller controller;
    private boolean pauseFlag;
    private static MMU memory = new MMU();;

    public Controller() {
        //this.pauseFlag = pauseFlag;

    }
    
    
    
    
    public static void readInstructions() throws FileNotFoundException, IOException{
        File file = new File("F:\\Ronaldo\\TEC\\Semestre 13 - 2024\\Sistemas Operativos\\Proyectos\\Proyecto 2\\Instructions.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        
        String pid = "";
        String size = "";   
        String ptr = "";
        String[] res;
        
        while ((st = br.readLine()) != null){
                pid = "";
                size = "";
                ptr = "";
                
                res = st.split("[\\\\()]");                     //Quita los paréntesis de la instrucción                
                String instruction = res[0];
                               
                switch(instruction){
                    case "new":
                        //System.out.println("Ejecutar instrucción New");
                        //System.out.println("Entra:"+ res[1]);
                        if(res[1].contains(",")){                               //Si la instrucción lleva una ",", quiere decir que es la instrucción "new"
                                
                                String[] splitRes;
                                splitRes = res[1].split(",");
                                pid = splitRes[0];                              //Toma el primer número antes del paréntesis (Es decir, el Pid del proceso)                   
                                size = splitRes[1];                             // Toma el número después de la coma (Es decir el Size en Bytes del proceso)
                                
                                     
                                memory.newInstruction(Integer.valueOf(pid), Integer.valueOf(size));        //Se deben convertir ambos valores a enteros
                        }                     
                        break;
                        
                    case "use":
                        
                        ptr = res[1];
                        //System.out.println("Ejecutar instrucción Use");
                        //System.out.println(res[1]);
                        
                        break;
                        
                        
                    case "delete":
                        
                        //System.out.println("Ejecutar instrucción Delete");
                        //System.out.println(res[1]);
                        ptr = res[1];
                        
                        break;
                    case "kill":    
                        
                        //System.out.println("Ejecutar instrucción Kill");
                        //System.out.println(res[1]);
                        ptr = res[1];
                        
                        break;
                }

                
                
                
                
                
              
                
        }   
    }
    
    
    
    public static void main(String[] args) throws IOException {
        readInstructions();
        memory.printMemoryMap();
        memory.printSymbolTable();
    }
}
