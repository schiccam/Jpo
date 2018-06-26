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
import java.util.ArrayList;
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

        MaBaseSQLite maBaseSQLite = new MaBaseSQLite(this);
        db = maBaseSQLite.getReadableDatabase();

        Afficher_commune();
    }

    public long getProfilesCount() {
        SQLiteDatabase database = this.openOrCreateDatabase("db_Commune", MODE_PRIVATE, null);
        long count = DatabaseUtils.queryNumEntries(database, "commune");
        database.close();
        return count;
    }

    public void Afficher_commune() {

        Cursor cr = db.rawQuery("SELECT * FROM commune LIMIT 10", null);
        if (cr.moveToFirst()) {
            do {
                String nom = cr.getString(cr.getColumnIndex("Nom_commune"));
                String cp = cr.getString(cr.getColumnIndex("Code_postal"));

                String raw = nom + " - " + cp;

            }
            while (cr.moveToNext());
            cr.close();
        }

    }



}
