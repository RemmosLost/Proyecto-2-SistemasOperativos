package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMU {
    
    //private String instructions;
    private int pageCounter;
    private int currentPtr;
    private ArrayList<Page> realMemory;                        
    //private Page[] realMemory2;
    private ArrayList<Process> processes;                   //Guarda los procesos que están existiendo actualemente (No necesariamente ejecutándose)
    private SymbolTable symbolTable;                        //Guarda punteros y su lista de páginas          
    private Map<Integer, ArrayList<Integer>> memoryMap;     //Guarda Procesos y su lista de punteros
    

    public MMU() {
        //this.instructions = instructions;
        this.currentPtr = 1;          
        this.processes = new ArrayList<Process>();
        this.realMemory = new ArrayList<Page>();
        this.symbolTable = new SymbolTable();
        this.memoryMap = new HashMap<>(); 
        
        //this.realMemory2 = new Page[100];
    }

   /*Hacer Getters y Setters****   */
    
    
    public void newInstruction(int pid, int size){
        /*
        Función que asigna memoria para procesos nuevos o existentes .
        Entradas: pid = ProcessId, size = Tamaño en bytes de solicitud de memoria.
        Salidas: N/A
        Restricciones: N/A
    
        NOTA: Requiere refinarse
    
        */
          
        int amountPages = (int) size / (4 * 1024);                          //Se calcula la cantidad de páginas que requiere
        Process actualProcess = null;
        System.out.println("Cant paginas a asignar: " + Integer.toString(amountPages));
        
        if(amountPages >= 100){
            System.out.println("Error: No se puede asignar más de 100 pgs a un proceso");       
            //Debe crearse otro error si se supera la memoria aunque la cantidad de páginas si sea menor que 100?       
        }else{         
            if(!memoryMap.containsKey(pid)){                                 //Si el proceso aún no está en la tabla de memoria, entonces se crea uno nuevo   
                System.out.println("Aqui voy");
                actualProcess = new Process(pid); 
                //System.out.println("PTR = " + actualProcess.getPtr());
                processes.add(actualProcess);                               //Se añade el Proceso a la Lista de Procesos
                memoryMap.put(pid, new ArrayList<>());                      //Luego, Se añade el Proceso "X" al mapa de memoria               
                memoryMap.get(pid).add(currentPtr);                         //Se asigna el puntero actual al Proceso "X" en el mapa de memoria
                System.out.println("Puntero Actual= " + currentPtr);
                symbolTable.addPointer(currentPtr);                         //Se agrega el PTR del Proceso "X" a la Tabla de Símbolos
                
                                                                            //Se crea el nuevo Proceso, Nota: Puede que más bien esta instrucción tenga que ir más abajo 
                                                                            
            }else{                                                          //Sino, se buscan sus datos, el proceso está pidiendo más memoria
                //System.out.println("Proceso " + Integer.toString(pid) + " Solicito mas memoria");
                for(Process p : processes) { 
                    if(p.getProcessID() == pid) { 
                        System.out.println("Goku");
                        actualProcess = p;
                        System.out.println("Puntero Actual= " + currentPtr);
                        memoryMap.get(pid).add(currentPtr);                         //Se asigna el puntero actual al Proceso "X" en el mapa de memoria
                        symbolTable.addPointer(currentPtr);                         //Se agrega el PTR del Proceso "X" al mapa de memoria
                    }
                } 
            }                                                                                            
            
            if(amountPages == 0){                                           //Si el proceso solo tiene asignada una página, se le crea esa página
                createPage(currentPtr,actualProcess);           
            }else{           
                for(int i = 0; i < amountPages; i++){                      //Si el proceso tiene asignada más de una página, se le crean esas páginas                     
                    createPage(currentPtr, actualProcess);  
                }
            }
            
            
            
         }
        currentPtr++;
    }
    
public void createPage(int ptr, Process p){
        /*
        Función que crea una página nueva para un proceso.
        Entradas: pid = ProcessId, p = Proceso.
        Salidas: N/A
        Restricciones: N/A
    
        NOTA: Requiere refinarse
    
        */
        
        Page newPage = new Page();                                          //Se debe crear la página      
        int pageId = newPage.getPageID();                                   //Se obtiene su ID
        
        System.out.println(pageId);
        
        symbolTable.addPageToPointer(ptr,pageId);                           //Asigna la Página al Puntero en la tabla de Símbolos
                                                                            //(Podría cambiarse por el SymbolTable, o tratar el mapa de memoria directamente como el SymbolTable)

        //processes.add(p);                                                 //???- Agrega el nuevo Proceso a la lista de Procesos ejecutándose

        this.realMemory.add(newPage);                                       //Agrega la página a memoria real (Podría quitarse)        
        
        //realMemory[0] = newPage;
                
        //??? - Hay que retornar el índice de la/s página/s que se le asignó?
  
    }
    
    public void printMemoryMap(){
        /*
        Función que imprime en consola los punteros que le corresponden a un mismo proceso.
        Entradas: N/A
        Salidas: N/A
        Restricciones: N/A
        */
        
        System.out.println("---- Punteros asignados a cada Proceso:");
        for (Map.Entry<Integer, ArrayList<Integer>> entry : memoryMap.entrySet()) {
            int key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();

            System.out.println("===Proceso# " + key + ":");
            for (int i = 0; i < value.size(); i++) {
                System.out.println("->Puntero " + value.get(i));
            }
        }
    }
    
   public void printSymbolTable(){
        /*
        Función que imprime en consola las páginas que le corresponden a un mismo puntero.
        Entradas: N/A
        Salidas: N/A
        Restricciones: N/A
        */
        
        symbolTable.printMemoryMap();
    }
        
        
 


}
    
  
    
    
    
    
    
    

    
    


