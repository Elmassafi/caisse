package com.fstg.caissev2.controllers;

import com.fstg.caissev2.Model.bean.Categorie;
import com.fstg.caissev2.Model.dao.CategorieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    private final CategorieService categorieService = new CategorieService();
    @FXML
    public TextField cateName;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void createCategorie(ActionEvent actionEvent) {
        if (cateName.getText().isEmpty()) {
            showAlert("Merci de donnez Categorie nome");
        } else if (categorieService.isCommandeExist(cateName.getText())) {
            showAlert("Deja Exist");
        } else {
            save(cateName.getText());
        }
    }

    public void save(String libelle) {
        Categorie categorie = new Categorie(libelle);
        categorie = categorieService.saveCategorie(categorie);
        if (categorie != null) {
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
