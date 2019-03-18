package com.fstg.caissev2.controllers;

import com.fstg.caissev2.Model.bean.Commande;
import com.fstg.caissev2.Model.bean.CommandeItem;
import com.fstg.caissev2.Model.bean.Produit;
import com.fstg.caissev2.Model.dao.CategorieService;
import com.fstg.caissev2.Model.dao.CommandeService;
import com.fstg.caissev2.Model.dao.ProduitService;
import com.fstg.caissev2.controllers.util.AlertShow;
import com.fstg.caissev2.controllers.util.TableViewProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CommandeCreate implements Initializable {


    @FXML
    public ComboBox categorieComboBox;
    @FXML
    public Button search;
    @FXML
    public TableView<Produit> tableView;
    @FXML
    public TableView<CommandeItem> commandeItemTableView;
    public TextField qte;

    private final CommandeService commandeService = new CommandeService();
    private TableViewProvider<Produit> produitTableViewProvider;
    private TableViewProvider<CommandeItem> commandeItemTableViewProvider;

    private final CategorieService categorieService = new CategorieService();
    private final ProduitService produitService = new ProduitService();

    // Upadate or delete Produit Vraibles
    private ComboBox<String> stringComboBox = new ComboBox<>();
    private TextField libelle = new TextField();
    private TextField prix = new TextField();
    private Produit produitSelected;
    private List<CommandeItem> commandeItems = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategorieComboBoxItems();
        initTableView();
    }

    private void setCategorieComboBoxItems() {
        categorieComboBox.getItems().setAll(new ArrayList());
        categorieComboBox.setValue("SELECT");
        List<String> items = categorieService.findAllCategoriesName();
        categorieComboBox.getItems().addAll(items);
    }

    private void initTableView() {
        produitTableViewProvider = new TableViewProvider<Produit>(tableView, Produit.getAttributesNames());
        commandeItemTableViewProvider= new TableViewProvider<CommandeItem>(commandeItemTableView,CommandeItem.getAttributesNames());
    }

    private void refresh() {
        produitTableViewProvider.setList(new ArrayList<>());
        commandeItemTableViewProvider.setList(new ArrayList<>());
        prix.setText("");
    }

    public void searchProduct(ActionEvent actionEvent) {
        String value = (String) categorieComboBox.getValue();
        if (!value.equals("SELECT")) {
            produitTableViewProvider.setList(produitService.findByCategorieLibelle(value));
            categorieComboBox.setValue("SELECT");
        }
    }

    public void showAlert(MouseEvent mouseEvent) {
        try {
            produitSelected = tableView.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            AlertShow.showErrorAlert();
        }
    }

    public void ajouterCommandeItem(ActionEvent actionEvent) {
        try {
            Integer qteCommandee = new Integer(qte.getText());
            CommandeItem commandeItem = new CommandeItem(produitSelected, produitSelected.getPrix(), qteCommandee);
            commandeItem.setTotal(produitSelected.getPrix() * qteCommandee);
            commandeItemTableViewProvider.addItem(commandeItem);
        } catch (Exception e) {
            AlertShow.showErrorAlert();
        }
    }

    public void valideCommande(ActionEvent actionEvent) {
        Commande commande = new Commande(commandeItemTableViewProvider.getList());
        commande.setTotal(commande.getCommandeItems().stream().mapToDouble(c -> c.getTotal()).sum());
        showAlertCommandeBref(commande);
    }

    private void showAlertCommandeBref(Commande commande) {
        //Alert  With Container FOr Upadate Product
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("verifiez vos information pour valider");
        alert.setContentText("Choose votre option:");
        TextArea textArea = new TextArea(commande.toString());
        textArea.setEditable(false);
        alert.getDialogPane().setContent(textArea);
        ButtonType buttonTypeOne = new ButtonType("Valider");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            int i = commandeService.saveCommande(commande);
            AlertShow.whatAlertIShouldShow(i);
            refresh();
        } else {
            // ... user chose CANCEL or closed the dialog
            alert.hide();
        }
    }

    public void goHome(ActionEvent actionEvent) {
        AdminScreen.goHome(actionEvent, getClass());
    }
}