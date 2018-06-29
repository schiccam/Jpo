package com.example.cdsm.jpo.Classe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

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

            //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listinscrit,parent,false);
        }

        //Handle TextView and display string from your list
        TextView tvNomInscrit = view.findViewById(R.id.tvNomInscrit);

        String nom =list.get(position).getNom();
        nom = nom.toUpperCase();
        String prenom=list.get(position).getPrenom();
        prenom = prenom.toLowerCase();
        tvNomInscrit.setText(nom +" "+prenom);

        TextView tvLibFormation = view.findViewById(R.id.tvLibFormation);
        FormationDAO formationDAO = new FormationDAO(context);
        String lib = formationDAO.getFormation(list.get(position).getFormation()).getLib();
        tvLibFormation.setText(lib);

        //Handle buttons and add onClickListeners
        Button btnSuppr = view.findViewById(R.id.btnSuppr);
        Button btnModif = view.findViewById(R.id.btnModif);

        btnSuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InscritDAO inscritDAO = new InscritDAO(context);
                inscritDAO.Supprimer(list.get(position).getId());
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        btnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("inscritID",list.get(position).getId());
            }
        });
        return view;
    }
}
