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
        +"insAnneeSco1 TEXT,"
            +"insLibSco1 TEXT,"
            +"insEtabSco1 TEXT,"
            +"insAnneeSco2 TEXT,"
            +"insLibSco2 TEXT,"
            +"insEtabSco2 TEXT,"
        +"insFormation INTEGER,"
        +"FOREIGN KEY (insFormation) REFERENCES Formation(form_id));";

    private String createFormation ="CREATE TABLE IF NOT EXISTS Formation(" +
            "form_id INTEGER PRIMARY KEY," +
            "formLib TEXT," +
            "formNiveau INTEGER,"+
            "FOREIGN KEY (formNiveau) REFERENCES Formation(nvform_id));";

    private String createAdmin = "CREATE TABLE IF NOT EXISTS Admin(" +
            "ad_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "adLogin TEXT," +
            "adMdp TEXT);";

    private String createNiveauFormation = "CREATE TABLE IF NOT EXISTS NiveauFormation(" +
            "nvform_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nvformLib TEXT);";


    public MaBaseSQLite(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Création de toutes les tables
        db.execSQL(createNiveauFormation);
        db.execSQL(createFormation);
        db.execSQL(createInscrit);
        db.execSQL(createAdmin);

        //Insertion de données dans la tables NiveauFormation
        db.execSQL("INSERT INTO NiveauFormation VALUES (1,'BAC')");
        db.execSQL("INSERT INTO NiveauFormation VALUES (2,'BAC+2')");
        db.execSQL("INSERT INTO NiveauFormation VALUES (3,'BAC+3')");
        db.execSQL("INSERT INTO NiveauFormation VALUES (4,'BAC+4')");
        db.execSQL("INSERT INTO NiveauFormation VALUES (5,'BAC+5')");

        /*Insertion de données dans la tables Formation
        db.execSQL("INSERT INTO Formation VALUES (1,'BTS1',2)");
        db.execSQL("INSERT INTO Formation VALUES (2,'BTS2',2)");
        db.execSQL("INSERT INTO Formation VALUES (3,'BTS3',2)");
        db.execSQL("INSERT INTO Formation VALUES (4,'BAC1',1)");
        db.execSQL("INSERT INTO Formation VALUES (5,'BAC2',1)");
        db.execSQL("INSERT INTO Formation VALUES (6,'BAC3',1)");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Inscrit;");
        db.execSQL("DROP TABLE Formation;");
        db.execSQL("DROP TABLE NiveauFormation;");
        db.execSQL("DROP TABLE Admin;");
        db.execSQL(createInscrit);
        db.execSQL(createFormation);
        db.execSQL(createNiveauFormation);
        db.execSQL(createAdmin);
    }

}
