/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fstg.caissev2.Model.dao;

import com.fstg.caissev2.Model.bean.Categorie;
import com.fstg.caissev2.Model.bean.Commande;
import com.fstg.caissev2.Model.bean.CommandeItem;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Anas
 */
public class CommandeService extends JPAUtility {


    public int saveCommande(Commande commande) {
        if (commande == null) {
            return -1;
        } else if (commande.getCommandeItems().isEmpty()) {
            return -2;
        } else {
            getEntityManager().getTransaction().begin();
            caluleTotalCommande(commande);
            getEntityManager().persist(commande);
            saveCommandeItems(commande, commande.getCommandeItems());
            getEntityManager().getTransaction().commit();
            return 1;
        }
    }

    private void saveCommandeItems(Commande commande, List<CommandeItem> commandeItems) {
        if (commande != null) {
            commandeItems.forEach(c -> {
                c.setCommande(commande);
                getEntityManager().persist(c);
            });
        }
    }

    private void caluleTotalCommande(Commande commande) {
        double total = 0D;
        List<CommandeItem> commandeItems = commande.getCommandeItems();
        total = commandeItems.stream().mapToDouble(CommandeItem::getPrix).sum();
        commande.setTotal(total);
    }

    public List<Double> commandeRevenues(LocalDate dateMin,LocalDate dateMax){
        String query="SELECT SUM(c.total) FROM Commande c where 1=1 ";
        if(dateMin!=null){
            query+=" And  c.dateCommande >='"+dateMin+"'";
        }
        if(dateMax!=null){
            query+=" And c.dateCommande <='"+dateMax+"'";
        }
        query+=" group by c.dateCommande";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<LocalDate> commandeRevenuesDate(LocalDate dateMin, LocalDate dateMax) {
        String query = "SELECT DISTINCT c.dateCommande FROM Commande c where 1=1 ";
        if (dateMin != null) {
            query += " And  c.dateCommande >='" + dateMin + "'";
        }
        if (dateMax != null) {
            query += " And c.dateCommande <='" + dateMax + "'";
        }
        query += " group by c.dateCommande";
        return getEntityManager().createQuery(query).getResultList();
    }


    public List<Double> commandeRevenuesByCategorie() {
        String query = "SELECT SUM(c.prix) FROM CommandeItem c where 1=1 group by  c.produit.categorie";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<Categorie> commandeCategorieByCategorie() {
        String query = "SELECT DISTINCT c.produit.categorie FROM CommandeItem c where 1=1 group by  c.produit.categorie";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<Commande> findByDateMinMax(LocalDate dateMin, LocalDate dateMax) {
        String query="SELECT DISTINCT c.commande  FROM CommandeItem c where 1=1";
        if(dateMin!=null){
            query+=" And  c.commande.dateCommande >='"+dateMin+"'";
        }
        if(dateMax!=null){
            query+=" And c.commande.dateCommande <='"+dateMax+"'";
        }
        return getMultipleResult(query);
    }
}
