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
    
    //private static MMU memory = new MMU();
    private static Computer simComputer1 = new Computer(1);
    private static Computer simComputer2 = new Computer(2);
    
    

    public Controller() {
        //this.pauseFlag = pauseFlag;
        this.pause = true;
        this.simComputer1 = new Computer(1);
        this.simComputer2 = new Computer(2);
        
    }

    public static boolean isPaused() {
        return pause;
    }

    public static void setPause(boolean pause) {
        Controller.pause = pause;
    }
    
 
    
    public void readInstructions(String dir) throws FileNotFoundException, IOException, InterruptedException{
        /*
        Función que cumple con el proceso de Tokenización.
        Entradas: N/A (El archivo podría ser un parámetro en el futuro)
        Salidas: N/A
        Restricciones: Archivo debe ser un TXT.
        */
        
        File file = new File(dir);     //Cambiar de acuerdo a su máquina
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        
        String pid = "";
        String size = "";   
        String ptr = "";
        String[] res;
        
        while ((st = br.readLine()) != null && isPaused() != false){ //Agregar condición de pausa!
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
                //sleep(3000);
        }
    /*System.out.println("");
    System.out.println("TABLA FINAL =================");
    System.out.println("");*/
    printTableInfo();
        
    }
    
    public void assignAlgorithm(int alg1, int alg2){
        simComputer1.setAlgorithm(alg1);
        //simComputer2.setAlgorithm(alg2);
       
    }
    
    public void printTableInfo(){
        ArrayList<String[]> rows = simComputer1.getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println("--pID-PageID-Loaded-Puntero-PosRAM-TimeStmp-Mark");
            System.out.printf("%-6s%-8s%-7s%-9s%-8s%-9s%-4s%n", arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
            System.out.println(""); // Agregar una línea en blanco después de cada arreglo
            //System.out.println(arr);
        }
        
    }
    
    /*public static void generateOperationsFile(long randomSeed, String algorithm, int numProcesses, int numOperations, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            //writer.write("RandomSeed: " + randomSeed);
            //writer.newLine();
            //writer.write("Algorithm: " + algorithm);
            //writer.newLine();
            //writer.write("NumProcesses: " + numProcesses);
            //writer.newLine();
            //writer.write("NumOperations: " + numOperations);
            //writer.newLine();
            // Inicializar el generador de números aleatorios con la semilla proporcionada
            random = new Random(randomSeed);
            // Generar las operaciones aleatorias y escribirlas en el archivo
            for (int i = 0; i < numOperations; i++) {
                String operation = generateRandomOperation(numProcesses);
                writer.write(operation);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error al generar el archivo de operaciones: " + e.getMessage());
        }
    }*/

    /*
    public static void readInstructions(String fileName) {
        try (Scanner scanner = new Scanner(fileName)) {
            while (scanner.hasNextLine()) {
                String instruction = scanner.nextLine();
                System.out.println("Procesando instrucción: " + instruction);
            }
        }    
    }

    public static String generateRandomOperation(int numProcesses) {
        int processId = random.nextInt(numProcesses) + 1;
        int operationCode = random.nextInt(100) + 1;
        String operation;
        if (operationCode <= 30) {
            operation = "new(" + processId + ", " + generateRandomSize() + ")";
        } else if (operationCode <= 60) {
            operation = "use(" + processId + ")";
        } else if (operationCode <= 90) {
            operation = "delete(" + processId + ")";
        } else {
            operation = "kill(" + processId + ")";
        }
        return operation;
    }

    public static int generateRandomSize() {
        return random.nextInt(901) + 100;
    }
*/
    
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
    
    
    public static void main(String[] args) throws IOException {       
        //readInstructions();
        //simComputer1.printMemoryMap();      
        //simComputer1.printSymbolTable();
        //simComputer1.printRealMemory();
        
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la semilla para random:");
        //Scanner scanner = new Scanner(System.in);
        /*System.out.println("Ingrese la semilla para random:");
        long randomSeed = scanner.nextLong();
        System.out.println("Ingrese el algoritmo a simular (FIFO, SC, MRU, RND):");
        String algorithm = scanner.next();
        System.out.println("Ingrese el número de procesos a simular (10, 50, 100):");
        int numProcesses = scanner.nextInt();
        System.out.println("Ingrese la cantidad de operaciones (500, 1000, 5000):");
        int numOperations = scanner.nextInt();
        System.out.println("Ingrese el nombre del archivo para guardar las operaciones:");
        String fileName = scanner.next();
        generateOperationsFile(randomSeed, algorithm, numProcesses, numOperations, fileName);
        System.out.println("El archivo de operaciones ha sido generado exitosamente: " + fileName);
        SimulationInterface simulation = new SimulationInterface();
        simulation.setVisible(true);
        simulation.setLocationRelativeTo(null);*/
        
        //generateRandomInstructions(10,200,1); /**/
        
        readInstructions();
        
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
