package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.List;

public class WebService {
    Context context;

    public WebService(Context context){
        this.context = context;
    }

    // HTTP POST pour recuperer toutes les formations
    public void GETFormation(){
        Ion.with(context)
                .load("http://192.168.43.80:62205/api/formation")
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray>  result) {
                        if (result.getHeaders().code() == 200){
                            processJsonGetFormation(result.getResult());
                        }
                        else{
                            Toast.makeText(context,"Erreur avec WebService",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void GETAdmin(){
        Ion.with(context)
                .load("http://192.168.43.80:62205/api/admin")
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray>  result) {
                        if (result.getHeaders().code() == 200){
                            processJsonGetAdmin(result.getResult());
                        }
                        else{
                            Toast.makeText(context,"Erreur avec WebService",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void processJsonGetFormation(JsonArray jsonArray) {
        try {
            FormationDAO formationDAO = new FormationDAO(context);
            formationDAO.EmptyTableFormation();

            JsonObject jsonFormation;
            Formation formation;

            int size = jsonArray.size();
            for (int i = 0; i<size;i++){
                jsonFormation = jsonArray.get(i).getAsJsonObject();
                formation = new Formation();
                formation.setLib(jsonFormation.get("formLib").getAsString());
                formation.setNvformationid(jsonFormation.get("formNiveau").getAsInt());

                formationDAO.Ajouter(formation);
            }
        } catch (Exception e) {
            Log.i("help", e.toString());
        }
    }

    private void processJsonGetAdmin(JsonArray jsonArray) {
        try {
            AdminDAO adminDAO = new AdminDAO(context);
            adminDAO.EmptyTableAdmin();

            JsonObject jsonFormation;
            Admin admin;

            int size = jsonArray.size();
            for (int i = 0; i<size;i++){
                jsonFormation = jsonArray.get(i).getAsJsonObject();
                admin = new Admin();
                admin.setLogin(jsonFormation.get("adLogin").getAsString());
                admin.setMdp(jsonFormation.get("adMdp").getAsString());

                adminDAO.Ajouter(admin);
            }
        } catch (Exception e) {
            Log.i("help", e.toString());
        }
    }
}
