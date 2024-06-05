package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Premonition {
    
    private ArrayList<PremonitionPage> pages;    
    private int currentPtr;
    private ArrayList<Integer> premonitionPages;
    
    private SymbolTable symbolTable;                        //Guarda punteros y su lista de páginas          
    private Map<Integer, ArrayList<Integer>> memoryMap;     //Guarda Procesos y su lista de punteros
    private ArrayList<Process> processes;

    public Premonition() {
        this.processes = new ArrayList<Process>();
        this.pages = new ArrayList<PremonitionPage>();
        this.symbolTable = new SymbolTable();
        this.memoryMap = new HashMap<>(); 
        this.premonitionPages = new ArrayList<Integer>();
        this.currentPtr=1;
    }
    
    
    
    public void executeNewInstruction(int pid, int size){
        int amountPages = (int) size / (4 * 1024); 
        Process actualProcess = null;
        if(amountPages > 100){
            System.out.println("Error: No se puede asignar más de 100 pgs a un proceso");       
            //Debe crearse otro error si se supera la memoria aunque la cantidad de páginas si sea menor que 100?       
        }else{         
            if(!memoryMap.containsKey(pid)){                                 //Si el proceso aún no está en la tabla de memoria, entonces se crea uno nuevo     
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
        
        PremonitionPage newPage = new PremonitionPage();                                          //Se debe crear la página      
        int pageId = newPage.getPageID();                                   //Se obtiene su ID
        
        //System.out.println(pageId);
        
        symbolTable.addPageToPointer(ptr,pageId);                           //Asigna la Página al Puntero en la tabla de Símbolos
                                                                            //(Podría cambiarse por el SymbolTable, o tratar el mapa de memoria directamente como el SymbolTable)

        //processes.add(p);                                                 //???- Agrega el nuevo Proceso a la lista de Procesos ejecutándose

        this.pages.add(newPage);                                       //Agrega la página a memoria real (Podría quitarse)        
        
        //realMemory[0] = newPage;
                
        //??? - Hay que retornar el índice de la/s página/s que se le asignó?
  
    }
        
    public void executeUseInstruction(int ptr){
        /*Función que asigna las páginas de un puntero a memoria real .
        Entradas: ptr = puntero de páginas.
        Salidas: N/A
        Restricciones: N/A
    
        NOTA: Podría variar de acuerdo al algoritmo
        */
        
        
        //this.symbolTable.printMemoryMap();
        
        /*for(int i = 0; i < this.currentPtr;i++){
            
        }*/
        
        ArrayList<Integer> ptrPages = symbolTable.getPointerPages(ptr);     
    
        for(Integer p: ptrPages){
            //System.out.println("Pagina prem = " + p);
            this.premonitionPages.add(p);
        }  
        //this.premonitionPages.add(1);
    } 

    public ArrayList<Integer> getPremonitionPages() {
        return premonitionPages;
    }
       //PRUEBAAAAAAAAAAAAAAAAAAAAA
    
}
