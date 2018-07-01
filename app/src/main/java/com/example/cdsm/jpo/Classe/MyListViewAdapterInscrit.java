package com.example.cdsm.jpo.Classe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cdsm.jpo.Activity.UpdateInscritForm_Activity;
import com.example.cdsm.jpo.R;
import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapterInscrit extends BaseAdapter implements ListAdapter {

    private List<Inscrit> list;
    private Context context;

    public MyListViewAdapterInscrit(List<Inscrit> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listinscrit, null);
        }

        //Handle TextView and display string from your list
        TextView tvNomInscrit = view.findViewById(R.id.tvNomInscrit);

        //Affichage du nom et prenom de l'inscrit
        String nom =list.get(position).getNom();
        nom = nom.toUpperCase();
        String prenom=list.get(position).getPrenom();
        prenom = prenom.toLowerCase();
        tvNomInscrit.setText(nom +" "+prenom);

        //Affichage de la formation de l'inscrit
        TextView tvLibFormation = view.findViewById(R.id.tvLibFormation);
        FormationDAO formationDAO = new FormationDAO(context);
        String lib = formationDAO.getFormation(list.get(position).getFormation()).getLib();
        tvLibFormation.setText(lib);

        //Handle buttons and add onClickListeners
        ImageButton btnSuppr = view.findViewById(R.id.btnSuppr);
        ImageButton btnModif = view.findViewById(R.id.btnModif);

        btnSuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Création d'une alerte pour confirmer le choix de l'admin
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("êtes-vous sûr de vouloir supprimer ?")
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Si oui suppression de la fiche
                                InscritDAO inscritDAO = new InscritDAO(context);
                                inscritDAO.Supprimer(list.get(position).getId());
                                list.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NON",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent vers le formulaire de modification
                Intent intent = new Intent(context, UpdateInscritForm_Activity.class);
                intent.putExtra("inscritID",list.get(position).getId());
                context.startActivity(intent);

            }
        });

        if(position % 2 == 0)
            view.setBackgroundColor(Color.LTGRAY);
        else
            view.setBackgroundColor(Color.WHITE);

        return view;
    }
}
