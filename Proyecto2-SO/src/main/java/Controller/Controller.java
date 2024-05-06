package Controller;

import Model.MMU;
import Model.Computer;
import View.SimulationInterface;


import java.io.BufferedReader;
import java.io.File;  
import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private Controller controller;
    private boolean pauseFlag;
    private static MMU memory = new MMU();
    private static Computer simComputer1 = new Computer(1);
    private static Computer simComputer2 = new Computer(2);
    
    

    public Controller() {
        //this.pauseFlag = pauseFlag;
        this.simComputer1 = new Computer(1);
        this.simComputer2 = new Computer(2);
        
    }
    
    
    public static void readInstructions() throws FileNotFoundException, IOException{
        /*
        Función que cumple con el proceso de Tokenización.
        Entradas: N/A (El archivo podría ser un parámetro en el futuro)
        Salidas: N/A
        Restricciones: Archivo debe ser un TXT.
        */
        
        File file = new File("F:\\Ronaldo\\TEC\\Semestre 13 - 2024\\Sistemas Operativos\\Proyectos\\Proyecto 2\\Instructions.txt");     //Cambiar de acuerdo a su máquina
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        
        String pid = "";
        String size = "";   
        String ptr = "";
        String[] res;
        
        while ((st = br.readLine()) != null){ //Agregar condición de pausa!
                pid = "";
                size = "";
                ptr = "";
                
                res = st.split("[\\\\()]");                     //Quita los paréntesis de la instrucción                
                String instruction = res[0];
                               
                switch(instruction){
                    case "new":
                        //System.out.println("Ejecutar instrucción New");
                        //System.out.println("Entra:"+ res[1]);
                        if(res[1].contains(",")){                               //Si parte de la instrucción lleva una ",", quiere decir que es la instrucción "new"
                                
                                String[] splitRes;
                                splitRes = res[1].split(",");
                                pid = splitRes[0];                              //Toma el primer número antes del paréntesis (Es decir, el Pid del proceso)                   
                                size = splitRes[1];                             // Toma el número después de la coma (Es decir el Size en Bytes del proceso)
                                
                                
                                //System.out.println("PID:"+ pid);
                                //System.out.println("Size:"+ size);
                                if(simComputer1.isReadyFlag() && simComputer2.isReadyFlag()){                
                                    simComputer1.executeNewInstruction(Integer.valueOf(pid), Integer.valueOf(size));    //Se deben convertir ambos valores de Strings a enteros
                                    //simComputer2.executeNewInstruction(Integer.valueOf(pid), Integer.valueOf(size));
                                }
                        }                     
                        break;
                        
                    case "use":
                        
                        ptr = res[1];
                        //System.out.println("Use PTR = " + ptr);
                        if(simComputer1.isReadyFlag() && simComputer2.isReadyFlag()){
                            simComputer1.executeUseInstruction(Integer.valueOf(ptr));    //Se deben convertir ambos valores de Strings a enteros
                            //simComputer2.executeUseInstruction(Integer.valueOf(ptr));
                        }
                        //System.out.println("Ejecutar instrucción Use");
                        //System.out.println(res[1]);
                        break;
                        
                        
                    case "delete":
                        
                        //System.out.println("Ejecutar instrucción Delete");
                        //System.out.println(res[1]);
                        ptr = res[1];
                        if(simComputer1.isReadyFlag() && simComputer2.isReadyFlag()){
                            simComputer1.executeDeleteInstruction(Integer.valueOf(ptr));    //Se deben convertir ambos valores de Strings a enteros
                            //simComputer2.executeDeleteInstruction(Integer.valueOf(ptr));
                        }
                        break;
                        
                    case "kill":    
                        //System.out.println("Ejecutar instrucción Kill");
                        //System.out.println(res[1]);
                        pid = res[1]; 
                        if(simComputer1.isReadyFlag() && simComputer2.isReadyFlag()){
                            simComputer1.executeKillInstruction(Integer.valueOf(pid));    //Se deben convertir ambos valores de Strings a enteros
                            //simComputer2.executeKillInstruction(Integer.valueOf(pid));      
                        }
                        break;
                }      
        }   
    }
    
    public void assignAlgorithm(int alg1, int alg2){
        simComputer1.setAlgorithm(alg1);
        //simComputer2.setAlgorithm(alg2);
       
    }
    
    public static void main(String[] args) throws IOException {       
        //readInstructions();
        //simComputer1.printMemoryMap();      
        //simComputer1.printSymbolTable();
        //simComputer1.printRealMemory();
        
        SimulationInterface simulation = new SimulationInterface();
        simulation.setVisible(true);
        simulation.setLocationRelativeTo(null);
    }
}
