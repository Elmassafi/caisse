package com.fstg.caisse.controllers;

import com.fstg.caisse.Model.bean.Category;
import com.fstg.caisse.Model.service.CategoryService;
import com.fstg.caisse.Model.service.impl.CategoryServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryScreen implements Initializable {

    private final CategoryService categoryService = new CategoryServiceImpl();

    @FXML
    public TextField cateName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void createCategory(ActionEvent actionEvent) {
        if (cateName.getText().isEmpty()) {
            showAlert("Merci de donnez nom de Categorie ");
        } else if (categoryService.isCategoryExist(cateName.getText())) {
            showAlert("Deja Exist");
        } else {
            save(cateName.getText());
        }
    }

    public void save(String libelle) {
        Category category = new Category(libelle);
        category = categoryService.saveCategory(category);
        if (category != null) {
            showAlert("Ajouter Avec Succes");
            cateName.setText("");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
