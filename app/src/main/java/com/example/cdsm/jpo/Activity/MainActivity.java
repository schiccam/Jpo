package com.example.cdsm.jpo.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.Hash;
import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.MaBaseSQLite;
import com.example.cdsm.jpo.Classe.Stat;
import com.example.cdsm.jpo.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout layout;
    SQLiteDatabase db;
    Boolean Testdb;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        List stats = new FormationDAO(this).getStat();
        List inscrits = new InscritDAO(this).getAllInscrit();

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

        myBuilder.setView(myView);
        dialog = myBuilder.create();
        dialog.show();
    }

    private void CloseLoginDialog(){
        dialog.dismiss();
    }
}
