package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cdsm.jpo.Interface.DAO;

import java.util.ArrayList;
import java.util.List;

public class InscritDAO implements DAO {

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
    public void Ajouter(Inscrit inscrit) {

        String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss, adresse, cp, ville, sco1, sco2;
        int form1, form2;

        nom = inscrit.getNom();
        prenom = inscrit.getPrenom();
        tel = inscrit.getTel();
        mail = inscrit.getMail();
        sexe = inscrit.getSexe();
        datenaiss = inscrit.getDateNaiss();
        lieunaiss = inscrit.getLieuNaiss();
        adresse = inscrit.getAdresse();
        cp = inscrit.getCp();
        ville = inscrit.getVille();
        sco1 = inscrit.getScolarite1();
        sco2 = inscrit.getScolarite2();
        form1 = inscrit.getFormation1();
        form2 = inscrit.getFormation2();

        String query = "INSERT INTO Inscrit(insNom,insPrenom,insTel,insMail,insSexe,insDateNaiss" +
                ",insLieuNaiss,insAdresse,insCP,insVille,insScolarite1,insScolarite2,insFormation1,insFormation2)" +
                "VALUES ("+nom+","+prenom+","+tel+","+mail+","+sexe+","+datenaiss+","+lieunaiss+","+adresse+
                ","+cp+","+ville+","+sco1+","+sco2+","+form1+","+form2+");";

        String querytest = "INSERT INTO Inscrit(insNom,insPrenom) VALUES ('testn','testp')";

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
    public void Modifier(Inscrit inscrit) {

        String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss, adresse, cp, ville, sco1, sco2;
        int id, form1, form2;

        id = inscrit.getId();
        nom = inscrit.getNom();
        prenom = inscrit.getPrenom();
        tel = inscrit.getTel();
        mail = inscrit.getMail();
        sexe = inscrit.getSexe();
        datenaiss = inscrit.getDateNaiss();
        lieunaiss = inscrit.getLieuNaiss();
        adresse = inscrit.getAdresse();
        cp = inscrit.getCp();
        ville = inscrit.getVille();
        sco1 = inscrit.getScolarite1();
        sco2 = inscrit.getScolarite2();
        form1 = inscrit.getFormation1();
        form2 = inscrit.getFormation2();

        String query = "UPDATE Inscrit SET insNom = '"+nom+"', insPrenom = '"+prenom+"', insTel = '"+tel+"'" +
                ", insMail = '"+mail+"', insSexe = '"+sexe+"', insDatenaiss = '"+datenaiss+"', insLieuNaiss = '"+lieunaiss+"'" +
                ", insAdresse = '"+adresse+"', insCP = '"+cp+"', insVille = '"+ville+"', insScolarite1 = '"+sco1+"'" +
                ", insScolarite2 = '"+sco2+"', insFormation1 = '"+form1+"', insFormation2 ='"+form2+"' WHERE ins_id = "+id+";";

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
                inscrit.setScolarite1(cr.getString(cr.getColumnIndex("insScolarite1")));
                inscrit.setScolarite2(cr.getString(cr.getColumnIndex("insScolarite2")));
                inscrit.setFormation1(cr.getInt(cr.getColumnIndex("insFormation1")));
                inscrit.setFormation2(cr.getInt(cr.getColumnIndex("insFormation2")));
            }
            while (cr.moveToNext());
            cr.close();
        }

        closedb();

        return inscrit;
    }

    public List getAllInscrit(){

        String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss, adresse, cp, ville, sco1, sco2;
        int id, form1, form2;

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
                sco1 = cr.getString(cr.getColumnIndex("insScolarite1"));
                sco2 = cr.getString(cr.getColumnIndex("insScolarite2"));
                form1 = cr.getInt(cr.getColumnIndex("insFormation1"));
                form2 = cr.getInt(cr.getColumnIndex("insFormation2"));

                Inscrit inscrit = new Inscrit(nom,prenom,tel,mail,sexe,datenaiss,lieunaiss,adresse,
                        cp,ville,sco1,sco2,form1,form2);
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
