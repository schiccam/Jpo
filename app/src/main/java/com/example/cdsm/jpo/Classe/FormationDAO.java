package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cdsm.jpo.Interface.DAO;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class FormationDAO implements DAO<Formation> {

    private SQLiteDatabase db;
    private MaBaseSQLite maBaseSQLite;

    public FormationDAO(Context context){
        maBaseSQLite =  new MaBaseSQLite(context);
    }

    private void opendb(){
        db = maBaseSQLite.getWritableDatabase();
    }

    private void closedb(){
        db.close();
    }


    @Override
    public void Ajouter(Formation item) {

        String lib;
        int nvFormID;

        lib = item.getLib();
        nvFormID = item.getNvformationid();

        String query = "INSERT INTO Formation(formLib, formNiveau) VALUES ('"+lib+"',"+nvFormID+");";

        opendb();

        db.execSQL(query);

        closedb();
    }

    @Override
    public void Supprimer(int id) {
    }

    @Override
    public void Modifier(Formation item) {
    }

    public Formation getFormation(int id){

        Formation formation = new Formation();

        opendb();

        Cursor cr = db.rawQuery("SELECT * FROM Formation WHERE form_id = "+id+";",null);
        if (cr.moveToFirst()){
            formation.setId(cr.getInt(cr.getColumnIndex("form_id")));
            formation.setLib(cr.getString(cr.getColumnIndex("formLib")));
            formation.setNvformationid(cr.getInt(cr.getColumnIndex("formNiveau")));
        }
        cr.close();

        closedb();

        return formation;
    }

    public List getFormationsByNvFormation(int nvformid){

        String lib;
        int id, nvFormID;

        List<Formation> formations = new ArrayList<>();

        opendb();

        Cursor cr = db.rawQuery("SELECT * FROM Formation WHERE formNiveau ="+nvformid+" ;",null);
        if(cr.moveToFirst()){
            do {
                id = cr.getInt(cr.getColumnIndex("form_id"));
                lib = cr.getString(cr.getColumnIndex("formLib"));
                nvFormID = cr.getInt(cr.getColumnIndex("formNiveau"));

                Formation formation = new Formation(lib, nvFormID);
                formation.setId(id);
                formations.add(formation);
            }
            while (cr.moveToNext());
            cr.close();
        }

        closedb();

        return formations;
    }

    public List getAllFormation(){

        String lib;
        int id, nvFormID;

        List<Formation> formations = new ArrayList<Formation>();

        opendb();

        Cursor cr = db.rawQuery("SELECT * FROM Formation;",null);
        if(cr.moveToFirst()){
            do {
                id = cr.getInt(cr.getColumnIndex("form_id"));
                lib = cr.getString(cr.getColumnIndex("formLib"));
                nvFormID = cr.getInt(cr.getColumnIndex("formNiveau"));

                Formation formation = new Formation(lib, nvFormID);
                formation.setId(id);
                formations.add(formation);
            }
            while (cr.moveToNext());
            cr.close();
        }

        closedb();

        return formations;
    }

    public List getAllNiveauFormation(){

        List<NiveauFormation> niveauFormations = new ArrayList<NiveauFormation>();

        opendb();

        Cursor cr = db.rawQuery("SELECT * FROM NiveauFormation;",null);
        if(cr.moveToFirst()){
            do {
                int id = cr.getInt(cr.getColumnIndex("nvform_id"));
                String lib = cr.getString(cr.getColumnIndex("nvformLib"));

                NiveauFormation niveauFormation = new NiveauFormation(id, lib);
                niveauFormations.add(niveauFormation);
            }
            while (cr.moveToNext());
            cr.close();
        }

        closedb();

        return niveauFormations;
    }

    public List getStat(){
        List<Stat> stats = new ArrayList<>();

        opendb();

        Cursor cr = db.rawQuery("SELECT formLib, COUNT(*) AS count FROM Formation, Inscrit" +
                " WHERE Inscrit.insFormation = Formation.form_id " +
                " GROUP BY formLib;",null);

        if(cr.moveToFirst()){
            do {
                String lib = cr.getString(cr.getColumnIndex("formLib"));
                int cpt = cr.getInt(cr.getColumnIndex("count"));
                stats.add(new Stat(lib, cpt));
            }
            while (cr.moveToNext());
            cr.close();
        }

        closedb();
        return stats;
    }
}
