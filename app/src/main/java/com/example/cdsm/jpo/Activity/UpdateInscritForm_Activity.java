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

    Intent intent;

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

        intent = getIntent();

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

        FillForUpdate();
    }

    private void ValiderIsClicked() {
        if (checkDataEntered() == 12){

            String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss,
                    adresse, ville, cp, anneesco1, libsco1, etabsco1,
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
            anneesco2 = etAnneeSco2.getText().toString();
            libsco2 = etLibSco2.getText().toString();
            etabsco2 = etEtabSco2.getText().toString();

            Formation form = (Formation) spLibFormation.getSelectedItem();

            Inscrit inscrit = new Inscrit(nom, prenom, tel, mail, sexe, datenaiss, lieunaiss,
                    adresse, cp, ville, anneesco1, libsco1, etabsco1 , anneesco2, libsco2, etabsco2, form.getId());
            inscrit.setId(intent.getIntExtra("inscritID",0));

            InscritDAO inscritDAO = new InscritDAO(UpdateInscritForm_Activity.this);
            inscritDAO.Modifier(inscrit);

            Intent intent = new Intent(this,AdminActivity.class);
            startActivity(intent);

        }
    }

    private void FillForUpdate() {

        int id = intent.getIntExtra("inscritID",0);
        Inscrit inscrit = new InscritDAO(this).getInscrit(id);

        etNom.setText(inscrit.getNom());
        etPrenom.setText(inscrit.getPrenom());
        etTel.setText(inscrit.getTel());
        etMail.setText(inscrit.getMail());
        etDateNaiss.setText(inscrit.getDateNaiss());
        etLieuNaiss.setText(inscrit.getLieuNaiss());
        etAdresse.setText(inscrit.getAdresse());
        etVille.setText(inscrit.getVille());
        etCp.setText(inscrit.getCp());
        etAnneeSco1.setText(inscrit.getAnneeSco1());
        String a = inscrit.getLibSco1();
        etLibSco1.setText(inscrit.getLibSco1());
        String b = inscrit.getEtabSco1();
        etEtabSco1.setText(inscrit.getEtabSco1());
        etAnneeSco2.setText(inscrit.getAnneeSco2());
        etLibSco2.setText(inscrit.getLibSco2());
        etEtabSco2.setText(inscrit.getEtabSco2());

        FormationDAO formationDAO = new FormationDAO(this);
        int nvForm = formationDAO.getFormation(inscrit.getFormation()).getNvformationid();
        spNvFormation.setSelection(getIndexNvForm(spNvFormation,nvForm));


        spLibFormation = findViewById(R.id.spLibFormation);
        spLibFormation.setVisibility(View.VISIBLE);

        NiveauFormation nv =(NiveauFormation)spNvFormation.getSelectedItem();

        //Adapter avec Custom spinner pour modifier la taille du texte + données de la BDD
        spLibFormation.setAdapter(getLibFormations(nv));

        int form = formationDAO.getFormation(inscrit.getFormation()).getId();
        int spinnerposition = getIndexLibForm(spLibFormation,form);
        //TODO régler le bug spinner n'affiche pas le lib correct
        spLibFormation.setSelection(spinnerposition);
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

    private int getIndexNvForm(Spinner spinner, int id){
        for (int i=0;i<spinner.getCount();i++){
            NiveauFormation nv = (NiveauFormation)spinner.getItemAtPosition(i);
            if (nv.getId() == id){
                return i;
            }
        }
        return 0;
    }

    private int getIndexLibForm(Spinner spinner, int id){
        for (int i=0;i<spinner.getCount();i++){
            Formation form = (Formation)spinner.getItemAtPosition(i);
            if (form.getId() == id){
                return i;
            }
        }
        return 0;
    }
}
