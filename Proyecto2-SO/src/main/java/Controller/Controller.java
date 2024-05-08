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
import java.io.BufferedWriter;
import java.io.FileWriter;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;

public class Controller {

    private Controller controller;
    private static Random random;
    private static boolean pause;
    //private ArrayList<String[]> simComputer1MemoryTable;
    //private ArrayList<String[]> simComputer2MemoryTable;
    
    //private static MMU memory = new MMU();
    private static Computer simComputer1 = new Computer(1);
    private static Computer simComputer2 = new Computer(2);
    
    

    public Controller(/*int pAlgorithm/*,int pAlgorithm*/) {
        //this.pauseFlag = pauseFlag;
        this.pause = true;  
    }
    public void startComputers(int pAlgorithm){
        this.simComputer1 = new Computer(pAlgorithm);
        this.simComputer2 = new Computer(0);
    }

    public static boolean isPaused() {
        return pause;
    }

    public static void setPause(boolean pause) {
        Controller.pause = pause;
    }
    
    
    
    public ArrayList<String[]> readInstructions(String st/*String dir*/) throws FileNotFoundException, IOException, InterruptedException{
        /*
        Función que cumple con el proceso de Tokenización.
        Entradas: N/A (El archivo podría ser un parámetro en el futuro)
        Salidas: N/A
        Restricciones: Archivo debe ser un TXT.
        */
        
        /*File file = new File(dir);     //Cambiar de acuerdo a su máquina
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;*/
        
        String pid = "";
        String size = "";   
        String ptr = "";
        String[] res;
        
        //while ((st = br.readLine()) != null && isPaused() != false){ //Agregar condición de pausa!
                
                
                
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
                        /*System.out.println("");
                        System.out.println("TABLA Actual =================");
                        System.out.println("");
                        printTableInfo();*/
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
                //sleep(2000);
                return simComputer1.getMemoryTableInfo();
        }
    /*System.out.println("");
    System.out.println("TABLA FINAL =================");
    System.out.println("");*/
    //printTableInfo();
        
    
    
    public void assignAlgorithm(int alg1/*, int alg2*/){
        simComputer1.setAlgorithm(alg1);
        //simComputer2.setAlgorithm(alg2);
       
    }
    
    public ArrayList<String[]> getMemoryTableInfo(){
        /*ArrayList<String[]> rows = simComputer1.getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println("--pID-PageID-Loaded-Puntero-PosRAM-TimeStmp-Mark");
            System.out.printf("%-6s%-8s%-7s%-9s%-8s%-9s%-4s%n", arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
            System.out.println(""); // Agregar una línea en blanco después de cada arreglo
            //System.out.println(arr);
        }
        */
        
        return simComputer1.getMemoryTableInfo();
        
       
    }
    
    public static void generateRandomInstructions(int amountProcesses, int amountOps, int pSeed){ //
        long seed = pSeed;
        Random rand = new Random(seed);    
        //Random rand = new Random();
        
        String lastInstruction = "nada";
        String[] instructionSet = {"new", "use", "delete", "kill"};
        
        int existingNew = 0;        //Cuenta cantidad de instrucciones new
        int existingUse = 0;        //Cuenta cantidad de instrucciones use
        int existingDelete = 0;     //Cuenta cantidad de instrucciones delete
        int existingKill = 0;       //Cuenta cantidad de instrucciones kill
        
        int pId = 1;                //Cuenta los procesos nuevos que se crean
        int ptrs = 1;               //Cuenta la cantidad existente de punteros, cada instrucción new crea un puntero nuevo
        
        //ArrayList<Integer> allPID;
        //ArrayList<Integer> allPTR;
        
        
        String generatedInstructions = "";
        
        for(int i = 0; i < amountOps; i++){
            int randomNumber = rand.nextInt(3 - 0 + 1) + 0;  
            
            String ins = instructionSet[randomNumber];
            String line;
            switch (ins){
                case "new": //Proceso pide memoria
                    if( (existingNew < amountProcesses) && !lastInstruction.equals("kill")){
                        int randomMemorySize = rand.nextInt(409600 - 1 + 1) + 1;        //204 800 (hace 50 págs)        409600 (hace 100 páginas)
                        line = "new("+pId+","+randomMemorySize+")" +"\n";
                        generatedInstructions += line;
                        lastInstruction = "new";
                        existingNew++;
                        pId++;
                        ptrs++;
                        
                    }
                    break;
                    
               case "use":
                    if(ptrs > 0){                                   //Si existe al menos un puntero, NOTa: Tal vez se deba revisar 
                        int randomPointer = rand.nextInt((pId) - 1 + 1) + 1;
                        line = "use("+randomPointer+")" +"\n";
                        generatedInstructions += line;
                        lastInstruction = "use";
                        existingUse++;
                   }
                   
                    break;
                    
               case "delete":
                    if(ptrs > 0){ 
                        line = "delete(" + ")" +"\n";
                        generatedInstructions += line;
                        lastInstruction = "delete";
                        existingDelete++;
                        break;
                    }
                    
               case "kill":
                    line = "kill(" + ")" +"\n";
                    generatedInstructions += line;
                    lastInstruction = "kill";
                    existingKill++;
                    break; 
            }
 
        }
        
    System.out.println("Cant NEW = " + existingNew);  
    System.out.println("Cant USE = " + existingUse); 
    System.out.println("Cant DELETE = " + existingDelete); 
    System.out.println("Cant KILL = " + existingKill); 
    
    System.out.println("Cant pid= " + (pId-1));
    System.out.println("Cant ptrs = " + (ptrs-1));
    
    System.out.println(generatedInstructions);    
    }
    
    public String[] getAmountProcesses(){
        String[] res = new String[1];
        res[0] = String.valueOf(simComputer1.getMemory().totalProcesses());
        
        return res;
    }
    
    
    public String[] getAmountThrashing(){
        String[] res = new String[1];
        res[0] = String.valueOf(simComputer1.getMemory().getThrashingTime()) + "s";
        
        return res;
    }
    
    public String[] getVRAM(){
        String[] res = new String[1];
        res[0] = String.valueOf(simComputer1.getMemory().getVRAM_KB()) + "KB";
        
        return res;
    }
    
    
    
    
    
    
    
    public static void main(String[] args) throws IOException {       
        //readInstructions();
        //simComputer1.printMemoryMap();      
        //simComputer1.printSymbolTable();
        //simComputer1.printRealMemory();
       
        //generateRandomInstructions(10,200,1); /**/
        
        //readInstructions();
        
        //simComputer1.printMemoryMap();      
        //simComputer1.printSymbolTable();
        //simComputer1.printRealMemory();
        
        //SimulationInterface simulation = new SimulationInterface();
        //simulation.setVisible(true);
        //simulation.setLocationRelativeTo(null);
        
        simComputer1.getMemory().totalProcesses();
        simComputer1.getMemory().getRAM();
        simComputer1.getMemory().getRAMPercentage();
        
    }
}
