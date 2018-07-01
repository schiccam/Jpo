package com.example.cdsm.jpo.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cdsm.jpo.Classe.Formation;
import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.Hash;
import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.MaBaseSQLite;
import com.example.cdsm.jpo.Classe.MyListViewAdapterInscrit;
import com.example.cdsm.jpo.Classe.NiveauFormation;
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

        List<Inscrit> list = new ArrayList<>();
        InscritDAO inscritDAO = new InscritDAO(this);
        list = inscritDAO.getAllInscrit();
        MyListViewAdapterInscrit adapterInscrit = new MyListViewAdapterInscrit(list,this);

        lvInscrit = findViewById(R.id.lvInscrit);
        lvInscrit.setAdapter(adapterInscrit);
    }

    public void btnForm_Clicked(View view) {

        //Custom Dialog pour ajout de formation
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        View myView = getLayoutInflater().inflate(R.layout.dialog_addformation, null);
        final EditText etLibForm = myView.findViewById(R.id.etLibForm);
        final Spinner spNvFormation = myView.findViewById(R.id.spNvFormation);
        spNvFormation.setAdapter(getNvFormations());

        Button valider = myView.findViewById(R.id.btnValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Formation formation = new Formation();
                formation.setLib(etLibForm.getText().toString());
                //Récupération de l'id du niveau de la formation
                NiveauFormation nvform = (NiveauFormation) spNvFormation.getSelectedItem();
                formation.setNvformationid(nvform.getId());
                new FormationDAO(AdminActivity.this).Ajouter(formation);
                CloseLoginDialog();

            }
        });

        myBuilder.setView(myView);
        dialog = myBuilder.create();
        dialog.show();
    }

    private ArrayAdapter getNvFormations(){
        FormationDAO formationDAO = new FormationDAO(this);
        List Formations = formationDAO.getAllNiveauFormation();
        ArrayAdapter<NiveauFormation> adapter = new ArrayAdapter<NiveauFormation>(this, R.layout.custom_spinner, Formations);
        return adapter;
    }

    private void CloseLoginDialog(){
        dialog.dismiss();
    }
}
