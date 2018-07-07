package com.example.cdsm.jpo.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.cdsm.jpo.Classe.Stat;
import com.example.cdsm.jpo.Classe.WebService;
import com.example.cdsm.jpo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    ConstraintLayout layout;
    SQLiteDatabase db;
    Boolean Testdb;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Contrôle si premier lancement ou non
        pref = getSharedPreferences("Jpo", MODE_PRIVATE);
        // si oui récupération des fomrations et des admins depuis le webservice
        if(pref.getBoolean("firstrun", true)){
            if (CheckConnection()){
                pref.edit().putBoolean("firstrun", false).commit();
            }
            else
            {
                //Affiche un dialog pour obliger l'utilisateur à activer le wifi
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Le premier lancement de l'application nécessite\n" +
                        " une connexion internet")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        // Au lancement de l'application chargement des données depuis le webservice
        GetFromWebservice();

        //OnTouch changement de page vers le formulaire d'inscription
        layout = findViewById(R.id.layout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Intent i = new Intent(MainActivity.this,AddInscritForm_Activity.class);
                startActivity(i);
                return false;

            }
        });
    }

    private void GetFromWebservice() {
        WebService ws = new WebService(this);
        ws.GETAdmin();
        ws.GETFormation();
    }

    private boolean CheckConnection(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
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
                //Vérification pour connexion admin
                if(!etLogin.getText().toString().isEmpty() && !etMdp.getText().toString().isEmpty()){

                    String login = etLogin.getText().toString();
                    String mdp = etMdp.getText().toString();

                    MaBaseSQLite maBaseSQLite = new MaBaseSQLite(MainActivity.this);
                    db = maBaseSQLite.getReadableDatabase();

                    //Récupération de la ligne correspondant au login
                    Cursor cr = db.rawQuery("SELECT * FROM Admin WHERE adlogin = '"+login+"' ",null);
                    //si login existe vérification du mot de passe
                    if (cr.moveToFirst()) {

                        String password = cr.getString(cr.getColumnIndex("adMdp"));
                        String mdpHash = Hash.md5(mdp);
                        //si mdp de l'admin = mdp dans la base de données Intent vers AdminActivity
                        if(mdpHash.equals(password))
                        {
                            //fermeture du dialog
                            CloseLoginDialog();
                            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                            startActivity(intent);
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

        // Affichage du dialog dnas l'UI
        myBuilder.setView(myView);
        dialog = myBuilder.create();
        dialog.show();
    }

    //fermeture du dialog
    private void CloseLoginDialog(){
        dialog.dismiss();
    }

    // Intent vers TestRESTActivity
    public void REST_Clicked(View view) {
        startActivity(new Intent(this, TestRESTActivity.class));
    }
}
