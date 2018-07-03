package com.example.cdsm.jpo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cdsm.jpo.Classe.WebService;
import com.example.cdsm.jpo.R;

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
}
