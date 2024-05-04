
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    /*
    Guarda punteros y su lista de páginas
    */

    private Map<Integer, ArrayList<Integer>> symbolTable;

    public SymbolTable(){
        this.symbolTable = new HashMap<>();
    }

   public void addPointer(int ptr){
      symbolTable.put(ptr, new ArrayList<>());   
   }
   
   public void addPageToPointer(int ptr, int pageId){
       System.out.println("AAAAA" + ptr + "BBBB"+ pageId);
       symbolTable.get(ptr).add(pageId);
       
   }
   
   public void removePointer(int ptr){
       
    
   }
    
    
    
    public void printMemoryMap(){
        /*
        Función que imprime en consola las páginas que le corresponden a un mismo proceso.
        Entradas: N/A
        Salidas: N/A
        Restricciones: N/A
        */
        
        System.out.println("--- Páginas asignadas a cada Puntero:");
        for (Map.Entry<Integer, ArrayList<Integer>> entry : symbolTable.entrySet()) {
            int key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();

            System.out.println("Puntero " + key + ":");
            for (int i = 0; i < value.size(); i++) {
                System.out.println("  Pagina " + value.get(i));
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
