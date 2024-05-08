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
        this.memory = new MMU(pAlgorithm);
        this.readyFlag = true;

    }
    

    public MMU getMemory() {
        return memory;
    }

    public void setMemory(MMU memory) {
        this.memory = memory;
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
        this.memory.newInstruction(pid, size);
        this.readyFlag = true;
        getMemoryTableInfo();
        System.out.println("Tabla actual");
        ArrayList<String[]> rows = getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3] + ", " + arr[4] + ", " + arr[5] + ", " + arr[6]);
            System.out.println("");
        }
    }
    
    public void executeUseInstruction(int ptr, ArrayList<Integer> premonition){
         this.readyFlag = false;
         this.memory.useInstruction(ptr, premonition);
         this.readyFlag = true;
         //getMemoryTableInfo();
         System.out.println("Tabla actual");
        ArrayList<String[]> rows = getMemoryTableInfo();
        for(String[] arr: rows ){
            System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3] + ", " + arr[4] + ", " + arr[5] + ", " + arr[6]);
            System.out.println("");
        }
    }
    
    public void executeDeleteInstruction(int ptr){
         this.readyFlag = false;
         this.memory.deleteInstruction(ptr);
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
         this.memory.killInstruction(pid);
         
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
         this.memory.printSymbolTable(); 
    }
    
    public void printMemoryMap(){
        this.memory.printMemoryMap();
    }
    
    public void printRealMemory(){
        memory.printRealMemory2();
    }
    
    public ArrayList<String[]> getMemoryTableInfo(){
        return memory.getTableInfo();
    }
    
    
}
