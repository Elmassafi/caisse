/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fstg.caissev2.Model.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anas
 */
@Entity
public class CommandeItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double prix;
    private int qte;
    private double total;
    @ManyToOne
    private Produit produit;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Commande commande;


    public CommandeItem() {
    }

    public CommandeItem(Produit produit, double prix, int qte) {
        this.produit = produit;
        this.prix = prix;
        this.qte = qte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public static List<String> getAttributesNames(){
        List<String> attributesNames = new ArrayList<>(Arrays.asList("produit", "prix", "qte", "total"));
        return attributesNames;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommandeItem)) {
            return false;
        }
        CommandeItem other = (CommandeItem) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "\n prix :" + prix +
                " , qte :" + qte +
                " , produit :" + produit +
                "\n";
    }

}
