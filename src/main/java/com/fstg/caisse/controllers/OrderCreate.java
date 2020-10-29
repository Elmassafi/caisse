package com.fstg.caisse.controllers;

import com.fstg.caisse.Model.bean.Order;
import com.fstg.caisse.Model.bean.OrderItem;
import com.fstg.caisse.Model.bean.Product;
import com.fstg.caisse.Model.service.*;
import com.fstg.caisse.Model.service.impl.CategoryServiceImpl;
import com.fstg.caisse.Model.service.impl.OrderServiceImpl;
import com.fstg.caisse.Model.service.impl.ProductServiceImpl;
import com.fstg.caisse.controllers.util.AlertShow;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderCreate implements Initializable {


    private final OrderService orderService = new OrderServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<OrderItem> commandItems = FXCollections.observableArrayList();
    private final TextField prix = new TextField();
    @FXML
    public ComboBox<String> categoriesComboBox;
    @FXML
    public Button search;
    @FXML
    public TableView<Product> productTableView;
    @FXML
    public TableColumn<Product, String> productLibelleColumn;
    @FXML
    public TableColumn<Product, Double> productPrixColumn;
    @FXML
    public TableColumn<Product, String> productCategorieColumn;
    @FXML
    public TextField qte;
    @FXML
    public TableView<OrderItem> commandItemTableView;
    @FXML
    public TableColumn<OrderItem, Product> productCommandItem;
    @FXML
    public TableColumn<OrderItem, Double> priceCommandItem;
    @FXML
    public TableColumn<OrderItem, Integer> qteCommandItem;
    @FXML
    public TableColumn<OrderItem, Double> totalCommandItem;
    private Product productSelected;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategorieComboBoxItems();
        initTableView();
    }

    private void setCategorieComboBoxItems() {
        categoriesComboBox.getItems().setAll(new ArrayList<>());
        categoriesComboBox.setValue("SELECT");
        List<String> items = categoryService.findAllCategoriesName();
        categoriesComboBox.getItems().addAll(items);
    }

    private void initTableView() {

        productTableView.setItems(products);
        productPrixColumn.setCellValueFactory((row) -> new SimpleObjectProperty<>(row.getValue().getPrice()));
        productCategorieColumn.setCellValueFactory((row) -> new SimpleStringProperty(row.getValue().getCategory().getName()));
        productLibelleColumn.setCellValueFactory((row) -> new SimpleStringProperty(row.getValue().getName()));

        commandItemTableView.setItems(commandItems);
        productCommandItem.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getProduct()));
        priceCommandItem.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPrice()));
        qteCommandItem.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getQte()));
        totalCommandItem.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getTotal()));
    }

    private void refresh() {
        products.clear();
        commandItems.clear();
        prix.setText("");
    }

    public void searchProduct() {
        String value = categoriesComboBox.getValue();
        if (!value.equals("SELECT")) {
            products.clear();
            products.addAll(productService.findByCategoryName(value));
            categoriesComboBox.setValue("SELECT");
        }
    }

    public void showAlert() {
        try {
            productSelected = productTableView.getSelectionModel().getSelectedItem();
            qte.setFocusTraversable(true);
        } catch (Exception e) {
            AlertShow.showErrorAlert();
        }
    }

    public void addCommandItem() {
        try {
            Integer qteCommand = new Integer(qte.getText());
            OrderItem orderItem = new OrderItem(productSelected, productSelected.getPrice(), qteCommand);
            orderItem.setTotal(productSelected.getPrice() * qteCommand);
            commandItems.add(orderItem);
        } catch (Exception e) {
            AlertShow.showErrorAlert();
        }
    }

    public void validCommand() {
        Order order = new Order(new ArrayList<>(commandItems));
        order.setTotal(order.getOrderItems().stream().mapToDouble(OrderItem::getTotal).sum());
        showAlertCommandBrief(order);
    }

    private void showAlertCommandBrief(Order order) {
        //Alert  With Container FOr Upadate Product
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("verifiez vos information pour valider");
        alert.setContentText("Choose votre option:");
        TextArea textArea = new TextArea(order.toString());
        textArea.setEditable(false);
        alert.getDialogPane().setContent(textArea);
        ButtonType buttonTypeOne = new ButtonType("Valider");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(a -> {
            if (a.equals(buttonTypeOne)) {
                int i = orderService.saveOrder(order);
                AlertShow.whatAlertIShouldShow(i);
                refresh();
            }

        });
    }

    public void goHome(ActionEvent actionEvent) {
        HomeScreen.goHome(actionEvent, getClass());
    }
}