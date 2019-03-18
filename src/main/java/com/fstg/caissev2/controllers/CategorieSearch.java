package com.fstg.caissev2.controllers;

import com.fstg.caissev2.Model.bean.Produit;
import com.fstg.caissev2.Model.dao.CategorieService;
import com.fstg.caissev2.Model.dao.ProduitService;
import com.fstg.caissev2.controllers.util.TableViewProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategorieSearch implements Initializable {

    @FXML
    public ComboBox categorieComboBox;
    @FXML
    public Button search;
    @FXML
    public TableView<Produit> tableView;

    private TableViewProvider<Produit> produitTableViewProvider;

    private final CategorieService categorieService= new CategorieService();
    private final ProduitService produitService= new ProduitService();

    // Upadate or delete Produit Vraibles
    private ComboBox<String> stringComboBox= new ComboBox<>();
    private TextField libelle = new TextField();
    private TextField prix = new TextField();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategorieComboBoxItems();
        initTableView();
    }

    private void setCategorieComboBoxItems() {
        categorieComboBox.getItems().setAll(new ArrayList());
        categorieComboBox.setValue("SELECT");
        List<String> items= categorieService.findAllCategoriesName();
        categorieComboBox.getItems().addAll(items);
    }

    private void initTableView(){
        produitTableViewProvider=new TableViewProvider<Produit>(tableView,Produit.getAttributesNames());
    }

    public void searchProduct(ActionEvent actionEvent) {
        String value= (String) categorieComboBox.getValue();
        if(!value.equals("SELECT")){
            produitTableViewProvider.setList(produitService.findByCategorieLibelle(value));
            categorieComboBox.setValue("SELECT");
        }
    }

    public void showAlert(MouseEvent mouseEvent) {
        try {
            Produit produit= tableView.getSelectionModel().getSelectedItem();
            showAlert(produit);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void showAlert(Produit produit) {
        //Alert  With Container FOr Upadate Product
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("");
        alert.setContentText("Choose votre option:");

        ButtonType buttonTypeOne = new ButtonType("Update");
        ButtonType buttonTypeTwo = new ButtonType("Delete");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        setAlertContent(alert,produit);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
         updateProduit(produit);
        } else if (result.get() == buttonTypeTwo) {
                      produitService.deleteProduit(produit);
        } else{
            // ... user chose CANCEL or closed the dialog
            alert.hide();
        }
    }

    private void updateProduit(Produit produit) {
        try{
            Double prixUpdate= new Double(prix.getText());
            produit=produitService.updateProduit(produit.getId(),libelle.getText(),prixUpdate,stringComboBox.getValue());
            produitTableViewProvider.setList(new ArrayList<>());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private  void setAlertContent(Alert alert,Produit produit){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        stringComboBox.setValue(new String(produit.getCategorie().getLibelle()));
        stringComboBox.getItems().addAll(categorieComboBox.getItems());
        libelle.setText(produit.getLibelle());
        prix.setText(produit.getPrix()+"");

        grid.add(new Label("Categorie:"), 0, 0);
        grid.add(stringComboBox,1,0);
        grid.add(new Label("Produit:"), 0, 1);
        grid.add(libelle, 1, 1);
        grid.add(new Label("Prix:"), 0, 2);
        grid.add(prix, 1, 2);

        alert.getDialogPane().setContent(grid);
    }
}
