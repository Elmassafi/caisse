package com.fstg.caisse.controllers;

import com.fstg.caisse.MainApp;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreen implements Initializable {

    @FXML
    public ImageView image;
    @FXML
    public VBox vbox;
    @FXML
    public ProgressBar progress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //   jpaUtility.testConnection();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(5000), vbox);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(this::handle);
        fadeTransition.play();
    }

    private void handle(ActionEvent e) {
        try {
            MainApp.forward(vbox, "/fxml/HomeScreen.fxml", getClass());
        } catch (IOException e1) {
            System.out.println("errror " + e1);
        }
    }
}
