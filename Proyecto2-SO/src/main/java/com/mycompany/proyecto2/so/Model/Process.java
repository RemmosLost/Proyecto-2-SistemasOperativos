package com.mycompany.proyecto2.so.Model;

/**
 *
 * @author rebecamadrigal
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Process {
    private int processID;
    private int ptr;
    private String Instructions;

    public int getProcessID() {
        return processID;
    }

    public int getPtr() {
        return ptr;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public void setPtr(int ptr) {
        this.ptr = ptr;
    }

    public void setInstructions(String Instructions) {
        this.Instructions = Instructions;
    }
    
    
    
    
    
}
