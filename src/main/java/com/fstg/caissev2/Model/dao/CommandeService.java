/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fstg.caissev2.Model.dao;

import com.fstg.caissev2.Model.bean.Commande;
import com.fstg.caissev2.Model.bean.CommandeItem;

import java.util.List;

/**
 * @author Anas
 */
public class CommandeService extends JPAUtility {

  /*//  public CommandeService() {
        super(Commande.class);
    }*/

    public void saveCommande(Commande commande) {
        getEntityManager().getTransaction().begin();
        if (commande != null) {
            if (!commande.getCommandeItems().isEmpty()) {
                caluleTotalCommande(commande);
                getEntityManager().persist(commande);
                saveCommandeItems(commande, commande.getCommandeItems());
                getEntityManager().getTransaction().commit();
            }
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
}
