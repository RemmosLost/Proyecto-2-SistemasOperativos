package com.mycompany.proyecto2.so;

/**
 *
 * @author rebecamadrigal
 */
import java.util.List;
import java.util.Map;

public abstract class AlgoritmoPaginacion {
    protected List<Pagina> memoriaReal;
    protected List<Pagina> memoriaVirtual;
    protected Map<Integer, Proceso> procesosEnEjecucion;

    public AlgoritmoPaginacion(List<Pagina> memoriaReal, List<Pagina> memoriaVirtual, Map<Integer, Proceso> procesosEnEjecucion) {
        this.memoriaReal = memoriaReal;
        this.memoriaVirtual = memoriaVirtual;
        this.procesosEnEjecucion = procesosEnEjecucion;
    }

    public abstract void ejecutarSiguienteOperacion();
}

