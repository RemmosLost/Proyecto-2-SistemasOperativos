package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MMU {
    
    //private String instructions;
    private int alg;
    private int clock;
    private int currentPtr;
    private ArrayList<Page> pages;                        
    private Integer[] realMemory2;                          //Indica qué paginas están en memoria real
    //private Page[] realMemory2;
    private ArrayList<Process> processes;                   //Guarda los procesos que están existiendo actualmente (No necesariamente ejecutándose)
    private SymbolTable symbolTable;                        //Guarda punteros y su lista de páginas          
    private Map<Integer, ArrayList<Integer>> memoryMap;     //Guarda Procesos y su lista de punteros
    
    
    private double VRAM_KB;
    private double RAM;
    
    int hits;                           //Lleva la cantidad de Hits de Página totales
    int faults;                         //Lleva la cantidad de Fallos de Página totales
    int firstOut;                       //Si se está usando FIFO, este contador decide qué índice es el que se debe reemplazar
    int mostRecent;                     //Si se está usando MRU, este contador guarda cuál fué la última página que se agregó

    public MMU(int algorithm) {
        //this.instructions = instructions;
        this.currentPtr = 1;          
        this.processes = new ArrayList<Process>();
        this.pages = new ArrayList<Page>();
        this.symbolTable = new SymbolTable();
        this.memoryMap = new HashMap<>(); 
        this.VRAM_KB = 0.0;
        this.alg = algorithm;
        this.clock = 0;
        
        this.hits = 0;           
        this.faults = 0;         
        this.firstOut = 0;
        this.mostRecent = -1;
        this.realMemory2 = new Integer[25];            //Nota, cambiar tamaño de memoria Real devuelta a 100! También se deben cambiar iteradores del arreglo y limite de parametro firstout
        Arrays.fill(realMemory2, -1);               //Se inicializan los valroes de la memoria real en -1  
    }
    
    
    

   /*Hacer Getters y Setters****   */
    
    

    public int getAlg() {
        return alg;
    }

    public void setAlg(int alg) {
        this.alg = alg;
    }

    public int getThrashingTime() {              //Devuelve el tiempo de Thrashing
        return faults * 5;                      
    }
    
    public ArrayList<String[]> getTableInfo(){  
       ArrayList<String[]> tableInfo = new ArrayList<>();
       
      // String[] row = new String[7];
       String pid = "";
       String PageId = "";
       String loaded = "";          //Puntero
       String logicalAdress = "";
       String memoryAdress = "";    //RAM  
       String loadedTime = "";
       String mark = "";
       
       
       
       for(Map.Entry<Integer,ArrayList<Integer>> entry : this.memoryMap.entrySet() ){           //Para cada proceso en el Mapa de Memoria     
            
            pid = "";               //Id de Proceso
            PageId = "";            //Id de Página
            loaded = "";            //Está cargado en RAM?
            logicalAdress = "";     //Puntero
            memoryAdress = "?";    //Posición en RAM   
            loadedTime = "";        //timestamp
            mark = "";              //Marking

           int processId = entry.getKey();        
           pid = String.valueOf(processId);
           
           
           ArrayList<Integer> pointers = memoryMap.get(processId);         
           for(Integer ptr: pointers){                                                          //Por cada puntero del Mapa de Memoria
               ArrayList<Integer> pages = this.symbolTable.getPointerPages(ptr);                //Obtener la lista de páginas asociadas al puntero
               for(Integer pg : pages){
                   String[] row = new String[7];
                   //Por cada página en la lista        
                   loaded = "NO";
                   logicalAdress = String.valueOf(ptr);
                   memoryAdress = "VRAM";
                   mark = "N/A";
                   for(int i = 0; i < 25; i++){  //Busca si está en memoria real RAM
                       
                       if(this.realMemory2[i] == pg){                       
                           loaded = "YES";
                           //logicalAdress = String.valueOf(ptr);
                           memoryAdress = String.valueOf(i);
                           Page actualPage = searchPageinPageListByID(pg);
                           loadedTime = String.valueOf(actualPage.getTimeStamp()) + "s";
                           if(actualPage.hasSecondChance()){
                               mark = "Has Second Chance";
                           }
                       }      
                   }
                    row[0] = String.valueOf(pg);
                    row[1] = pid;
                    row[2] = loaded;          //Puntero
                    row[3] = logicalAdress;
                    row[4] = memoryAdress;    //RAM  
                    row[5] = loadedTime;
                    row[6] = mark;
                    
                   /* System.out.println("Fila Actual");
                    System.out.println("");
                    System.out.println("PID =" + row[0] + " PageID =" + row[1] + " Loaded =" + row[2] +" LogicalAdress =" + row[3]+ " MemoryAdress =" + row[4]+" TimeStamp =" + row[5] +" Marking =" + row[6]);
                    System.out.println("");*/
                    
                    tableInfo.add(row);
                    
                }
                         
           }
       
       }

       return tableInfo;
                
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public double getVRAM_KB() {
        return VRAM_KB;
    }

    public void setVRAM_KB(double VRAM_KB) {
        this.VRAM_KB = VRAM_KB;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getFaults() {
        return faults;
    }

    public void setFaults(int faults) {
        this.faults = faults;
    }
    
    
    
    
    

    
    
    /////
    
    public void newInstruction(int pid, int size){ ///Size en bytes
        /*
        Función que asigna memoria para procesos nuevos o existentes .
        Entradas: pid = ProcessId, size = Tamaño en bytes de solicitud de memoria.
        Salidas: N/A
        Restricciones: N/A
    
        NOTA: Requiere refinarse
    
        */
          
        int amountPages = (int) size / (4 * 1024);                          //Se calcula la cantidad de páginas que requiere
        this.VRAM_KB += size/ 1024;     
        this.RAM += size / 1024;
        
        //Se calcula la cantidad de páginas que requiere
        Process actualProcess = null;
        System.out.println("NEW PID= "+ pid);       
        System.out.println("Cant paginas a asignar: " + Integer.toString(amountPages));
        
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
        VRAM_KB += size / (4 * 1024); 
        currentPtr++;
    }
    
    
    
    public void useInstruction(int ptr, ArrayList<Integer> premonition){
        /*Función que asigna las páginas de un puntero a memoria real .
        Entradas: ptr = puntero de páginas.
        Salidas: N/A
        Restricciones: N/A
    
        NOTA: Podría variar de acuerdo al algoritmo
        */
        ArrayList<Integer> pages = symbolTable.getPointerPages(ptr);      //Se obtiene la lista de páginas asociadas a un mismo puntero  
        System.out.println("USE PAGINAS= " + pages);
        int pageListSize = pages.size();
        
        
        switch(this.alg){
                case 0: //Algoritmo Óptimo 
                    System.out.println("USE Optimo");
                    for(Integer page: pages){                   //A cada página de la lista de páginas...
                       System.out.println("USE Optimo Aqui voy 1");
                       boolean found = false;            
                        //System.out.println(pages.get(page));
                        found = searchIfItsPageHit(page);            //Caso1: Busca si ya está en Memoria Real
                        this.clock += 1;
                        if(found)                                    //Si se encuentra, se siguen buscando si las demás páginas están
                          continue;
                        
                        System.out.println("USE Optimo Aqui voy 2");
                        boolean emptyFrame = false;    
                        emptyFrame = searchIfThereIsSpace(page);    //Caso 2: Busca si hay espacio libre en memoria    
                        this.clock += 1; 
                        if(emptyFrame)
                           continue;
                       
                        System.out.println("USE Optimo Aqui voy 3");                                            
                        int farthest = -1;                       
                        int replaceIndex = 1;
                        /*for(int j = 0; j < 25; j++){
                            int k;
                            for(k = page + 1; k < 25; k++){
                                if(this.realMemory2[j] == premonition.get(k)){
                                    if(k > farthest ){
                                        farthest = k;
                                        replaceIndex = j;
                                    }
                                    break;
                                 }
                             }
                             if(k == premonition.size()){
                                 replaceIndex = j;
                                 break;
                             }
                            System.out.println("USE Optimo Aqui voy 4");
                            this.realMemory2[replaceIndex] = j;
                            this.clock += 5;
                            Page actualPage = searchPageinPageListByID(page);
                            actualPage.setPhyAdress(this.firstOut);     //Asigna a la página su dirección en memoria RAM 
                            actualPage.setTimeStamp(this.clock);
                            this.firstOut++; 
                            this.faults++;   
                            break;
                         }*/
                      }
                   
                    break;
            
                case 1: //FIFO
                    
                    System.out.println("-----FIFO SELECCIONADO-----");
                    for(Integer page: pages){
                            
                        if(this.firstOut == 25){                       
                            this.firstOut = 0;
                        }
                        
                        System.out.println(">>>>Memoria Actual>>>>   FIFO = " + this.firstOut);
                        printRealMemory2();
                        System.out.println("<<<<Memoria Actual<<<<");
                        
                        boolean found = false;            
                        //System.out.println(pages.get(page));
                        found = searchIfItsPageHit(page);            //Caso1: Busca si ya está en Memoria Real
                        this.clock += 1;
                        if(found)                                    //Si se encuentra, se siguen buscando si las demás páginas están
                          continue;
                        
                        boolean emptyFrame = false;    
                        emptyFrame = searchIfThereIsSpace(page);    //Caso 2: Busca si hay espacio libre en memoria    
                        this.clock += 1; 
                        if(emptyFrame)
                           continue;
                        
                        
                        Page actualPage = searchPageinPageListByID(page);
                        realMemory2[this.firstOut] = page;          //Caso 3: Reemplezar una página de acuerdo a cuál fue la primera página que entró
                        this.clock += 5;
                        actualPage.setPhyAdress(this.firstOut);     //Asigna a la página su dirección en memoria RAM 
                        actualPage.setTimeStamp(this.clock);
                        this.firstOut++; 
                        this.faults++;                              //Aumenta cantidad de fallos de página
                        
                        
                        System.out.println("GOKU+" + this.firstOut);
                        //printRealMemory2();
     
                    }
                    
                    break;
                case 2: //Second Chance
                    System.out.println("-----SECOND CHANCE SELECCIONADO-----");
                    for(Integer page: pages){
if(this.firstOut == 25){                       
                            this.firstOut = 0;
                        }

                        System.out.println(">>>>Memoria Actual>>>>   FIFO = " + this.firstOut + " ENTRA PAGE "+ page);
                        printRealMemory2();
                        System.out.println("<<<<Memoria Actual<<<<");

                        boolean found = false;            
                        //System.out.println(pages.get(page));
                        found = searchIfItsPageHit_SC(page,ptr);            //Caso1: Busca si ya está en Memoria Real
                        this.clock += 1;
                        if(found)                                    //Si se encuentra, se siguen buscando si las demás páginas están
                          continue;

                        boolean emptyFrame = false;    
                        emptyFrame = searchIfThereIsSpace(page);    //Caso 2: Busca si hay espacio libre en memoria    
                        this.clock += 1;
                        if(emptyFrame)
                           continue;
                               
                        
                        //realMemory2[this.firstOut] = page;          //Caso 3: Reemplezar una página de acuerdo a cuál fue la primera página que entró
                                                                        //Aumenta cantidad de fallos de página
                        Page actualPage = searchPageinPageListByID(page);                            
                        for(Page pg: this.pages){                   //Itera toda la lista de páginas existentes
                            //System.out.println("+*+*+*+*+* PAGE = " + page + " PageID = " + pg.getPageID());
                            if(realMemory2[this.firstOut] == pg.getPageID()){             //Si la página en RAM actual está en la lista de páginas existentes    
                               if(pg.hasSecondChance() == true){
                                   System.out.println("===== PAGE = " + pg.getPageID() +"Ya tiene vida extra!");
                                   checkFIFO(); 
                                   for(Page pag: this.pages){
                                        checkNextSC(pag);                           //Chequear si el siguiente campo también tiene Second Chance
                                   }
                                   realMemory2[this.firstOut+1] = page;
                                   this.clock += 5;
                                   this.firstOut++;
                                   pg.setSecondChance(false);
                                   System.out.println("===== PAGE = " + pg.getPageID() +"Vida Extra Usada");
                                   
                                   
                                   
                                   
                               }else{
                                   System.out.println("No hay vidas extra, SE REEEMPLAZA PAGE" + page);
                                   realMemory2[this.firstOut] = page;
                                   this.clock += 5;
                               }  
                               
                            }/*else{
                                System.out.println("===== PAGE = " + pg.getPageID() +" No Tiene vida extra!");
                            }  */
                        }
                        
                        actualPage.setPhyAdress(this.firstOut);     //Asigna a la página su dirección en memoria RAM 
                        actualPage.setTimeStamp(this.clock);
                        this.firstOut++;
                        this.faults++; 
                        
                    }
                    break;
                    
                case 3: //MRU (Most Recent Used)
                    System.out.println("-----MRU SELECCIONADO-----");
                    for(Integer page: pages){
                        System.out.println(">>>>Memoria Actual>>>>   MRU = " + this.mostRecent);
                        printRealMemory2();
                        System.out.println("<<<<Memoria Actual<<<<");
                        
                        boolean found = false;            
                        //System.out.println(pages.get(page));
                        found = searchIfItsPageHit(page);            //Caso1: Busca si ya está en Memoria Real
                        this.clock += 1;
                        
                        if(found)                                    //Si se encuentra, se siguen buscando si las demás páginas están
                          continue;
                        
                        boolean emptyFrame = false;    
<<<<<<< Updated upstream
                        //this.clock += 1;
=======
                        this.clock += 1;
>>>>>>> Stashed changes
                        emptyFrame = searchIfThereIsSpace(page);    //Caso 2: Busca si hay espacio libre en memoria    
                        if(emptyFrame)
                           continue;
                        
                        Page actualPage = searchPageinPageListByID(page);
                        for(int i = 0; i < 25; i++){                 //Caso 3: Busca en toda la memoria si la página actual es la más reciente
                            if(realMemory2[i] == this.mostRecent){  //Si es la página mas reciente...
                                realMemory2[i] = page;              //Se asigna la página nueva a su posición.
                                this.clock += 5;
                            }
                        }
                        
                        this.mostRecent = page;                     //Se actualiza cuál es el nuevo, más reciente
                        actualPage.setPhyAdress(this.firstOut);     //Asigna a la página su dirección en memoria RAM 
                        actualPage.setTimeStamp(this.clock);                     
                        this.faults++;                              //Aumenta cantidad de fallos de página
                    }
                    break;
                case 4: //Random
                    System.out.println("-----FIFO SELECCIONADO-----");
                    Random rand = new Random();
                    //int randomNumber = rand.nextInt(100 - 0 + 1) + 0;
                    int randomNumber = rand.nextInt(4 - 0 + 1) + 0;
                    System.out.println("Marco de Pagina RANDOM= " + randomNumber);
                    for(Integer page: pages){
                        
                        //System.out.println(">>>>Memoria Actual>>>>" + this.mostRecent);
                        //printRealMemory2();
                        //System.out.println("<<<<Memoria Actual<<<<");
                        
                        boolean found = false;            
                        //System.out.println(pages.get(page));
                        found = searchIfItsPageHit(page);            //Caso1: Busca si ya está en Memoria Real
<<<<<<< Updated upstream
                        //this.clock += 1;
=======
                        this.clock += 1;
>>>>>>> Stashed changes
                        
                        if(found)                                    //Si se encuentra, se siguen buscando si las demás páginas están
                          continue;
                        
                        boolean emptyFrame = false;    
                        emptyFrame = searchIfThereIsSpace(page);    //Caso 2: Busca si hay espacio libre en memoria    
<<<<<<< Updated upstream
                        //this.clock += 1;
=======
                        this.clock += 1;
>>>>>>> Stashed changes
                        if(emptyFrame)
                           continue;
                        
                        Page actualPage = searchPageinPageListByID(page);
                        realMemory2[randomNumber] = page;           //CASO 3: Selecciona un marco de página random y cambia su página a la nueva
                        this.clock += 5;
                        actualPage.setPhyAdress(this.firstOut);     //Asigna a la página su dirección en memoria RAM 
                        actualPage.setTimeStamp(this.clock);
                        this.faults++;                              //Aumenta cantidad de fallos de página
                        
                    }
                    break;               
        }
    }
    
    
    public void deleteInstruction(int ptr){
        /*Función que elimina las páginas de un puntero.
        Entradas: ptr = Puntero de páginas.
        Salidas: N/A
        Restricciones: N/A
    
        NOTA: Podría variar de acuerdo al algoritmo
        */

        symbolTable.removePointerPages(ptr);                                    //Remueve totalmente las páginas y el puntero de la tabla de símbolos
        System.out.println("DELETE PTRS= " + ptr);
        //ArrayList<Integer> values = memoryMap.get(ptr);                       //Se está buscando por PTR, debe ser por PID!!!!!!!!
        
        
        //Hay que buscar en cada llave cuál contiene el ptr, y luego borrar el ptr
        Integer pidKey = null;      
        for(Map.Entry<Integer, ArrayList<Integer>> entry : memoryMap.entrySet()){
           ArrayList<Integer> ptrList = entry.getValue();
           
           if(ptrList.contains(ptr)){                                                   //Si un Proceso del Mapa de Memoria contiene el puntero    
              pidKey = entry.getKey();                                                  //Se obtiene el PID del iterador
              //ArrayList<Integer> ptrList = memoryMap.get(pidKey);                       //Se obtiene la lista de punteros del PID
              System.out.println("PROCESS ID = "+ pidKey + " VALUES= " + ptrList);
              ptrList.remove(Integer.valueOf(ptr));                                                                        //Se remueve el ptr de la lista de punteros
              System.out.println("PROCESS ID = "+ pidKey + " VALUES= " + ptrList);
              break;
          }
        }
        //memoryMap.remove(pidKey);
        //symbolTable.removePointerPages(ptr);                                    //Remueve totalmente las páginas y el puntero de la tabla de símbolos
    
    }
    
    
    
    public void killInstruction(int pid){
        /*Función que elimina un proceso del mapa de memoria y borra sus punteros y las páginas asociadas a ese puntero.
        Entradas: pid = Id del Proceso a borrar.
        Salidas: N/A
        Restricciones: N/A
    
        NOTA: Podría variar de acuerdo al algoritmo
        */
        
        ArrayList<Integer> ptrList = memoryMap.get(pid); 
        System.out.println("KILL PID= "+ pid+ " LIST= " + ptrList);
        
        for(Integer i: ptrList){
            symbolTable.removePointerPages(i);
            System.out.println("PROCESS ID = "+ pid + " ELIMINATED PAGE= " + i);
        }
        
        memoryMap.remove(pid);
        System.out.println("REMOVED PID= " + pid);
        
        //Process processToKill; 
        //processes.remove()
        //System.out.println("KILL PID= " + pid + " PTRS= "+ ptrList);
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
        
        //System.out.println(pageId);
        
        symbolTable.addPageToPointer(ptr,pageId);                           //Asigna la Página al Puntero en la tabla de Símbolos
                                                                            //(Podría cambiarse por el SymbolTable, o tratar el mapa de memoria directamente como el SymbolTable)

        //processes.add(p);                                                 //???- Agrega el nuevo Proceso a la lista de Procesos ejecutándose

        this.pages.add(newPage);                                       //Agrega la página a memoria real (Podría quitarse)        
        
        //realMemory[0] = newPage;
                
        //??? - Hay que retornar el índice de la/s página/s que se le asignó?
  
    }
    
    
    
    
    
    public boolean searchIfItsPageHit(Integer p){
        boolean found = false; 
        //System.out.println(pages.get(page));
        for(int i = 0; i < 25; i++){             //Caso 1: Se le busca si está en memoria Real
            if(this.realMemory2[i] == p){
                this.hits++;
                
                found = true;                    //Si la encuentra, es un hit de página, Nota: asignar tiempo de reloj +1
                this.mostRecent = p;
                System.out.println("Es un HIT!!!");          
                break;  
            }
        }
        return found;
        //return hits;
    }
        
     public boolean searchIfItsPageHit_SC(Integer p, Integer ptr){
        boolean found = false; 
        //System.out.println(pages.get(page));
        for(int i = 0; i < 25; i++){             //Caso 1: Se le busca si está en memoria Real
            if(this.realMemory2[i] == p){
                this.hits++;
                this.clock += 1;
                found = true;                    //Si la encuentra, es un hit de página, Nota: asignar tiempo de reloj +1
                //this.mostRecent = p;
                System.out.println("Es un HIT!!!");
                if(this.alg == 2){              //Si el algoritmo de paginación es Second Chance
                    ArrayList<Integer> currentPages = this.symbolTable.getPointerPages(ptr);    //Se traen todas las páginas que usará el puntero
                   // for(Integer page: currentPages){                //Para cáda página de la lista 
                        for(Page pg: this.pages){                   //Se revisan todas las páginas
                            if(pg.getPageID() == this.realMemory2[i]){             //Si la página actual está en la lista de páginas existentes    
                            pg.setSecondChance(true);           //Se le da un Secon Chance, una "vida" extra
                            System.out.println("Page =" + p + "Vida Extra+1");
                            }
                        }
                            
                            /*if(pg.hasSecondChance()){
                                this.firstOut++; 
                            }*/
                        //}
                    //}
                }
                break;  
            }
        }      
        
        
        return found;
        //return hits;
    }
    
    
    public boolean searchIfThereIsSpace(Integer p){
        boolean emptyFrame = false;    
        for(int i = 0; i < 25; i++){               //Caso 2: Se busca por toda memoria Real si hay un espacio vacío, ie, -1
            if(this.realMemory2[i] == -1){               //Si el campo está vacio  
                this.realMemory2[i] = p;              //Se agrega la página al espacio vacío
                //Cambiar estado de Dirección Física de la página actual
                Page actualPage = searchPageinPageListByID(p);
                actualPage.setTimeStamp(this.clock);
                actualPage.setPhyAdress(i);
                this.clock += 1; 
                //this.firstOut++;
                this.mostRecent = p;
                emptyFrame = true;
                break;
            }   
        }
        //printRealMemory2();
        return emptyFrame;
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
    
   public void checkFIFO(){
       if(this.firstOut == 24){ 
            System.out.println("CAMBIA FIFO 24->0 ");
            this.firstOut = 0;
      }
   }
   
  public void checkNextSC(Page pg){
       if(this.firstOut == 24){ 
            System.out.println("CAMBIA FIFO 24->0 ");
            this.firstOut = 0;
       }
       if(realMemory2[this.firstOut+1] == pg.getPageID()){             //Si la página en RAM actual está en la lista de páginas existentes    
            if(pg.hasSecondChance() == true){
                this.firstOut++;
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
    
    public void printRealMemory2(){
         /*
         Función que imprime en consola las páginas que le corresponden a un mismo puntero.
         Entradas: N/A
         Salidas: N/A
         Restricciones: N/A
         */
         for(int i = 0; i < 25; i++){
             System.out.println("Memoria Real, Indice Marco = " + i + " Pagina= " + this.realMemory2[i]);
         }
         


     }
  
    public Page searchPageinPageListByID(int pId){
        Page res = null;
        for(Page p: this.pages){
            if(p.getPageID() == pId){
                res = p;
            }    
        }
        return res;
    }
   public int totalProcesses(){
        int total = processes.size();
        System.out.println("Total de procesos: " + total);
        return total;
    }

    public double getRAM() {
        System.out.println("Total de RAM: " + RAM);
        return RAM;
    }
    
    public double getRAMPercentage() {
        System.out.println("Total de RAM%: " + RAM / 100);
        return RAM / 100;
    }

  
}
    
    
    
    
    

    
    


