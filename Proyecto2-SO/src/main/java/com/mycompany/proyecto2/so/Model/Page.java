/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2.so.Model;

/**
 *
 * @author rebecamadrigal
 */
public class Page {
    private int pageID;
    private String phyAdress;
    private boolean flag;
    private boolean algorithm;

    public Page(int pageID, String phyAdress, boolean flag, boolean algorithm) {
        this.pageID = pageID;
        this.phyAdress = phyAdress;
        this.flag = flag;
        this.algorithm = algorithm;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public String getPhyAdress() {
        return phyAdress;
    }

    public void setPhyAdress(String phyAdress) {
        this.phyAdress = phyAdress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(boolean algorithm) {
        this.algorithm = algorithm;
    }
}
