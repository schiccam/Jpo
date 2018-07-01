package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cdsm.jpo.Interface.DAO;

import java.util.ArrayList;
import java.util.List;

public class InscritDAO implements DAO<Inscrit> {

    private SQLiteDatabase db;
    private MaBaseSQLite maBaseSQLite;

    public InscritDAO(Context context){
        maBaseSQLite =  new MaBaseSQLite(context);
    }

    private void opendb(){
        db = maBaseSQLite.getWritableDatabase();
    }

    private void closedb(){
        db.close();
    }

    @Override
    public void Ajouter(Inscrit item) {

        String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss, adresse, cp, ville, anneeSco1,
                libSco1, etabSco1, anneeSco2, libSco2, etabSco2;
        int form;

        nom = item.getNom();
        prenom = item.getPrenom();
        tel = item.getTel();
        mail = item.getMail();
        sexe = item.getSexe();
        datenaiss = item.getDateNaiss();
        lieunaiss = item.getLieuNaiss();
        adresse = item.getAdresse();
        cp = item.getCp();
        ville = item.getVille();
        anneeSco1 = item.getAnneeSco1();
        libSco1 = item.getLibSco1();
        etabSco1 = item.getEtabSco1();
        anneeSco2 = item.getAnneeSco2();
        libSco2 = item.getLibSco2();
        etabSco2 = item.getEtabSco2();
        form = item.getFormation();

        String query = "INSERT INTO Inscrit(insNom,insPrenom,insTel,insMail,insSexe,insDateNaiss" +
                ",insLieuNaiss,insAdresse,insCP,insVille,insAnneeSco1,insLibSco1,insEtabSco1," +
                "insAnneeSco2,insLibSco2,insEtabSco2,insFormation)" +
                "VALUES ('"+nom+"','"+prenom+"','"+tel+"','"+mail+"','"+sexe+"','"+datenaiss+"','"+lieunaiss+"','"+adresse+
                "','"+cp+"','"+ville+"','"+anneeSco1+"','"+libSco1+"' ,'"+etabSco1+"' ," +
                "'"+anneeSco2+"','"+libSco2+"' ,'"+etabSco2+"',"+form+");";



        opendb();

        db.execSQL(query);

        closedb();
    }

    @Override
    public void Supprimer(int id) {

        opendb();

        db.execSQL("DELETE FROM Inscrit WHERE ins_id = "+id+";");

        closedb();

    }

    @Override
    public void Modifier(Inscrit item) {

        String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss, adresse, cp, ville, anneeSco1,
                libSco1, etabSco1, anneeSco2, libSco2, etabSco2;
        int id, form;

        id = item.getId();
        nom = item.getNom();
        prenom = item.getPrenom();
        tel = item.getTel();
        mail = item.getMail();
        sexe = item.getSexe();
        datenaiss = item.getDateNaiss();
        lieunaiss = item.getLieuNaiss();
        adresse = item.getAdresse();
        cp = item.getCp();
        ville = item.getVille();
        anneeSco1 = item.getAnneeSco1();
        libSco1 = item.getLibSco1();
        etabSco1 = item.getEtabSco1();
        anneeSco2 = item.getAnneeSco2();
        libSco2 = item.getLibSco2();
        etabSco2 = item.getEtabSco2();
        form = item.getFormation();

        String query = "UPDATE Inscrit SET insNom = '"+nom+"', insPrenom = '"+prenom+"', insTel = '"+tel+"'" +
                ", insMail = '"+mail+"', insSexe = '"+sexe+"', insDatenaiss = '"+datenaiss+"', insLieuNaiss = '"+lieunaiss+"'" +
                ", insAdresse = '"+adresse+"', insCP = '"+cp+"', insVille = '"+ville+"', insAnneeSco1 = '"+anneeSco1+"'" +
                ", insLibSco1 = '"+libSco1+"', insEtabSco1 = '"+etabSco1+"', insAnneeSco2 = '"+anneeSco2+"'," +
                "insLibSco2 = '"+libSco2+"', insEtabSco1 = '"+etabSco2+"', insFormation = "+form+" WHERE ins_id = "+id+";";

        opendb();

        db.execSQL(query);

        closedb();

    }

