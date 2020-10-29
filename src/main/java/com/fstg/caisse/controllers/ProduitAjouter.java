package com.fstg.caisse.controllers;

import com.fstg.caisse.Model.bean.Product;
import com.fstg.caisse.Model.service.impl.ProductServiceImpl;
import com.fstg.caisse.Model.service.impl.CategoryServiceImpl;
import com.fstg.caisse.Model.service.ProductService;
import com.fstg.caisse.controllers.util.AlertShow;
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
    private ComboBox<String> cateBox;
    @FXML
    private TextField libelle;
    @FXML
    private TextField prix;

    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();

    private final ProductService productService = new ProductServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBox();
    }

    @FXML
    private void initComboBox() {
        cateBox.getItems().setAll(new ArrayList<>());
        cateBox.setValue("SELECT");
        List<String> items= categoryService.findAllCategoriesName();
        cateBox.getItems().addAll(items);
    }

    public void addProduit(ActionEvent actionEvent) {
            addProduit();
    }

    private void addProduit(){
        if(cateBox.getValue().equals("SELECT")){
            AlertShow.showErrorAlert();
        }else{
            Product product = getProduit();
            if(product ==null){
                AlertShow.showErrorAlert();
            }else{
                int i= productService.saveProduct(product, cateBox.getValue());
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

    private Product getProduit(){
        Product product = new Product();
        try{
            product.setName(libelle.getText());
            product.setPrice(new Double(prix.getText()));
        }catch (Exception e){
            return null;
        }
        return product;
    }

}


