package com.fstg.caissev2.Model.dao;

import com.fstg.caissev2.Model.bean.Categorie;
import com.fstg.caissev2.Model.bean.Produit;

import java.util.ArrayList;
import java.util.List;

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
            return (Produit)  getSingleResult(query);
        } else {
            return null;
        }
    }
    public List<Produit> findAll(){
        return getMultipleResult("SELECT p FROM Produit p");
    }
    public List<Produit> findByCategorieLibelle(String libelle){
        if (libelle != null && !libelle.isEmpty()) {
            String query = "SELECT p FROM Produit p WHERE p.categorie.libelle='" + libelle + "'";
            return getMultipleResult(query);
        } else {
            return new ArrayList<>();
        }
    }

    public Produit updateProduit(Long id, String libelle, Double prixUpdate, String categorieName) {
        Produit produit=findByLibelle(libelle);
        if(produit==null){
            produit= findById(id);
            if(produit!=null){
                produit.setLibelle(libelle);
            }
        }
        if(prixUpdate>0) produit.setPrix(prixUpdate);
        if(!produit.getCategorie().getLibelle().equals(categorieName)){
            Categorie categorie= categorieService.findByLibelle(categorieName);
            produit.setCategorie(categorie);
        }
        save(produit);
        return produit;
    }

    private Produit findById(Long id) {
        if (id != null) {
            String query = "SELECT p FROM Produit p WHERE p.id=" + id + "";
            return getSingleResult(query);
        } else {
            return null;
        }
    }

    public void deleteProduit(Produit produit) {
        // TODO
        System.out.println("Not now");
    }
}
