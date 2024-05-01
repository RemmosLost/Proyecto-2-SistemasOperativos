package com.mycompany.proyecto2.so;

/**
 *
 * @author rebecamadrigal
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMU {
    private List<Pagina> memoriaReal;
    private List<Pagina> memoriaVirtual;
    private Map<Integer, Proceso> procesosEnEjecucion;

    public MMU(int tamañoMemoriaReal) {
        this.memoriaReal = new ArrayList<>(tamañoMemoriaReal);
        this.memoriaVirtual = new ArrayList<>();
        this.procesosEnEjecucion = new HashMap<>();
    }

    public void asignarPagina(Proceso proceso, Pagina pagina) {
        if (memoriaReal.size() < memoriaReal.capacity()) {
            memoriaReal.add(pagina);
            proceso.getTablaSimbolos().put(pagina.getId(), pagina.getDireccionFisica());
            proceso.getPaginasAsignadas().add(pagina.getId());
            procesosEnEjecucion.put(proceso.getPid(), proceso);
        } else {
            memoriaVirtual.add(pagina);
            proceso.getTablaSimbolos().put(pagina.getId(), -1); // Indicar que está en memoria virtual
            proceso.getPaginasAsignadas().add(pagina.getId());
            procesosEnEjecucion.put(proceso.getPid(), proceso);
        }
    }

    public void liberarPaginas(Proceso proceso) {
        for (Integer paginaId : proceso.getPaginasAsignadas()) {
            for (Pagina pagina : memoriaReal) {
                if (pagina.getId() == paginaId) {
                    memoriaReal.remove(pagina);
                    break;
                }
            }
        }
        proceso.getTablaSimbolos().clear();
        proceso.getPaginasAsignadas().clear();
        procesosEnEjecucion.remove(proceso.getPid());
    }

    public void resolverFalloPagina(Proceso proceso, Pagina pagina) {
        // Lógica para resolver un fallo de página
        // Por ejemplo, mover la página de la memoria virtual a la memoria real
        if (memoriaReal.size() < memoriaReal.capacity()) {
            memoriaReal.add(pagina);
            proceso.getTablaSimbolos().put(pagina.getId(), pagina.getDireccionFisica());
            memoriaVirtual.remove(pagina);
        } else {
            // Aplicar algún algoritmo de reemplazo de páginas para liberar espacio en memoria real
            Pagina paginaARemover = // Aplicar algoritmo de reemplazo (FIFO, SC, MRU, etc.)
            for (Proceso p : procesosEnEjecucion.values()) {
                if (p.getTablaSimbolos().containsValue(paginaARemover.getDireccionFisica())) {
                    p.getTablaSimbolos().remove(paginaARemover.getId());
                    p.getPaginasAsignadas().remove(paginaARemover.getId());
                }
            }
            memoriaReal.remove(paginaARemover);
            memoriaReal.add(pagina);
            proceso.getTablaSimbolos().put(pagina.getId(), pagina.getDireccionFisica());
            memoriaVirtual.remove(pagina);
        }
    }

    public void liberarPagina(Proceso proceso, Pagina pagina) {
        // Lógica para liberar una página asignada a un proceso
        proceso.getTablaSimbolos().remove(pagina.getId());
        proceso.getPaginasAsignadas().remove(pagina.getId());
        for (Pagina p : memoriaReal) {
            if (p.getId() == pagina.getId()) {
                memoriaReal.remove(p);
                break;
            }
        }
    }

}
