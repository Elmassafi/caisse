package com.fstg.caissev2.controllers;

import com.fstg.caissev2.Model.bean.Commande;
import com.fstg.caissev2.Model.service.CommandeService;
import com.fstg.caissev2.controllers.util.TableViewProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsScreen implements Initializable {


    @FXML
    public DatePicker dateMax;
    @FXML
    public DatePicker dateMin;
    @FXML
    public DatePicker dateMinGraph;
    @FXML
    public DatePicker dateMaxGraph;
    @FXML
    public TableView tableView;

    private TableViewProvider<Commande> commandeTableViewProvider;
    private final CommandeService commandeService = new CommandeService();
    @FXML
    public CategoryAxis x;
    @FXML
    public NumberAxis y;
    @FXML
    public LineChart lineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drowLineChart();
        commandeTableViewProvider=new TableViewProvider<Commande>(tableView,Commande.getAttributesNames());
    }

    public void drowLineChart() {
        List<Object[]> objects = commandeService.findRevenuesByDateMinMax(dateMinGraph.getValue(), dateMaxGraph.getValue());
        XYChart.Series series = new XYChart.Series<>();
        for (Object[] object : objects) {
            series.getData().add(new XYChart.Data(object[0].toString(), object[1]));
            //    series.setData(objects);
        }
        lineChart.getData().setAll(series);
    }

    public void commandsByDate(ActionEvent actionEvent) {
        List<Commande> commands= commandeService.findByDateMinMax(dateMin.getValue(), dateMax.getValue());
        commandeTableViewProvider.setList(commands);
    }

    public void goHome(ActionEvent actionEvent) {
        HomeScreen.goHome(actionEvent, getClass());
    }
}
