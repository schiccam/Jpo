package com.example.cdsm.jpo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.cdsm.jpo.Classe.MyListViewAdapterStat;
import com.example.cdsm.jpo.Classe.Stat;
import com.example.cdsm.jpo.Classe.WebService;
import com.example.cdsm.jpo.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;

public class WebServiceStatActivity extends AppCompatActivity {
    // Déclaration des variables
    ListView lvStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service_stat);

        lvStat = findViewById(R.id.lvStat);

        //Récupération des stats et affichage dans la listview
        GETStat();

    }
    // HTTP GET pour récupérer les stats du webservice
    public void GETStat(){

        Ion.with(this)
                // url du webservice
                .load("http://192.168.43.80:62205/api/inscrit")
                // retour en Json
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {
                        List<Stat> list = processJsonGetStat(result.getResult());
                        lvStat.setAdapter(new MyListViewAdapterStat(list, WebServiceStatActivity.this));
                    }
                });

    }
    // Gestion du JsonArray
    private List<Stat> processJsonGetStat(JsonArray jsonArray) {
        List<Stat> stats = new ArrayList<>();
        try {
            JsonObject jsonStat;
            Stat stat;

            int size = jsonArray.size();
            // Récupération des données contenu dans le JsonArray
            for (int i = 0; i<size;i++){
                // Récupération du JsonObject à l'indice i
                jsonStat = jsonArray.get(i).getAsJsonObject();
                // Récupération des données du JsonObject et insertion dans un objet Stat
                stat = new Stat();
                stat.setLibFormation(jsonStat.get("libelleFormation").getAsString());
                stat.setNbInsParForm(jsonStat.get("nbInscrit").getAsInt());
                // Insertion de la formation dans la liste
                stats.add(stat);
            }
        } catch (Exception e) {
            Log.i("erreur", e.toString());
        }
        return stats;
    }
}
