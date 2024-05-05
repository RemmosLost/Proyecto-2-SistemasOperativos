package Model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Process {
    private int processID;
    //private int ptr;                          //El proceso no debe llevar un puntero, debe llevarlo el mapa de memoria de manera que asocie una lista de páginas a cada ptr
    private String Instructions;

    
    public Process(int processID/*, int ptr*/) {
        this.processID = processID;
        //this.ptr = ptr;
    }
    
    public int getProcessID() {
        return processID;
    }

    /*public int getPtr() {
        return ptr;
    }*/
    
     /*public void setPtr(int ptr) {
        this.ptr = ptr;
    }*/

    public String getInstructions() {
        return Instructions;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public void setInstructions(String Instructions) {
        this.Instructions = Instructions;
    }
    
    
    
    
    
}
