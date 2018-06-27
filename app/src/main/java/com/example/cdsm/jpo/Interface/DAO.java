package com.example.cdsm.jpo.Interface;

import com.example.cdsm.jpo.Classe.Inscrit;

public interface DAO<T> {
    void Ajouter(T item);
    void Supprimer(int id);
    void Modifier(T item);
}
