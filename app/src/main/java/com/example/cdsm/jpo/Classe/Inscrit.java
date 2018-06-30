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
                   String scolarite2, int formation){

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
        //TODO Modifier la base de données pour la scolariter
       /*this.libSco1;
        this.etabSco1;
        this.anneeSco2;
        this.libSco2;
        this.etabSco2;*/
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

    public int getFormation() {
        return formation;
    }

    public void setFormation(int formation) {
        this.formation = formation;
    }

}
