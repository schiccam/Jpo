package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String DB_NAME = "jpo.db";
    private static final int DB_VERSION = 1;
    private Context context;

    private String createInscrit ="CREATE TABLE IF NOT EXISTS Inscrit("
        +"ins_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        +"insNom TEXT,"
        +"insPrenom TEXT,"
        +"insTel TEXT,"
        +"insMail TEXT,"
        +"insSexe TEXT,"
        +"insDateNaiss TEXT,"
        +"insLieuNaiss TEXT,"
        +"insAdresse TEXT,"
        +"insCP TEXT,"
        +"insVille TEXT,"
        +"insScolarite1 TEXT,"
        +"insScolarite2 TEXT,"
        +"insFormation1 TEXT,"
        +"insFormation2 TEXT);";

    private String createAdmin = "CREATE TABLE IF NOT EXISTS Admin(" +
            "ad_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "adLogin TEXT," +
            "adMdp TEXT);";


    public MaBaseSQLite(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createInscrit);
        db.execSQL(createAdmin);
        String mdp = Hash.md5("azerty");
        String mdp2 = Hash.md5("azertyuiop");
        db.execSQL("INSERT INTO Admin VALUES (1,'admin','"+mdp+"')");
        db.execSQL("INSERT INTO Admin VALUES (2,'admin2','"+mdp2+"')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Inscrit;");
        db.execSQL(createInscrit);
    }

}
