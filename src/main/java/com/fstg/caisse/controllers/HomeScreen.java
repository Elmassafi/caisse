package com.fstg.caisse.controllers;

import com.fstg.caisse.MainApp;
import com.fstg.caisse.Model.service.OrderService;
import com.fstg.caisse.Model.service.impl.OrderServiceImpl;
import com.fstg.caisse.controllers.util.AlertShow;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreen implements Initializable {

    private final OrderService orderService = new OrderServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onMouseEntered(MouseEvent mouseEvent) {
        try {
            VBox vBox= (VBox) mouseEvent.getSource();
            vBox.setStyle("-fx-background-color: #e3bd54; -fx-border-radius: 100px;");
        }catch (Exception e){
            System.out.println("mouseEvent is Not VBox");
        }

    }

    public void onMouseExited(MouseEvent mouseEvent) {
        try {
            VBox vBox= (VBox) mouseEvent.getSource();
            vBox.setStyle("");
        }catch (Exception e){
            System.out.println("mouseEvent is Not VBox");
        }
    }

    public void switchToServant(MouseEvent mouseEvent) {
        try{
            MainApp.forward((Node) mouseEvent.getSource(), "/fxml/OrderCreate.fxml",getClass());
        }catch (Exception e){
            System.out.println();
        }
    }
    public void switchToStatistics(MouseEvent mouseEvent) {
        try{
            MainApp.forward((Node) mouseEvent.getSource(),"/fxml/StatisticsScreen.fxml",getClass());
        }catch (Exception e){
            System.out.println();
        }
    }

    public void switchToMenu(MouseEvent mouseEvent) {
        try {
            MainApp.forward((Node) mouseEvent.getSource(), "/fxml/MenuScreen.fxml", getClass());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void goHome(ActionEvent actionEvent, Class myClass) {
        try {
            MainApp.forward((Node) actionEvent.getSource(), "/fxml/HomeScreen.fxml", myClass);
        } catch (Exception e) {
            AlertShow.showErrorAlert();
        }
    }

}
