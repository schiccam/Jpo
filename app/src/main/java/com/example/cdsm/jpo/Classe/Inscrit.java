package com.example.cdsm.jpo.Classe;

public class Inscrit {

    private int id;
    private String nom;
    private String prenom;
    private String tel;
    private String mail;
    private String sexe;
    private String dateNaiss;
    private String lieuNaiss;
    private String adresse;
    private String cp;
    private String ville;
    private String anneeSco1;
    private String libSco1;
    private String etabSco1;
    private String anneeSco2;
    private String libSco2;
    private String etabSco2;
    private int formation;

    public Inscrit(){ }


    public Inscrit(String nom, String prenom, String tel, String mail, String sexe, String dateNaiss,
                   String lieuNaiss, String adresse, String cp, String ville, String anneeSco1,
                   String libSco1, String etabSco1, String anneeSco2, String libSco2, String etabSco2,
                   int formation){

        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
        this.sexe = sexe;
        this.dateNaiss = dateNaiss;
        this.lieuNaiss = lieuNaiss;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.anneeSco1 = anneeSco1;
        this.libSco1 = libSco1;
        this.etabSco1 = etabSco1;
        this.anneeSco2 = anneeSco2;
        this.libSco2 = libSco2;
        this.etabSco2 = etabSco2;
        this.formation = formation;

    }
    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAnneeSco1() {
        return anneeSco1;
    }

    public void setAnneeSco1(String anneeSco1) {
        this.anneeSco1 = anneeSco1;
    }

    public String getLibSco1() {
        return libSco1;
    }

    public void setLibSco1(String libSco1) {
        this.libSco1 = libSco1;
    }

    public String getEtabSco1() {
        return etabSco1;
    }

    public void setEtabSco1(String etabSco1) {
        this.etabSco1 = etabSco1;
    }

    public String getAnneeSco2() {
        return anneeSco2;
    }

    public void setAnneeSco2(String anneeSco2) {
        this.anneeSco2 = anneeSco2;
    }

    public String getLibSco2() {
        return libSco2;
    }

    public void setLibSco2(String libSco2) {
        this.libSco2 = libSco2;
    }

    public String getEtabSco2() {
        return etabSco2;
    }

    public void setEtabSco2(String etabSco2) {
        this.etabSco2 = etabSco2;
    }

    public int getFormation() {
        return formation;

    }

    public void setFormation(int formation) {
        this.formation = formation;
    }

}
