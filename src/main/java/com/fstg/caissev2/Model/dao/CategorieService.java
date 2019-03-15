package com.fstg.caissev2.Model.dao;

import com.fstg.caissev2.Model.bean.Categorie;

public class CategorieService extends JPAUtility<Categorie> {


    public boolean isCommandeExist(String libelle) {
        Categorie c = findByLibelle(libelle);
        return c != null;
    }

    public Categorie saveCategorie(Categorie categorie) {
        if (categorie != null) {
            if (!isCommandeExist(categorie.getLibelle())) {
                save(categorie);
                return categorie;
            }
        }
        return null;
    }

    public Categorie findByLibelle(String libelle) {
        if (libelle != null) {
            String query = "SELECT c FROM Categorie c where c.libelle='" + libelle.toLowerCase() + "'";
            return getSingleResult(query);
        } else {
            return null;
        }
    }

}
