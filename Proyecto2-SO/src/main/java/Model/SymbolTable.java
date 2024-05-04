
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
       //System.out.println("AAAAA" + ptr + "BBBB"+ pageId);
       symbolTable.get(ptr).add(pageId);
       
   }
   
   public ArrayList<Integer> getPointerPages(int ptr){
       
     ArrayList<Integer> pages = symbolTable.get(ptr);
     //Hay que generar un error si el puntero aún no está en la tabla**
     //System.out.println("Paginas asociadas al puntero " + ptr + ": " + pages);  
     return pages;
       
       
   }
   
   
   
   
   
   
   
   public void removePointerPages(int ptr){
       /*
        Función que elimina todas las páginas de un puntero.
        Entradas: ptr = puntero de páginas.
        Salidas: N/A
        Restricciones: N/A
        */
       System.out.println("Se van a remover las páginas del puntero: " + ptr);
       symbolTable.remove(ptr);                                                     //Remueve totalmente las páginas y el puntero
       
       
       
       
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
