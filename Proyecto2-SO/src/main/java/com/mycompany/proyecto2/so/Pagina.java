/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2.so;

/**
 *
 * @author rebecamadrigal
 */
public class Pagina {
    private int id;
    private boolean enMemoriaReal;
    private int direccionFisica;

    public Pagina(int id, boolean enMemoriaReal, int direccionFisica) {
        this.id = id;
        this.enMemoriaReal = enMemoriaReal;
        this.direccionFisica = direccionFisica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnMemoriaReal() {
        return enMemoriaReal;
    }

    public void setEnMemoriaReal(boolean enMemoriaReal) {
        this.enMemoriaReal = enMemoriaReal;
    }

    public int getDireccionFisica() {
        return direccionFisica;
    }

    public void setDireccionFisica(int direccionFisica) {
        this.direccionFisica = direccionFisica;
    }
}

