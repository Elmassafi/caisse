package com.fstg.caissev2.Model.service;

import com.fstg.caissev2.Model.bean.Categorie;

import java.util.List;

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

    public List<String> findAllCategoriesName(){
        return getEntityManager().createQuery("SELECT c.libelle FROM Categorie c where 1=1").getResultList();
    }

    public List<Categorie> findAll(){
        return getMultipleResult("SELECT c FROM Categorie c where 1=1");
    }

}
