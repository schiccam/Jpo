package com.example.cdsm.jpo.Interface;

import com.example.cdsm.jpo.Classe.Inscrit;

public interface DAO {
    void Ajouter(Inscrit inscrit);
    void Supprimer(int id);
    void Modifier(Inscrit inscrit);
}
