package com.example.cdsm.jpo.Classe;

import java.text.Format;

public class Formation {

    private int id;
    private String lib;
    private int nvformationid;

    public Formation(){}

    public Formation(String lib, int nvformationid){

        this.lib = lib;
        this.nvformationid = nvformationid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public int getNvformationid() {
        return nvformationid;
    }

    public void setNvformationid(int nvformationid) {
        this.nvformationid = nvformationid;
    }

    @Override
    public String toString() {
        return this.lib;
    }
}
