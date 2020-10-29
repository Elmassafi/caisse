package com.fstg.caisse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuScreen implements Initializable {

    @FXML
    public TilePane searchPane;
    @FXML
    public TilePane statisticPane;
    @FXML
    public Pane produitPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addChildPane(searchPane, "/fxml/ProduitSearch.fxml", getClass());
        addChildPane(produitPane, "/fxml/ProduitAjouter.fxml", getClass());
        addChildPane(produitPane, "/fxml/CategoryScreen.fxml", getClass());
        addChildPane(statisticPane, "/fxml/CategoryStatistics.fxml", getClass());

    }

    public void goHome(ActionEvent actionEvent) {
        HomeScreen.goHome(actionEvent, getClass());
    }

    private void addChildPane(Pane pane, String pageName, Class myClass) {
        try {
            Parent parent = FXMLLoader.load(myClass.getResource(pageName));
            pane.getChildren().add(parent);
        } catch (Exception e) {
            pane.getChildren().addAll(new Label("Errorr Loading This Component" + e));
        }
    }

}
