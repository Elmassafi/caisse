package com.fstg.caisse.controllers;

import com.fstg.caisse.Model.bean.Product;
import com.fstg.caisse.Model.service.ProductService;
import com.fstg.caisse.Model.service.impl.CategoryServiceImpl;
import com.fstg.caisse.Model.service.impl.ProductServiceImpl;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductSearch implements Initializable {

    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    // Upadate or delete Produit Vraibles
    private final ComboBox<String> categorieAlertComboBox = new ComboBox<>();
    private final TextField libelle = new TextField();
    private final TextField prix = new TextField();
    @FXML
    public ComboBox<String> categoryComboBox;
    @FXML
    public Button search;
    @FXML
    public TableView<Product> tableView;

    public TableColumn<Product, String> productNameColumn;
    public TableColumn<Product, Double> productPriceColumn;
    public TableColumn<Product, String> productCategoryColumn;

    ObservableList<Product> products = FXCollections.observableArrayList();
    //private TableViewProvider<Product> produitTableViewProvider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCategorieComboBoxItems();
        initTableView();
    }

    public void initCategorieComboBoxItems() {
        categoryComboBox.getItems().setAll(new ArrayList<>());
        categoryComboBox.setValue("SELECT");
        List<String> items = categoryService.findAllCategoriesName();
        categoryComboBox.getItems().addAll(items);
        categorieAlertComboBox.getItems().setAll(new ArrayList<>());
        categorieAlertComboBox.getItems().addAll(categoryComboBox.getItems());
    }

    private void initTableView() {
        tableView.setItems(products);
        productNameColumn.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getName()));
        productPriceColumn.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPrice()));
        productCategoryColumn.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().getCategory().getName()));
        products.addAll(productService.findAll());
        //produitTableViewProvider = new TableViewProvider<>(tableView, Product.getAttributesNames());
        tableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showAlert()));
    }

    public void searchProduct(ActionEvent actionEvent) {
        String value = categoryComboBox.getValue();
        if (!value.equals("SELECT")) {
            products.clear();
            products.addAll(productService.findByCategoryName(value));
            //produitTableViewProvider.setList();
            categoryComboBox.setValue("SELECT");
        }
    }

    public void showAlert() {
        try {
            Product product = tableView.getSelectionModel().getSelectedItem();
            showAlert(product);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void showAlert(Product product) {
        //Alert  With Container FOr Upadate Product
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("");
        alert.setContentText("Choose votre option:");

        ButtonType buttonTypeOne = new ButtonType("Update");
        ButtonType buttonTypeTwo = new ButtonType("Delete");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        setAlertContent(alert, product);
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(a -> {
            if (result.get() == buttonTypeOne) {
                updateProduct(product);
            } else if (result.get() == buttonTypeTwo) {
                productService.deleteProduct(product);
            } else {
                // ... user chose CANCEL or closed the dialog
                alert.hide();

            }
        });
        tableView.getSelectionModel().clearSelection();
    }

    private void updateProduct(Product product) {
        try {
            Double priceUpdate = new Double(prix.getText());
            productService.updateProduct(product.getId(), libelle.getText(), priceUpdate, categorieAlertComboBox.getValue());
            //produitTableViewProvider.setList(new ArrayList<>());
            products.clear();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void setAlertContent(Alert alert, Product product) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        categorieAlertComboBox.setValue(product.getCategory().getName());
        libelle.setText(product.getName());
        prix.setText(product.getPrice() + "");

        grid.add(new Label("Categorie:"), 0, 0);
        grid.add(categorieAlertComboBox, 1, 0);
        grid.add(new Label("Produit:"), 0, 1);
        grid.add(libelle, 1, 1);
        grid.add(new Label("Prix:"), 0, 2);
        grid.add(prix, 1, 2);

        alert.getDialogPane().setContent(grid);
    }
}
