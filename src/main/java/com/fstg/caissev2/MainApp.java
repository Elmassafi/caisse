package com.fstg.caissev2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApp extends Application {

    public static void forward(ActionEvent actionEvent, String pageName, Class myClass) throws IOException {
        Parent parent = FXMLLoader.load(myClass.getResource(pageName));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    public static void forward(Node node, String pageName, Class myClass) throws IOException {
        Parent parent = FXMLLoader.load(myClass.getResource(pageName));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) node.getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ServeurScene.fxml"));
        Group group = new Group();
        HBox hBox = new HBox();
        for (int i = 0; i < 5; i++) {
            hBox.getChildren().add(new Button("hello"));
        }
        Button button = new Button("go go");
        hBox.getChildren().add(button);
        button.setOnAction(this::handle);
        group.getChildren().add(hBox);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Hadi Tkhwira 5/5");
        stage.setScene(scene);
        stage.show();
    }

    private void handle(ActionEvent e) {
        try {
            forward(e, "/fxml/SplashScreen.fxml", getClass());
        } catch (IOException e1) {
            System.out.println("errror");
        }
    }
}
