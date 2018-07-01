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
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cdsm.jpo.Activity.UpdateInscritForm_Activity;
import com.example.cdsm.jpo.R;

import java.util.List;

public class MyListViewAdapterStat extends BaseAdapter implements ListAdapter {

    private List<Stat> list;
    private Context context;

    public MyListViewAdapterStat(List<Stat> list, Context context){
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_liststat, null);
        }

        //Handle TextView and display string from your list
        TextView tvLib = view.findViewById(R.id.tvLibStat);
        TextView tvNb = view.findViewById(R.id.tvNbInsStat);

        //Affichage du libelé de la formation
        tvLib.setText(list.get(position).getLibFormation());

        //Affichage du nombre d'inscrit par formation
        int i = list.get(position).getNbInsParForm();
        if(i == 0){
            tvNb.setText("Aucun inscrit");
        }
        else{
            tvNb.setText(i+" inscrits");
        }

        //Changement de couleur
        if(position % 2 == 0)
            view.setBackgroundColor(Color.LTGRAY);
        else
            view.setBackgroundColor(Color.WHITE);

        return view;
    }
}
