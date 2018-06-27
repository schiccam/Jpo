package com.example.cdsm.jpo.Classe;

public class NiveauFormation {

    int id;
    String nvFormLib;

    public NiveauFormation(int id, String nvFormLib){
        this.id = id;
        this.nvFormLib = nvFormLib;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNvFormLib() {
        return nvFormLib;
    }

    public void setNvFormLib(String nvFormLib) {
        this.nvFormLib = nvFormLib;
    }
}
