package com.example.cdsm.jpo.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.cdsm.jpo.R;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    ListView lvInscrit;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Création de l'adapter avec la liste de tous les inscrits
        List<Inscrit> inscrits;
        InscritDAO inscritDAO = new InscritDAO(this);
        inscrits = inscritDAO.getAllInscrit();
        MyListViewAdapterInscrit adapterInscrit = new MyListViewAdapterInscrit(inscrits,this, this);

        lvInscrit = findViewById(R.id.lvInscrit);
        lvInscrit.setAdapter(adapterInscrit);

        //Création de l'adapter pour les stats
        FillStat();
    }

    public void FillStat(){
        List<Stat> stats = new FormationDAO(this).getStat();
        ListView lvStat = findViewById(R.id.lvStat);
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

                //Récuperation du libellé et de l'id
                Formation formation = new Formation();
                formation.setId(Integer.parseInt(etIDForm.getText().toString()));
                formation.setLib(etLibForm.getText().toString());
                //Récupération de l'id du niveau de la formation
                NiveauFormation nvform = (NiveauFormation) spNvFormation.getSelectedItem();
                formation.setNvformationid(nvform.getId());
                //Ajout de la formation dans la base de données
                new FormationDAO(AdminActivity.this).Ajouter(formation);
                CloseFormDialog();
            }
        });

        myBuilder.setView(myView);
        dialog = myBuilder.create();
        dialog.show();
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

    public void btnSync_Clicked(View view) {
        //TODO Synchro avec le web service
    }
}
