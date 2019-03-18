package com.fstg.caissev2.controllers;

import com.fstg.caissev2.Model.dao.CommandeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategorieStatistics implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    @FXML
    public CategoryAxis x;
    @FXML
    public NumberAxis y;
    @FXML
    public LineChart lineChart;

    private final CommandeService commandeService= new CommandeService();

    private void drowlineChart(){
        XYChart.Series series = new XYChart.Series<>();
        List<Double> ress = commandeService.commandeRevenues(null);
        for (int i = 0; i < ress.size(); i++) {
            Double myRes = ress.get(i);
            series.getData().add(new XYChart.Data((i + 1) + "", myRes));
        }
        lineChart.getData().setAll(series);
    }

}
