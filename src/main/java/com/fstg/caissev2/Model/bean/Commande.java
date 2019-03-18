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
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
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

    public static List<String> getAttributesNames(){
        List<String> attributesNames= new ArrayList<>();
        attributesNames.addAll(Arrays.asList("date", "time", "total"));
        return attributesNames;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        //  Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {

        return "\n commandeItems :\n" + commandeItems +
                "\n total : " + total +
                "\n date : " + date +
                " " + time;
    }
}
