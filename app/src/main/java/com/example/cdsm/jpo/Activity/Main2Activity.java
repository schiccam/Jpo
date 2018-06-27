package com.example.cdsm.jpo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.R;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    EditText etNom;
    EditText etPrenom;
    EditText etTel;
    EditText etMail;
    Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etTel = findViewById(R.id.etTel);
        etMail = findViewById(R.id.etMail);
        btnValider = findViewById(R.id.btnValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkDataEntered() == 4){
                    Toast.makeText(Main2Activity.this,"OK",Toast.LENGTH_SHORT).show();
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

        return cptValide;
    }
}
