package com.fstg.caissev2.controllers.util;

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
        //Alert  With Container FOr Upadate Product
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Okey");
        alert.setHeaderText("Info");
        alert.setContentText("Produit ");
        alert.show();
    }

    public static void showErrorAlert(){
        //Alert  With Container FOr Upadate Product
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText("ERROR");
        alert.show();
    }
}