    public Inscrit getInscrit(int id){

        Inscrit inscrit = new Inscrit();

        opendb();

        Cursor cr = db.rawQuery("SELECT * FROM Inscrit WHERE ins_id = "+id+";",null);
        if (cr.moveToFirst()) {
            do {
                inscrit.setId(cr.getInt(cr.getColumnIndex("ins_id")));
                inscrit.setNom(cr.getString(cr.getColumnIndex("insNom")));
                inscrit.setPrenom(cr.getString(cr.getColumnIndex("insPrenom")));
                inscrit.setTel(cr.getString(cr.getColumnIndex("insTel")));
                inscrit.setMail(cr.getString(cr.getColumnIndex("insMail")));
                inscrit.setSexe(cr.getString(cr.getColumnIndex("insSexe")));
                inscrit.setDateNaiss(cr.getString(cr.getColumnIndex("insDateNaiss")));
                inscrit.setLieuNaiss(cr.getString(cr.getColumnIndex("insLieuNaiss")));
                inscrit.setAdresse(cr.getString(cr.getColumnIndex("insAdresse")));
                inscrit.setCp(cr.getString(cr.getColumnIndex("insCP")));
                inscrit.setVille(cr.getString(cr.getColumnIndex("insVille")));
                inscrit.setAnneeSco1(cr.getString(cr.getColumnIndex("insAnneeSco1")));
                inscrit.setLibSco1(cr.getString(cr.getColumnIndex("insLibSco1")));
                inscrit.setEtabSco1(cr.getString(cr.getColumnIndex("insEtabSco1")));
                inscrit.setAnneeSco2(cr.getString(cr.getColumnIndex("insAnneeSco2")));
                inscrit.setLibSco2(cr.getString(cr.getColumnIndex("insLibSco2")));
                inscrit.setEtabSco2(cr.getString(cr.getColumnIndex("insEtabSco2")));
                inscrit.setFormation(cr.getInt(cr.getColumnIndex("insFormation")));
            }
            while (cr.moveToNext());
            cr.close();
        }

        closedb();
        return inscrit;
    }

    public List getAllInscrit(){

        String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss, adresse, cp, ville, anneeSco1,
                libSco1, etabSco1, anneeSco2, libSco2, etabSco2;
        int id, form;

        List<Inscrit> Inscrits = new ArrayList<Inscrit>();

        opendb();

        Cursor cr = db.rawQuery("SELECT * FROM Inscrit;",null);
        if (cr.moveToFirst()) {
            do {
                id = cr.getInt(cr.getColumnIndex("ins_id"));
                nom = cr.getString(cr.getColumnIndex("insNom"));
                prenom = cr.getString(cr.getColumnIndex("insPrenom"));
                tel = cr.getString(cr.getColumnIndex("insTel"));
                mail = cr.getString(cr.getColumnIndex("insMail"));
                sexe = cr.getString(cr.getColumnIndex("insSexe"));
                datenaiss = cr.getString(cr.getColumnIndex("insDateNaiss"));
                lieunaiss = cr.getString(cr.getColumnIndex("insLieuNaiss"));
                adresse = cr.getString(cr.getColumnIndex("insAdresse"));
                cp = cr.getString(cr.getColumnIndex("insCP"));
                ville = cr.getString(cr.getColumnIndex("insVille"));
                anneeSco1 = cr.getString(cr.getColumnIndex("insAnneeSco1"));
                libSco1 = cr.getString(cr.getColumnIndex("insLibSco1"));
                etabSco1 = cr.getString(cr.getColumnIndex("insEtabSco1"));
                anneeSco2 = cr.getString(cr.getColumnIndex("insAnneeSco2"));
                libSco2 = cr.getString(cr.getColumnIndex("insLibSco2"));
                etabSco2 = cr.getString(cr.getColumnIndex("insEtabSco2"));
                form = cr.getInt(cr.getColumnIndex("insFormation"));

                Inscrit inscrit = new Inscrit(nom,prenom,tel,mail,sexe,datenaiss,lieunaiss,adresse,
                        cp,ville,anneeSco1,libSco1,etabSco1,anneeSco2,libSco2,etabSco2,form);
                inscrit.setId(id);
                Inscrits.add(inscrit);
            }
            while (cr.moveToNext());
            cr.close();
        }

        closedb();

        return Inscrits;

    }




}
