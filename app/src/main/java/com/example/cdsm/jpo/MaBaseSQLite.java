package com.example.cdsm.jpo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Scanner;

public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String DB_NAME = "jpo.db";
    private static final int DB_VERSION = 1;
    private Context context;

    private String createInscrit ="CREATE TABLE IF NOT EXISTS Inscrit("
        +"ins_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        +"insNom TEXT,"
        +"insPrenom TEXT,"
        +"insTel TEXT,"
        +"insMail TEXT);";


    public MaBaseSQLite(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createInscrit);
        db.execSQL(commune());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Inscrit;");
        db.execSQL(createInscrit);
    }

    private String commune(){

        Scanner scan = new Scanner (context.getResources().openRawResource(R.raw.commune));
        String query = "";
        while(scan.hasNextLine())
        {
            query += scan.nextLine() + "\n";
        }
        return query;
    }
}