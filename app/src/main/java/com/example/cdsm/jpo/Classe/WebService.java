package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;


public class WebService {
    String WEBSERVICE = "http://192.168.43.80:62205";
    Context context;

    public WebService(Context context){
        this.context = context;
    }

    // HTTP POST pour récupérer toutes les formations
    public void GETFormation(){
        Ion.with(context)
                // url du webservice
                .load(WEBSERVICE+"/api/formation")
                // retour en Json
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray>  result) {
                        // Si le webService renvoie le code HTTP 200 OK
                        if (result.getHeaders().code() == 200){
                            processJsonGetFormation(result.getResult());
                        }
                        // Sinon Erreur
                        else{
                            Toast.makeText(context,"Erreur avec WebService",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // HTTP POST pour récupérer tous les admins
    public void GETAdmin(){
        Ion.with(context)
                // url du webservice
                .load(WEBSERVICE+"/api/admin")
                // retour en Json
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray>  result) {
                        // Si le webService renvoie le code HTTP 200 OK
                        if (result.getHeaders().code() == 200){
                            processJsonGetAdmin(result.getResult());
                        }
                        // Sinon Erreur
                        else{
                            Toast.makeText(context,"Erreur avec WebService",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Récupération des formations depuis le Webservice vers la BDD locale
    private void processJsonGetFormation(JsonArray jsonArray) {
        try {
            FormationDAO formationDAO = new FormationDAO(context);
            //Vider la table Formation
            formationDAO.EmptyTableFormation();

            JsonObject jsonFormation;
            Formation formation;

            int size = jsonArray.size();
            // Récupération des données contenu dans le JsonArray
            for (int i = 0; i<size;i++){
                // Récupération du JsonObject à l'indice i
                jsonFormation = jsonArray.get(i).getAsJsonObject();
                // Récupération des données du JsonObject et insertion dans un objet Formation
                formation = new Formation();
                formation.setLib(jsonFormation.get("formLib").getAsString());
                formation.setNvformationid(jsonFormation.get("formNiveau").getAsInt());
                // Insertion de la formation dans la BDD locale
                formationDAO.Ajouter(formation);
            }
        } catch (Exception e) {
            Log.i("help", e.toString());
        }
    }

    //Récupération des admins depuis le Webservice vers la BDD Local
    private void processJsonGetAdmin(JsonArray jsonArray) {
        try {
            AdminDAO adminDAO = new AdminDAO(context);
            //Vider la table Admin
            adminDAO.EmptyTableAdmin();

            JsonObject jsonFormation;
            Admin admin;

            int size = jsonArray.size();
            for (int i = 0; i<size;i++){
                // Récupération du JsonObject à l'indice i
                jsonFormation = jsonArray.get(i).getAsJsonObject();
                // Récupération des données du JsonObject et insertion dans un objet Admin
                admin = new Admin();
                admin.setLogin(jsonFormation.get("adLogin").getAsString());
                admin.setMdp(jsonFormation.get("adMdp").getAsString());
                // Insertion de l'admin dans la BDD locale
                adminDAO.Ajouter(admin);
            }
        } catch (Exception e) {
            Log.i("help", e.toString());
        }
    }
}
