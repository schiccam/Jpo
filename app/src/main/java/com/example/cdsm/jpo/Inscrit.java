package com.example.cdsm.jpo;

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
    private String scolarite1;
    private String scolarite2;
    private String formation1;
    private String formation2;

    public Inscrit(){ }


    public Inscrit(String nom, String prenom, String tel, String mail, String sexe, String dateNaiss,
                   String lieuNaiss, String adresse, String cp, String ville, String scolarite1,
                   String scolarite2, String formation1, String formation2 ){

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
        this.scolarite1 = scolarite1;
        this.scolarite2 = scolarite2;
        this.formation1 = formation1;
        this.formation2 = formation2;
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

    public String getScolarite1() {
        return scolarite1;
    }

    public void setScolarite1(String scolarite1) {
        this.scolarite1 = scolarite1;
    }

    public String getScolarite2() {
        return scolarite2;
    }

    public void setScolarite2(String scolarite2) {
        this.scolarite2 = scolarite2;
    }

    public String getFormation1() {
        return formation1;
    }

    public void setFormation1(String formation1) {
        this.formation1 = formation1;
    }

    public String getFormation2() {
        return formation2;
    }

    public void setFormation2(String formation2) {
        this.formation2 = formation2;
    }
}
