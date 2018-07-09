package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.cdsm.jpo.Activity.WebServiceStatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WebService {
    //Adresse du WebService
    String WEBSERVICE = "http://192.168.43.80:62205";
    Context context;

    public WebService(Context context){
        this.context = context;
    }

    public void POSTInscrit(List<Inscrit> inscrits){
        if (CheckConnection())
        {
            //JsonArray pour le Webservice
            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject;
            int listSize = inscrits.size();
            // pour chaque élément de la liste faire un jsonobject
            for (int i = 0; i<listSize; i++){
                jsonObject = new JsonObject();
                jsonObject.addProperty("insNom", inscrits.get(i).getNom());
                jsonObject.addProperty("insPrenom", inscrits.get(i).getPrenom());
                jsonObject.addProperty("insTel", inscrits.get(i).getTel());
                jsonObject.addProperty("insMail", inscrits.get(i).getMail());
                jsonObject.addProperty("insSexe", inscrits.get(i).getSexe());
                jsonObject.addProperty("insDateNaiss", inscrits.get(i).getDateNaiss());
                jsonObject.addProperty("insLieuNaiss", inscrits.get(i).getLieuNaiss());
                jsonObject.addProperty("insAdresse", inscrits.get(i).getAdresse());
                jsonObject.addProperty("insCP", inscrits.get(i).getCp());
                jsonObject.addProperty("insVille", inscrits.get(i).getVille());
                jsonObject.addProperty("insAnneeSco1", inscrits.get(i).getAnneeSco1());
                jsonObject.addProperty("insLibSco1", inscrits.get(i).getLibSco1());
                jsonObject.addProperty("insEtabSco1", inscrits.get(i).getEtabSco1());
                jsonObject.addProperty("insAnneeSco2", inscrits.get(i).getAnneeSco2());
                jsonObject.addProperty("insLibSco2", inscrits.get(i).getLibSco2());
                jsonObject.addProperty("insEtabSco2", inscrits.get(i).getEtabSco2());
                jsonObject.addProperty("insFormation", inscrits.get(i).getFormation());
                //ajouter le jsonobject au jsonarray
                jsonArray.add(jsonObject);
            }

            Ion.with(context)
                    .load(WEBSERVICE+"/api/inscrit")
                    //Envoie en Json
                    .setJsonArrayBody(jsonArray)
                    .asJsonArray()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<JsonArray>>() {
                        @Override
                        public void onCompleted(Exception e, Response<JsonArray> result) {
                            if (result.getHeaders().code() == 200){
                                new InscritDAO(context).EmptyTableInscrit();
                            }
                            // Sinon Erreur
                            else{
                                Toast.makeText(context,"Erreur avec WebService",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void POSTMail(String mail, int formation){

        if (CheckConnection())
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("receveur", mail);
            jsonObject.addProperty("formation", formation);

            Ion.with(context)
                    .load(WEBSERVICE+"/api/sendgrid")
                    //Envoie en Json
                    .setJsonObjectBody(jsonObject)
                    .asJsonObject()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<JsonObject>>() {
                        @Override
                        public void onCompleted(Exception e, Response<JsonObject> result) {
                            if (result.getHeaders().code() == 200){
                                Toast.makeText(context,"Mail envoyé",Toast.LENGTH_SHORT).show();
                            }
                            // Sinon Erreur
                            else{
                                Toast.makeText(context,"Erreur avec WebService",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void POSTFormation(List<Formation> formations){

        if (CheckConnection())
        {
            //JsonArray pour le Webservice
            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject;
            int listSize = formations.size();
            // pour chaque élément de la liste faire un jsonobject
            for (int i = 0; i<listSize; i++){
                jsonObject = new JsonObject();
                jsonObject.addProperty("formID", formations.get(i).getId());
                jsonObject.addProperty("formLib", formations.get(i).getLib());
                jsonObject.addProperty("formNiveau", formations.get(i).getNvformationid());

                //ajouter le jsonobject au jsonarray
                jsonArray.add(jsonObject);
            }


            Ion.with(context)
                    .load(WEBSERVICE+"/api/formation")
                    //Envoie en Json
                    .setJsonArrayBody(jsonArray)
                    .asJsonArray()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<JsonArray>>() {
                        @Override
                        public void onCompleted(Exception e, Response<JsonArray> result) {
                            if (!(result.getHeaders().code() == 200)){
                                Toast.makeText(context,"Erreur avec WebService",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // HTTP GET pour récupérer toutes les formations
    public void GETFormation(){

        if (CheckConnection())
        {
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
    }

    // HTTP GET pour récupérer tous les admins
    public void GETAdmin(){

        if (CheckConnection())
        {
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
    }

    // HTTP GET pour récupérer les stats du webservice
    public void GETStat(){

            Ion.with(context)
                    // url du webservice
                    .load(WEBSERVICE+"/api/inscrit")
                    // retour en Json
                    .asJsonArray()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<JsonArray>>() {
                        @Override
                        public void onCompleted(Exception e, Response<JsonArray> result) {

                        }
                    });

    }

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
                formation.setId(jsonFormation.get("formID").getAsInt());
                formation.setLib(jsonFormation.get("formLib").getAsString());
                formation.setNvformationid(jsonFormation.get("formNiveau").getAsInt());
                // Insertion de la formation dans la BDD locale
                formationDAO.Ajouter(formation);
            }
        } catch (Exception e) {
            Log.i("erreur", e.toString());
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
            Log.i("erreur", e.toString());
        }
    }

    private boolean CheckConnection(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
