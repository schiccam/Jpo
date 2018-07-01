package com.example.cdsm.jpo.Classe;

public class Stat {
    String libFormation;
    int nbInsParForm;

    public Stat(){};

    public Stat(String libFormation, int nbInsParForm){
        this.libFormation = libFormation;
        this.nbInsParForm = nbInsParForm;
    }

    public String getLibFormation() {
        return libFormation;
    }

    public void setLibFormation(String libFormation) {
        this.libFormation = libFormation;
    }

    public int getNbInsParForm() {
        return nbInsParForm;
    }

    public void setNbInsParForm(int nbInsParForm) {
        this.nbInsParForm = nbInsParForm;
    }
}
