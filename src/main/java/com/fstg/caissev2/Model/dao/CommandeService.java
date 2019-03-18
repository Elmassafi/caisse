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


    private static double commandeItemTotal(CommandeItem c) {
        return c.getPrix() * c.getQte();
    }

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
        total = commandeItems.stream().mapToDouble(CommandeService::commandeItemTotal).sum();
        commande.setTotal(total);
    }

    public List<Double> commandeRevenues(LocalDate dateMin, LocalDate dateMax) {
        String query = "SELECT SUM(c.total) FROM Commande c where 1=1 ";
        if (dateMin != null) {
            query += " And  c.date >='" + dateMin + "'";
        }
        if (dateMax != null) {
            query += " And c.date <='" + dateMax + "'";
        }
        query += " group by c.date";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<LocalDate> commandeRevenuesDate(LocalDate dateMin, LocalDate dateMax) {
        String query = "SELECT DISTINCT c.date FROM Commande c where 1=1 ";
        if (dateMin != null) {
            query += " And  c.date >='" + dateMin + "'";
        }
        if (dateMax != null) {
            query += " And c.date <='" + dateMax + "'";
        }
        query += " group by c.date";
        return getEntityManager().createQuery(query).getResultList();
    }


    public List<Double> commandeRevenuesByCategorie() {
        String query = "SELECT SUM(c.total) FROM CommandeItem c where 1=1 group by  c.produit.categorie";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<Categorie> commandeCategorieByCategorie() {
        String query = "SELECT DISTINCT c.produit.categorie FROM CommandeItem c where 1=1 group by  c.produit.categorie";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<CommandeItem> findCommandeItemsByCommande(Long id) {
        String query = "SELECT c FROM CommandeItem c WHERE c.commande.id=" + id + "";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<Commande> findByDateMinMax(LocalDate dateMin, LocalDate dateMax) {
        String query = "SELECT DISTINCT c.commande  FROM CommandeItem c where 1=1 ";
        if (dateMin != null) {
            query += " And  c.commande.date >='" + dateMin + "'";
        }
        if (dateMax != null) {
            query += " And c.commande.date <='" + dateMax + "'";
        }
        return getMultipleResult(query);
    }

    public List<Commande> findAll() {
        String query = "SELECT c  FROM Commande c ";
        return getMultipleResult(query);
    }

    public void miseAjour() {
        List<Commande> commandes = findAll();
        commandes.forEach(c -> miseAjour(c));
    }

    private void miseAjour(Commande commande) {
        List<CommandeItem> commandeItems = findCommandeItemsByCommande(commande.getId());
        double total = 0D;
        total = commandeItems.stream().mapToDouble(c -> c.getTotal()).sum();
        commande.setTotal(total);
        save(commande);
    }
}
