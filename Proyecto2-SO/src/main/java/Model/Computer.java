package Model;

import java.util.ArrayList;

public class Computer {
    
    private int algorithm;
    private int clock;
    private double VRAM_KB;
    private MMU memory; 
    private boolean readyFlag;
    
    public Computer(int pAlgorithm){
        
        this.algorithm = pAlgorithm;
        this.clock = 0;
        this.VRAM_KB = 0.0;
        this.memory = new MMU();
        this.readyFlag = true;

    }
    

    public int getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isReadyFlag() {
        return readyFlag;
    }

    public void setReadyFlag(boolean readyFlag) {
        this.readyFlag = readyFlag;
    }
   
    public void addtoVRAM(int size){
        this.VRAM_KB += size / 4 * 1024;
    }
    
    public void substracttoVRAM(int size){
        this.VRAM_KB -= size / 4 * 1024;
    }
    
    public void executeNewInstruction(int pid, int size){
        this.readyFlag = false;
        memory.newInstruction(pid, size);
        this.readyFlag = true;
        //getMemoryTableInfo();
        /*System.out.println("Tabla actual");
        ArrayList<String[]> rows = getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3] + ", " + arr[4] + ", " + arr[5] + ", " + arr[6]);
            System.out.println("");
        }*/
    }
    
    public void executeUseInstruction(int ptr){
        this.readyFlag = false;
         memory.useInstruction(ptr);
         this.readyFlag = true;
         //getMemoryTableInfo();
         /*System.out.println("Tabla actual");
        ArrayList<String[]> rows = getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3] + ", " + arr[4] + ", " + arr[5] + ", " + arr[6]);
            System.out.println("");
        }*/
    }
    
    public void executeDeleteInstruction(int ptr){
         this.readyFlag = false;
         memory.deleteInstruction(ptr);
         this.readyFlag = true;
         //getMemoryTableInfo();
        /*System.out.println("Tabla actual");
        ArrayList<String[]> rows = getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3] + ", " + arr[4] + ", " + arr[5] + ", " + arr[6]);
            System.out.println("");
        }*/
    }
    
    public void executeKillInstruction(int pid){
         this.readyFlag = false;
         memory.killInstruction(pid);
         
         this.readyFlag = true;
         //getMemoryTableInfo();
         /*System.out.println("Tabla actual");
        ArrayList<String[]> rows = getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3] + ", " + arr[4] + ", " + arr[5] + ", " + arr[6]);
            System.out.println("");
        }*/
    }
    
    public void printSymbolTable(){
         memory.printSymbolTable(); 
    }
    
    public void printMemoryMap(){
    memory.printMemoryMap();
    }
    
    public void printRealMemory(){
        memory.printRealMemory2();
    }
    
    public ArrayList<String[]> getMemoryTableInfo(){
        return memory.getTableInfo();
    }
    
    
}
