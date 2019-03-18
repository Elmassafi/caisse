package com.fstg.caissev2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServeurScene implements Initializable {


    @FXML
    public BorderPane borPane;
    @FXML
    public Button buttonAffiche;
    @FXML
    public Pane container;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ok(ActionEvent actionEvent) {
        handle(actionEvent);
    }

    private void handle(ActionEvent e) {
        try {
            setContainer("/fxml/CategorieScreen.fxml", getClass());
        } catch (IOException e1) {
            System.out.println("errror");
        }
    }

    public void setContainer(String pageName, Class myClass) throws IOException {
        Parent parent = FXMLLoader.load(myClass.getResource(pageName));
        Scene scene = new Scene(parent);
        container.getChildren().setAll(parent);
    }


}
