package com.example.cdsm.jpo;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import junit.framework.Test;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout layout;
    SQLiteDatabase db;
    Boolean Testdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //OnTouch changement de page vers le formulaire d'inscription
        layout = findViewById(R.id.layout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
                return false;

            }
        });

        // Création de la base de données à l'ouverture de l'application
        db = this.openOrCreateDatabase("db_JPO", MODE_PRIVATE, null);

        //Création de la table  Formation
        db.beginTransaction();
        try {
            //Table Formation
            String CreateTable =
                    "CREATE TABLE IF NOT EXISTS Formation("
                            + "  FormId INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "  FormNom TEXT,"
                            + "  FormPdf TEXT);";
            db.execSQL(CreateTable);
            db.setTransactionSuccessful();


        } catch (SQLException e) {
            System.out.print(e);
        } finally {
            db.endTransaction();
        }

        //Création de la table Fiche
        db.beginTransaction();
        try {
            //Table Fiche
//TODO Modifier la table en fonction des infos pour les check box (Emploi , Déscolarisé , Autre)
            String CreateTable =
                    "CREATE TABLE IF NOT EXISTS Fiche("
                            + "  FId INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "  FNom TEXT,"
                            + "  FPrenom TEXT,"
                            + "  FSexe TEXT,"
                            + "  FDateNaiss TEXT,"
                            + "  FLieuNaiss TEXT,"
                            + "  FNationalite TEXT,"
                            + "  FAdresse TEXT,"
                            + "  FCP INTEGER,"
                            + "  FVille TEXT,"
                            + "  FTelFixe INTEGER,"
                            + "  FTelPort INTEGER,"
                            + "  FMail TEXT,"
                            + "  FScolarite1 TEXT,"
                            + "  FScolarite2 TEXT,"
                            + "  FFormation1 TEXT,"
                            + "  FFormation2 TEXT,"
                            + "  FCommentaires TEXT,"
                            + "  FOrigineDemande TEXT);";
            db.execSQL(CreateTable);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            System.out.print(e);
        } finally {
            db.endTransaction();
        }

        //Importation des communes

        // Test si la table Commune existe
        try
        {
            long count = getProfilesCount();
            Testdb = true;
        }
        catch (Exception e)
        {
            Log.e("Erreur :",""+e);
            Testdb = false;
        }

        // Création ou non de la table en fonction du test
        if(!Testdb)
        {
            Log.i("Communes","Début importation");
            db = this.openOrCreateDatabase("db_Commune", MODE_PRIVATE, null);
            Scanner scan = new Scanner (getResources().openRawResource(R.raw.commune));
            String query = "";
            while(scan.hasNextLine())
            {
                query += scan.nextLine() + "\n";
                if(query.trim().endsWith(";"))
                {
                    db.execSQL(query);
                    query = "";
                }
            }
            Log.i("Communes","Fin importation");
        }

    }

    public long getProfilesCount() {
        SQLiteDatabase database = this.openOrCreateDatabase("db_Commune", MODE_PRIVATE, null);
        long count = DatabaseUtils.queryNumEntries(database, "commune");
        database.close();
        return count;
    }



}
