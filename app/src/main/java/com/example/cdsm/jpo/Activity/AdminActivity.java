package com.example.cdsm.jpo.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cdsm.jpo.Classe.Formation;
import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.Hash;
import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.MaBaseSQLite;
import com.example.cdsm.jpo.Classe.MyListViewAdapterInscrit;
import com.example.cdsm.jpo.Classe.MyListViewAdapterStat;
import com.example.cdsm.jpo.Classe.NiveauFormation;
import com.example.cdsm.jpo.Classe.Stat;
import com.example.cdsm.jpo.Classe.WebService;
import com.example.cdsm.jpo.R;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    ListView lvInscrit;
    ListView lvStat;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lvStat = findViewById(R.id.lvStat);
        lvInscrit = findViewById(R.id.lvInscrit);


        //Création de l'adapter avec la liste de tous les inscrits
        FillLocalInscrit();
        //Création de l'adapter pour les stats
        FillLocalStat();
    }

    private void FillLocalInscrit() {
        List<Inscrit> inscrits = new InscritDAO(this).getAllInscrit();
        lvInscrit.setAdapter(new MyListViewAdapterInscrit(inscrits, this, this));
    }

    public void FillLocalStat(){
        List<Stat> stats = new FormationDAO(this).getStat();
        lvStat.setAdapter(new MyListViewAdapterStat(stats, this));
    }

    public void btnForm_Clicked(View view) {

        //Custom Dialog pour ajout de formation
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        View myView = getLayoutInflater().inflate(R.layout.dialog_addformation, null);
        final EditText etIDForm = myView.findViewById(R.id.etIDForm);
        final EditText etLibForm = myView.findViewById(R.id.etLibForm);
        final Spinner spNvFormation = myView.findViewById(R.id.spNvFormation);
        spNvFormation.setAdapter(getNvFormations());

        Button valider = myView.findViewById(R.id.btnValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Récupération des ID des formations de la bdd
                List<Integer> ids = new FormationDAO(AdminActivity.this).getAllFormationID();

                if(!(isEmpty(etIDForm)) && !(isEmpty(etLibForm))){
                    //Si ID n'existe pas dans la bdd alors ajout de la formation dans la bdd
                    if(!(ids.contains(Integer.parseInt(etIDForm.getText().toString()))))
                    {
                        //Récuperation du libellé et de l'id
                        Formation formation = new Formation();
                        formation.setId(Integer.parseInt(etIDForm.getText().toString()));
                        formation.setLib(etLibForm.getText().toString());
                        //Récupération de l'id du niveau de la formation
                        NiveauFormation nvform = (NiveauFormation) spNvFormation.getSelectedItem();
                        formation.setNvformationid(nvform.getId());
                        //Ajout de la formation dans les bases de données
                        FormationDAO formationDAO = new FormationDAO(AdminActivity.this);
                        formationDAO.Ajouter(formation);
                        new WebService(AdminActivity.this).POSTFormation(formationDAO.getAllFormation());
                        CloseFormDialog();
                    }
                    else{
                        etIDForm.setError("Existe déjà");
                    }
                }
                else{
                    if(isEmpty(etIDForm)){
                        etIDForm.setError("Champ Obligatoire");
                    }
                    if(isEmpty(etLibForm)){
                        etLibForm.setError("Champ Obligatoire");
                    }
                }
            }
        });

        myBuilder.setView(myView);
        dialog = myBuilder.create();
        dialog.show();
    }

    public void btnSync_Clicked(View view) {

        WebService webService = new WebService(this);
        webService.POSTInscrit(new InscritDAO(this).getAllInscrit());
        webService.POSTFormation(new FormationDAO(this).getAllFormation());

        //Handler pour vider les listview
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //Mise à jour des listview
                FillLocalInscrit();
                FillLocalStat();
                Toast.makeText(AdminActivity.this,"Synchronisation fini!",Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    public void btnStat_Clicked(View view) {
        if(CheckConnection()){
            Intent intent = new Intent(this,WebServiceStatActivity.class);
            startActivity(intent);
        }
        else{
            //Affiche un dialog pour obliger l'utilisateur à activer le wifi
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("une connexion internet est requise\n" +
                    " pour avoir accès à cette page.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    //Récupere dans une liste tout les niveaux de formation depuis la BDD
    private ArrayAdapter getNvFormations(){
        FormationDAO formationDAO = new FormationDAO(this);
        List Formations = formationDAO.getAllNiveauFormation();
        //Création de l'adapter pour le spinner
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

    private void CloseFormDialog(){
        dialog.dismiss();
    }

    private boolean CheckConnection(){
        //vérification de la connexion
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private boolean isEmpty(EditText et){
        CharSequence str = et.getText().toString();
        return TextUtils.isEmpty(str);
    }


}
