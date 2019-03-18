/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fstg.caissev2.Model.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anas
 */
@Entity
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double total;
    private LocalDate dateCommande = LocalDate.now();
    private LocalTime timeCommande = LocalTime.now();
    @OneToMany(mappedBy = "commande")
    private List<CommandeItem> commandeItems = new ArrayList<>();

    public Commande() {
    }

    public Commande(List<CommandeItem> commandeItems) {
        this.commandeItems = commandeItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getDateCommande() {
        return this.dateCommande;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CommandeItem> getCommandeItems() {
        return commandeItems;
    }

    public void setCommandeItems(List<CommandeItem> commandeItems) {
        if (commandeItems != null) {
            this.commandeItems = commandeItems;
        }
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public LocalTime getTimeCommande() {
        return timeCommande;
    }

    public void setTimeCommande(LocalTime timeCommande) {
        this.timeCommande = timeCommande;
    }

    public static List<String> getAttributesNames(){
        List<String> attributesNames= new ArrayList<>();
        attributesNames.addAll(Arrays.asList("total", "dateCommande", "timeCommande"));
        return attributesNames;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", total=" + total +
                ", dateCommande=" + dateCommande +
                ", timeCommande=" + timeCommande +
                ", commandeItems=" + commandeItems +
                '}';
    }
}
