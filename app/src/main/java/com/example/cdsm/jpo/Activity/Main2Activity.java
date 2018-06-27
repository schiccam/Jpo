package com.example.cdsm.jpo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.NiveauFormation;
import com.example.cdsm.jpo.R;

import org.w3c.dom.Text;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    EditText etNom;
    EditText etPrenom;
    EditText etTel;
    EditText etMail;
    Spinner spSexe;
    EditText etDateNaiss;
    EditText etLieuNaiss;
    EditText etAdresse;
    EditText etVille;
    EditText etCp;
    EditText etAnneeSco1;
    EditText etLibSco1;
    EditText etEtabSco1;
    EditText etAnneeSco2;
    EditText etLibSco2;
    EditText etEtabSco2;
    Spinner spNvFormation1;
    Spinner spLibFormation1;
    Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etTel = findViewById(R.id.etTel);
        etMail = findViewById(R.id.etMail);

        spSexe = findViewById(R.id.spSexe);
        //Adapter avec Custom spinner pour modifier la taille du texte
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sexe_array, R.layout.custom_spinner);
        spSexe.setAdapter(adapter);

        etDateNaiss = findViewById(R.id.etDateNaiss);
        etLieuNaiss = findViewById(R.id.etLieuNaiss);
        etAdresse = findViewById(R.id.etAdresse);
        etVille = findViewById(R.id.etVille);
        etCp = findViewById(R.id.etCp);
        etAnneeSco1 = findViewById(R.id.etAnneeSco1);
        etLibSco1 = findViewById(R.id.etLibSco1);
        etEtabSco1 = findViewById(R.id.etEtabSco1);
        etAnneeSco2 = findViewById(R.id.etAnneeSco2);
        etLibSco2 = findViewById(R.id.etLibSco2);
        etEtabSco2 = findViewById(R.id.etEtabSco2);

        spNvFormation1 = findViewById(R.id.spNvFormation1);
        //Adapter avec Custom spinner pour modifier la taille du texte + données de la BDD
        spNvFormation1.setAdapter(getNvFormations());

        spLibFormation1 = findViewById(R.id.spLibFormation1);
        //Adapter avec Custom spinner pour modifier la taille du texte + données de la BDD
        spLibFormation1.setAdapter(getLibFormations());


        btnValider = findViewById(R.id.btnValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkDataEntered() == 4){
                    Toast.makeText(Main2Activity.this,"OK",Toast.LENGTH_SHORT).show();
                    Log.w("spinner : ",spSexe.getSelectedItem().toString());
                }
                /*String nom = etNom.getText().toString();
                String prenom = etPrenom.getText().toString();
                String tel = etTel.getText().toString();
                String mail = etMail.getText().toString();

                Inscrit inscrit = new Inscrit();
                inscrit.setNom(nom);
                inscrit.setPrenom(prenom);
                inscrit.setTel(tel);
                inscrit.setMail(mail);

                InscritDAO inscritDAO = new InscritDAO(Main2Activity.this);
                inscritDAO.Ajouter(inscrit);*/



            }
        });

    }

    public boolean isEmpty(EditText et){
        CharSequence str = et.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean isEmail(EditText et){
        CharSequence email = et.getText().toString();
        return (!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public int checkDataEntered(){
        int cptValide = 0;

        if (isEmpty(etNom)){
            etNom.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etPrenom)){
            etPrenom.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etTel)){
            etTel.setError("");
        }
        else
            cptValide++;

        if (isEmail(etMail) == false){
            etMail.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etDateNaiss)){
            etDateNaiss.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etLieuNaiss)){
            etLieuNaiss.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etAdresse)){
            etAdresse.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etVille)){
            etVille.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etCp)){
            etCp.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etAnneeSco1)){
            etCp.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etLibSco1)){
            etCp.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etEtabSco1)){
            etCp.setError("");
        }
        else
            cptValide++;



        return cptValide;
    }

    public ArrayAdapter getNvFormations(){
        FormationDAO formationDAO = new FormationDAO(this);
        List Formations = formationDAO.getAllNiveauFormation();
        ArrayAdapter<NiveauFormation> adapter = new ArrayAdapter<NiveauFormation>(this, R.layout.custom_spinner, Formations);
        return adapter;
    }

    public ArrayAdapter getLibFormations(){
        FormationDAO formationDAO = new FormationDAO(this);
        List allFormations = formationDAO.getAllFormation();
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, allFormations);
        return adapter;
    }
}
