package com.fstg.caisse.controllers.util;

import javafx.scene.control.Alert;

public class AlertShow {

    public static void whatAlertIShouldShow(int i) {
        if (i > 0) {
            showSuccessfulAlert();
        } else {
            showErrorAlert();
        }
    }

    public static void showSuccessfulAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Effectué avec succès");
        alert.show();
    }

    public static void showErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText("Merci de vérifier les informations ");
        alert.show();
    }
}
