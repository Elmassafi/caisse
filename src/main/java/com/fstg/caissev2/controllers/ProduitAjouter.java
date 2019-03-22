package com.fstg.caissev2.controllers;

import com.fstg.caissev2.Model.bean.Produit;
import com.fstg.caissev2.Model.service.CategorieService;
import com.fstg.caissev2.Model.service.ProduitService;
import com.fstg.caissev2.controllers.util.AlertShow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitAjouter implements Initializable {

    @FXML
    private ComboBox cateBox;
    @FXML
    private TextField libelle;
    @FXML
    private TextField prix;

    private final CategorieService categorieService= new CategorieService();

    private final ProduitService produitService = new ProduitService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBox();
    }

    @FXML
    private void initComboBox() {
        cateBox.getItems().setAll(new ArrayList());
        cateBox.setValue("SELECT");
        List<String> items= categorieService.findAllCategoriesName();
        cateBox.getItems().addAll(items);
    }

    public void addProduit(ActionEvent actionEvent) {
            addProduit();
    }

    private void addProduit(){
        if(cateBox.getValue().equals("SELECT")){
            AlertShow.showErrorAlert();
        }else{
            Produit produit= getProduit();
            if(produit==null){
                AlertShow.showErrorAlert();
            }else{
                int i=produitService.saveProduit(produit,(String) cateBox.getValue());
                if(i!=1){
                    AlertShow.showErrorAlert();
                }else{
                    AlertShow.showSuccessfulAlert();
                    libelle.setText(" ");
                    prix.setText(" ");
                    cateBox.setValue("SELECT");
                }
            }
        }
    }

    private Produit getProduit(){
        Produit produit= new Produit();
        try{
            produit.setLibelle(libelle.getText());
            produit.setPrix(new Double(prix.getText()));
        }catch (Exception e){
            return null;
        }
        return produit;
    }

}


