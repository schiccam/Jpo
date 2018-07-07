package com.example.cdsm.jpo.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cdsm.jpo.Classe.Formation;
import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.MyNumberPicker;
import com.example.cdsm.jpo.Classe.NiveauFormation;
import com.example.cdsm.jpo.Classe.WebService;
import com.example.cdsm.jpo.R;

import java.util.Calendar;
import java.util.List;

public class AddInscritForm_Activity extends AppCompatActivity {

    //Déclaration des variables
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

    android.support.v7.app.AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinscrit);

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
                //Si edittext focus
                if(hasFocus){
                    // Affichage du datepicker
                    ShowDatePicker();
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

                TextView tvTitre = findViewById(R.id.tvTitre);

                tvTitre.setFocusableInTouchMode(true);

                tvTitre.requestFocusFromTouch();

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

        //à mettre en commentaire pour la présentation
        FillForTest();
    }

    private void ShowDatePicker() {
        int y, m, d;
        // si edittext est vide date par defaut
        if((etDateNaiss.getText().toString()).matches("")){
            y = 2000;
            m = 5;
            d = 15;
        }
        // sinon date defaut = valeur edittext
        else{
            String date = etDateNaiss.getText().toString();
            String[] parts = date.split("/");
            d = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]) - 1;
            y = Integer.parseInt(parts[2]);
        }
        // Cacher le clavier
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        // Cration du DatePicker
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddInscritForm_Activity.this, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = String.format("%02d/%02d/%d", day,(month + 1),year);
                        etDateNaiss.setText(date);
                    }
                }, y, m, d);
        //Affichage du DatePicker dans l'UI
        datePickerDialog.show();
    }

    private void ValiderIsClicked() {
        //Si les 12 champs obligatoires ne sont pas vide
        if (checkDataEntered() == 12){

            String nom, prenom, tel, mail, sexe, datenaiss, lieunaiss,
                    adresse, ville, cp, anneesco1, libsco1, etabsco1,
                    anneesco2, libsco2, etabsco2;

            //Récuperation des données de tous les Widget
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

            //Insertion des données dans un objet Inscrit
            Inscrit inscrit = new Inscrit(nom, prenom, tel, mail, sexe, datenaiss, lieunaiss,
                    adresse, cp, ville, anneesco1,libsco1,etabsco1,anneesco2,libsco2,etabsco2, form.getId());

            InscritDAO inscritDAO = new InscritDAO(AddInscritForm_Activity.this);
            //Insertion de l'inscrit dans la Table Inscrit
            inscritDAO.Ajouter(inscrit);

            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            //TODO envoie de mail
            new WebService(this).POSTMail(mail,form.getId());

        }
    }

    // remplissage automatique du formulaire pour les tests
    private void FillForTest() {
        etNom.setText("Chiccam");
        etPrenom.setText("Sylvain");
        etTel.setText("0658219313");
        etMail.setText("sylvainchiccam@gmail.com");
        etDateNaiss.setText("27/04/1994");
        etLieuNaiss.setText("Sevres");
        etAdresse.setText("100 allée du hetre pourpre");
        etVille.setText("Dammarie-les-lys");
        etCp.setText("77190");
        etAnneeSco1.setText("2012");
        etLibSco1.setText("BAC PRO EDPI");
        etEtabSco1.setText("Lycée Joliot Curie Dammarie-les-lys");
    }

    // vérification si Edittext est vide
    private boolean isEmpty(EditText et){
        CharSequence str = et.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //vérification si email est valide
    private boolean isEmailValide(EditText et){
        CharSequence email = et.getText().toString();
        return (!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //vérification de tous les élément obligatoire du layout
    private int checkDataEntered(){
        int cptValide = 0;
        String vide = "Champ Ogligatoire";

        if (isEmpty(etNom)){
            etNom.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etPrenom)){
            etPrenom.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etTel)){
            etTel.setError(vide);
        }
        else
            cptValide++;

        if (isEmailValide(etMail) == false){
            etMail.setError("");
        }
        else
            cptValide++;

        if (isEmpty(etDateNaiss)){
            etDateNaiss.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etLieuNaiss)){
            etLieuNaiss.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etAdresse)){
            etAdresse.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etVille)){
            etVille.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etCp)){
            etCp.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etAnneeSco1)){
            etCp.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etLibSco1)){
            etCp.setError(vide);
        }
        else
            cptValide++;

        if (isEmpty(etEtabSco1)){
            etCp.setError(vide);
        }
        else
            cptValide++;

        return cptValide;
    }

    //récuperation de tous les objets NiveauFormation
    private ArrayAdapter getNvFormations(){
        FormationDAO formationDAO = new FormationDAO(this);
        //récuperation de tous les objets NiveauFormation dans une list
        List Formations = formationDAO.getAllNiveauFormation();
        //Création de l'adapter pour le spinner avec son XML et ses données
        ArrayAdapter<NiveauFormation> adapter = new ArrayAdapter<NiveauFormation>(this, R.layout.custom_spinner, Formations){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv =(TextView) view;
                // Changement de couleur en fonction de la position
                if(position%2 == 1) {
                    tv.setBackgroundColor(Color.LTGRAY);
                }
                else {
                    tv.setBackgroundColor(Color.WHITE);
                }
                return view;
            }
        };
        return adapter;
    }

    //récuperation de toutes les formations en fonction du Niveau de la fomations
    private ArrayAdapter getLibFormations(NiveauFormation nv){
        FormationDAO formationDAO = new FormationDAO(this);
        //récuperation de tous les objets Formation dans une list en fonction du niveau choisi
        List allFormations = formationDAO.getFormationsByNvFormation(nv.getId());
        //Création de l'adapter pour le spinner avec son XML et ses données
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_spinner, allFormations){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv =(TextView) view;
                // Changement de couleur en fonction de la position
                if(position%2 == 1) {
                    tv.setBackgroundColor(Color.LTGRAY);
                }
                else {
                    tv.setBackgroundColor(Color.WHITE);
                }
                return view;
            }
        };
        return adapter;
    }

    //création du custom dialog pour les edittext année scolaire
    public void ChooseYear(final EditText editText) {

        //Custom Dialog pour formulaire de connexion Admin
        final android.support.v7.app.AlertDialog.Builder myBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        View myView = getLayoutInflater().inflate(R.layout.dialog_year, null);
        final MyNumberPicker npYear = myView.findViewById(R.id.npYear);
        Button valider = myView.findViewById(R.id.btnValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editText.setText(Integer.toString(npYear.getValue()));
               dialog.dismiss();
            }
        });

        myBuilder.setView(myView);
        dialog = myBuilder.create();
        dialog.show();

    }


}
