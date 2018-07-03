package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cdsm.jpo.Interface.DAO;

public class AdminDAO{

    private SQLiteDatabase db;
    private MaBaseSQLite maBaseSQLite;

    public AdminDAO(Context context){
        maBaseSQLite =  new MaBaseSQLite(context);
    }

    private void opendb(){
        db = maBaseSQLite.getWritableDatabase();
    }

    private void closedb(){
        db.close();
    }

    public void Ajouter(Admin admin){

        String login;
        String mdp;

        login = admin.getLogin();
        mdp = admin.getMdp();

        String query ="INSERT INTO Admin(adLogin, adMdp) VALUES ('"+login+"', '"+mdp+"');";

        opendb();

        db.execSQL(query);

        closedb();
    }

    public void EmptyTableAdmin(){
        opendb();

        db.execSQL("DELETE FROM Admin");

        closedb();
    }


}
