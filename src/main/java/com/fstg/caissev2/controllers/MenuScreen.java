package com.fstg.caissev2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
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
        setSearchPaneChild(searchPane,"/fxml/CategorieSearch.fxml",getClass());
        setSearchPaneChild(produitPane,"/fxml/ProduitAjouter.fxml",getClass());
        addSearchPaneChild(produitPane,"/fxml/CategorieScreen.fxml",getClass());
        setSearchPaneChild(statisticPane,"/fxml/CategorieStatistics.fxml",getClass());

    }

    public void setSearchPaneChild(Pane pane, String pageName, Class myClass){
        try {
            Parent parent = FXMLLoader.load(myClass.getResource(pageName));
            Scene scene = new Scene(parent);
            pane.getChildren().setAll(parent);
        }catch (Exception e){
            pane.getChildren().addAll(new Label("Errorr Loading This Component"+e));
        }
    }
    public void addSearchPaneChild(Pane pane, String pageName, Class myClass){
        try {
            Parent parent = FXMLLoader.load(myClass.getResource(pageName));
            Scene scene = new Scene(parent);
            pane.getChildren().add(parent);
        }catch (Exception e){
            pane.getChildren().addAll(new Label("Errorr Loading This Component"+e));
        }
    }

}
