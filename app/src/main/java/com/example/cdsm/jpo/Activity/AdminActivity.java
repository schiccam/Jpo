package com.example.cdsm.jpo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cdsm.jpo.Classe.Inscrit;
import com.example.cdsm.jpo.Classe.InscritDAO;
import com.example.cdsm.jpo.Classe.MyListViewAdapterInscrit;
import com.example.cdsm.jpo.R;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    ListView lvInscrit;

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
}
