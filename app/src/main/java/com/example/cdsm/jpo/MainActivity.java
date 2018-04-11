package com.example.cdsm.jpo;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout layout;
    SQLiteDatabase db;

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
        db = this.openOrCreateDatabase("JPO", MODE_PRIVATE, null);
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
            Log.e("Table 1","ok");

            //Table Fiche
            //TODO Modifier la table en fonction des infos pour les check box
            CreateTable =
                    "CREATE TABLE IF NOT EXISTS Fiche("
                            + "  FId INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "  FNom TEXT,"
                            + "  FPrenom TEXT,"
                            + "  FSexe TEXT,"
                            + "  FDateNaiss TEXT,"
                            + "  FLieuNaiss TEXT,"
                            + "  FNationalité TEXT,"
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
            Log.e("Table 2","ok");

        } catch (SQLException e) {
            System.out.print(e);
        } finally {
            db.endTransaction();
        }

    }

}
