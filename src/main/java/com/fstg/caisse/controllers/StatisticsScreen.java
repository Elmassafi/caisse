package com.fstg.caisse.controllers;

import com.fstg.caisse.Model.bean.Order;
import com.fstg.caisse.Model.service.OrderService;
import com.fstg.caisse.Model.service.impl.OrderServiceImpl;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsScreen implements Initializable {


    private final OrderService orderService = new OrderServiceImpl();
    @FXML
    public DatePicker dateMax;
    @FXML
    public DatePicker dateMin;
    @FXML
    public DatePicker dateMinGraph;
    @FXML
    public DatePicker dateMaxGraph;
    @FXML
    public TableView<Order> tableView;
    public TableColumn<Order, Double> orderTotalColumn;
    public TableColumn<Order, LocalTime> orderTimeColumn;
    public TableColumn<Order, LocalDate> orderDateColumn;
    @FXML
    public CategoryAxis x;
    @FXML
    public NumberAxis y;
    @FXML
    public LineChart lineChart;
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            drawLineChart();
            tableView.setItems(orders);
            orderDateColumn.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getDate()));
            orderTimeColumn.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getTime()));
            orderTotalColumn.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getTotal()));
    }

    public void drawLineChart() {
        List<Object[]> objects = orderService.findRevenuesByDateMinMax(dateMinGraph.getValue(), dateMaxGraph.getValue());
        XYChart.Series series = new XYChart.Series<>();
        for (Object[] object : objects) {
            series.getData().add(new XYChart.Data(object[0].toString(), object[1]));
            //    series.setData(objects);
        }
        lineChart.getData().setAll(series);
    }

    public void commandsByDate(ActionEvent actionEvent) {
        List<Order> orders = orderService.findByDateMinMax(dateMin.getValue(), dateMax.getValue());
        this.orders.clear();
        this.orders.addAll(orders);
    }

    public void goHome(ActionEvent actionEvent) {
        HomeScreen.goHome(actionEvent, getClass());
    }
}
