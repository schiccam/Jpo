package com.example.cdsm.jpo.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cdsm.jpo.Classe.Formation;
import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.Hash;
import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.MaBaseSQLite;
import com.example.cdsm.jpo.R;

import java.util.List;

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

         /*TEST InscritDAO
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

        /*Test Table Formation
        FormationDAO f = new FormationDAO(this);
        List allform = f.getAllFormation();
        List allNv = f.getAllNiveauFormation();*/

        /*Test des tables Inscrit, Formation, NiveauFormation
        Inscrit inscrit = new Inscrit();
        inscrit.setNom("Chiccam");
        inscrit.setPrenom("Sylvain");
        inscrit.setTel("0658219313");
        inscrit.setMail("hotmail");
        inscrit.setFormation1(1);
        inscrit.setFormation2(2);

        InscritDAO inscritDAO = new InscritDAO(this);
        inscritDAO.Ajouter(inscrit);

        List all = inscritDAO.getAllInscrit();*/




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

        //Custom Dialog pour formulaire de connexion Admin
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        View myView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        final EditText etLogin = myView.findViewById(R.id.etLogin);
        final EditText etMdp = myView.findViewById(R.id.etMDP);
        Button valider = myView.findViewById(R.id.btnValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etLogin.getText().toString().isEmpty() && !etMdp.getText().toString().isEmpty()){

                    String login = etLogin.getText().toString();
                    String mdp = etMdp.getText().toString();

                    MaBaseSQLite maBaseSQLite = new MaBaseSQLite(MainActivity.this);
                    db = maBaseSQLite.getReadableDatabase();

                    Cursor cr = db.rawQuery("SELECT * FROM Admin WHERE adlogin = '"+login+"' ",null);
                    if (cr.moveToFirst()) {

                        String password = cr.getString(cr.getColumnIndex("adMdp"));
                        String mdpHash = Hash.md5(mdp);

                        if(mdpHash.equals(password))
                        {
                            Toast.makeText(MainActivity.this,"OK!!!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Login et/ou Mot de passe incorrect !",Toast.LENGTH_SHORT).show();
                        }

                        cr.close();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Login et/ou Mot de passe incorrect !",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Veuillez remplir tous les champs !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        myBuilder.setView(myView);
        AlertDialog dialog = myBuilder.create();
        dialog.show();

    }
}
