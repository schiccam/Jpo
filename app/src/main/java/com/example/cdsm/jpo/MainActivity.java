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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import junit.framework.Test;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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

        /* TEST InscritDAO
        Inscrit i = new Inscrit();

        InscritDAO ide = new InscritDAO(this);
        ide.Ajouter(i);
        ide.Ajouter(i);
        ide.Ajouter(i);
        ide.Ajouter(i);


        MaBaseSQLite maBaseSQLite = new MaBaseSQLite(this);
        db = maBaseSQLite.getReadableDatabase();

        String rawQuery = "INSERT INTO Inscrit(insNom, insPrenom, insTel, insMail) VALUES ('Chiccam','Sylvain','0658219313','schiccam@hotmail.fr')";
        db.execSQL(rawQuery);

        Inscrit iu = new Inscrit();
        iu.setId(95);
        iu.setNom("Chiccamtest");
        iu.setPrenom("Sylvaintest");
        iu.setTel("9999999999");
        iu.setMail("TEst");

        ide.Modifier(iu);

        Cursor cr = db.rawQuery("SELECT * FROM Inscrit",null);

        if (cr.moveToFirst()) {
            do {
                int id = cr.getInt(cr.getColumnIndex("ins_id"));
                String nom = cr.getString(cr.getColumnIndex("insNom"));
                String prenom = cr.getString(cr.getColumnIndex("insPrenom"));
                String tel = cr.getString(cr.getColumnIndex("insTel"));
                String mail = cr.getString(cr.getColumnIndex("insMail"));

                String raw = id + " " + nom + " " + prenom + " " + tel + " " + mail;

                Log.e("test BDD :",raw);

            }
            while (cr.moveToNext());
            cr.close();
        }

        Inscrit testfd = ide.getInscrit(95);

        List<Inscrit> list = ide.getAllInscrit();*/




        /*TEST Table Admin Locale
        MaBaseSQLite maBaseSQLite = new MaBaseSQLite(this);
        db = maBaseSQLite.getReadableDatabase();

        Cursor cr = db.rawQuery("SELECT * FROM Admin",null);

        if (cr.moveToFirst()) {
            do {
                int id = cr.getInt(cr.getColumnIndex("ad_id"));
                String login = cr.getString(cr.getColumnIndex("adLogin"));
                String mdp = cr.getString(cr.getColumnIndex("adMdp"));

                String raw = id + " " + login + " " + mdp;

                Log.e("test MDP :", raw);

            }
            while (cr.moveToNext());
            cr.close();
        }*/

    }

    public void admin_Click(View view) {

    }
}
