package com.example.cdsm.jpo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cdsm.jpo.Classe.Admin;
import com.example.cdsm.jpo.Classe.AdminDAO;
import com.example.cdsm.jpo.Classe.Formation;
import com.example.cdsm.jpo.Classe.FormationDAO;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.WebService;
import com.example.cdsm.jpo.R;

import java.util.List;

public class TestRESTActivity extends AppCompatActivity {
    WebService webService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rest);

        webService = new WebService(this);
    }

    public void GetFormation_Click(View view) {
        webService.GETFormation();
    }

    public void GetAdmin_Click(View view) {
        webService.GETAdmin();
    }

    public void PostInscrit_Click(View view) {
        webService.POSTInscrit(new InscritDAO(this).getAllInscrit());
    }

    public void PostFormation_Click(View view) {
        webService.POSTFormation(new FormationDAO(this).getAllFormation());
    }
}
