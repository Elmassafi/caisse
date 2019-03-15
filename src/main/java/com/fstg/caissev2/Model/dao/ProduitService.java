package com.fstg.caissev2.Model.dao;

import com.fstg.caissev2.Model.bean.Categorie;
import com.fstg.caissev2.Model.bean.Produit;

public class ProduitService extends JPAUtility<Produit> {

    private final CategorieService categorieService = new CategorieService();

    public int saveProduit(Produit produit, String categorieName) {
        if (produit == null) {
            return -1;
        }
        Produit p = findByLibelle(produit.getLibelle());
        if (p != null) {
            return -2;
        } else {
            Categorie categorie = categorieService.findByLibelle(categorieName);
            if (categorie == null) {
                return -2;
            }
            produit.setCategorie(categorie);
            save(produit);
            return 1;
        }
    }

    public Produit findByLibelle(String libelle) {
        if (libelle != null && !libelle.isEmpty()) {
            String query = "SELECT p FROM Produit p WHERE p.libelle='" + libelle + "'";
            return (Produit) getEntityManager().createQuery(query).getSingleResult();
        } else {
            return null;
        }
    }
}
