package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cdsm.jpo.Interface.DAO;

public class FormationDAO implements DAO {

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
    public void Ajouter(Inscrit inscrit) {

    }

    @Override
    public void Supprimer(int id) {

    }

    @Override
    public void Modifier(Inscrit inscrit) {

    }
}
