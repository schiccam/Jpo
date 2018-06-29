package com.example.cdsm.jpo.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cdsm.jpo.Classe.Formation;
import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.MyNumberPicker;
import com.example.cdsm.jpo.Classe.NiveauFormation;
import com.example.cdsm.jpo.R;

import java.util.Calendar;
import java.util.List;

public class UpdateInscritForm_Activity extends AppCompatActivity {

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
    Spinner spNvFormation;
    Spinner spLibFormation;
    Button btnValider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinscrit);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etTel = findViewById(R.id.etTel);
        etMail = findViewById(R.id.etMail);

        spSexe = findViewById(R.id.spSexe);
        //Adapter avec Custom spinner pour modifier la taille du texte
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sexe_array, R.layout.custom_spinner);
        spSexe.setAdapter(adapter);

        //Datepicker
        etDateNaiss = findViewById(R.id.etDateNaiss);
        etDateNaiss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateInscritForm_Activity.this, AlertDialog.THEME_HOLO_LIGHT,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    etDateNaiss.setText(day + "/" + (month + 1) + "/" + year);
                                }
                            }, 2000, 5, 15);
                    datePickerDialog.show();
                }
            }
        });

        etLieuNaiss = findViewById(R.id.etLieuNaiss);
        etAdresse = findViewById(R.id.etAdresse);
        etVille = findViewById(R.id.etVille);
        etCp = findViewById(R.id.etCp);

        //Custom Dialog pour le choix de l'année
        etAnneeSco1 = findViewById(R.id.etAnneeSco1);
        etAnneeSco1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ChooseYear(etAnneeSco1);
                }
            }
        });

        etLibSco1 = findViewById(R.id.etLibSco1);
        etEtabSco1 = findViewById(R.id.etEtabSco1);

        etAnneeSco2 = findViewById(R.id.etAnneeSco2);
        etAnneeSco2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ChooseYear(etAnneeSco2);
                }
            }
        });

        etLibSco2 = findViewById(R.id.etLibSco2);
        etEtabSco2 = findViewById(R.id.etEtabSco2);

        spNvFormation = findViewById(R.id.spNvFormation);
        //Adapter avec Custom spinner pour modifier la taille du texte + données de la BDD
        spNvFormation.setAdapter(getNvFormations());

        spNvFormation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spLibFormation = findViewById(R.id.spLibFormation);
                //Rendre visible le spinner
                spLibFormation.setVisibility(View.VISIBLE);

                NiveauFormation nv = (NiveauFormation) parent.getSelectedItem();

                //Adapter avec Custom spinner pour modifier la taille du texte + données de la BDD
                spLibFormation.setAdapter(getLibFormations(nv));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnValider = findViewById(R.id.btnValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValiderIsClicked();
            }
        });

        FillForTest();
    }

    private void ValiderIsClicked() {
        if (checkDataEntered() == 12){

            String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss,
                    adresse, ville, cp, sco1, sco2, anneesco1, libsco1, etabsco1,
                    anneesco2, libsco2, etabsco2;

            nom = etNom.getText().toString();
            prenom = etPrenom.getText().toString();
            tel = etTel.getText().toString();
            mail = etMail.getText().toString();
            sexe = spSexe.getSelectedItem().toString();
            datenaiss = etDateNaiss.getText().toString();
            lieunaiss = etLieuNaiss.getText().toString();
            adresse = etAdresse.getText().toString();
            ville = etVille.getText().toString();
            cp = etCp.getText().toString();
            anneesco1 = etAnneeSco1.getText().toString();
            libsco1 = etLibSco1.getText().toString();
            etabsco1 = etEtabSco1.getText().toString();
            sco1 = anneesco1+" "+libsco1+" "+etabsco1;
            anneesco2 = etAnneeSco2.getText().toString();
            libsco2 = etLibSco2.getText().toString();
            etabsco2 = etEtabSco2.getText().toString();
            sco2 = anneesco2+" "+libsco2+" "+etabsco2;

            Formation form = (Formation) spLibFormation.getSelectedItem();

            Inscrit inscrit = new Inscrit(nom, prenom, tel, mail, sexe, datenaiss, lieunaiss,
                    adresse, cp, ville, sco1, sco2, form.getId());

            InscritDAO inscritDAO = new InscritDAO(UpdateInscritForm_Activity.this);
            inscritDAO.Ajouter(inscrit);

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void FillForTest() {
        etNom.setText("Chiccam");
        etPrenom.setText("Sylvain");
        etTel.setText("0658219313");
        etMail.setText("schiccam@hotmail.fr");
        etDateNaiss.setText("27/4/1994");
        etLieuNaiss.setText("Sevres");
        etAdresse.setText("100 allée du hetre pourpre");
        etVille.setText("Dammarie-les-lys");
        etCp.setText("77190");
        etAnneeSco1.setText("2012");
        etLibSco1.setText("BAC PRO EDPI");
        etEtabSco1.setText("Lycée Joliot Curie Dammarie-les-lys");
    }

    private boolean isEmpty(EditText et){
        CharSequence str = et.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean isEmailValide(EditText et){
        CharSequence email = et.getText().toString();
        return (!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private int checkDataEntered(){
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

        if (isEmailValide(etMail) == false){
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

    private ArrayAdapter getNvFormations(){
        FormationDAO formationDAO = new FormationDAO(this);
        List Formations = formationDAO.getAllNiveauFormation();
        ArrayAdapter<NiveauFormation> adapter = new ArrayAdapter<NiveauFormation>(this, R.layout.custom_spinner, Formations);
        return adapter;
    }

    private ArrayAdapter getLibFormations(NiveauFormation nv){
        FormationDAO formationDAO = new FormationDAO(this);
        List allFormations = formationDAO.getFormationsByNvFormation(nv.getId());
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, allFormations);
        return adapter;
    }

    public void ChooseYear(final EditText editText) {

        //Custom Dialog pour formulaire de connexion Admin
        android.support.v7.app.AlertDialog.Builder myBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        View myView = getLayoutInflater().inflate(R.layout.dialog_year, null);
        final MyNumberPicker npYear = myView.findViewById(R.id.npYear);
        Button valider = myView.findViewById(R.id.btnValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editText.setText(Integer.toString(npYear.getValue()));
            }
        });

        myBuilder.setView(myView);
        android.support.v7.app.AlertDialog dialog = myBuilder.create();
        dialog.show();

    }
}
