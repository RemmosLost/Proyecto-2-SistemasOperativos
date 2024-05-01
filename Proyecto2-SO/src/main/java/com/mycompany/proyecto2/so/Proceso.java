package com.mycompany.proyecto2.so;

/**
 *
 * @author rebecamadrigal
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Proceso {
    private int pid;
    private Map<Integer, Integer> tablaSimbolos; // <ptr, tamaño>
    private List<Integer> paginasAsignadas; // Lista de IDs de páginas asignadas

    public Proceso(int pid) {
        this.pid = pid;
        this.tablaSimbolos = new HashMap<>();
        this.paginasAsignadas = new ArrayList<>();
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Map<Integer, Integer> getTablaSimbolos() {
        return tablaSimbolos;
    }

    public void setTablaSimbolos(Map<Integer, Integer> tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
    }

    public List<Integer> getPaginasAsignadas() {
        return paginasAsignadas;
    }

    public void setPaginasAsignadas(List<Integer> paginasAsignadas) {
        this.paginasAsignadas = paginasAsignadas;
    }
}
