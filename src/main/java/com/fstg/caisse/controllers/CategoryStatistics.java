package com.fstg.caisse.controllers;

import com.fstg.caisse.Model.bean.Category;
import com.fstg.caisse.Model.dal.CategoryDao;
import com.fstg.caisse.Model.dal.impl.DbCategoryDao;
import com.fstg.caisse.Model.service.OrderService;
import com.fstg.caisse.Model.service.impl.OrderServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryStatistics implements Initializable {

    private final OrderService orderService = new OrderServiceImpl();
    private final CategoryDao categoryDao = new DbCategoryDao();
    @FXML
    public CategoryAxis x;
    @FXML
    public NumberAxis y;
    @FXML
    public LineChart<String, Double> lineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("CategoryStatistics initialize");
        try {
            drawlingChart();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void drawlingChart() {
        XYChart.Series<String, Double> series = new XYChart.Series<>();

        List<Category> categories = categoryDao.orderCategoriesByCategory();
        List<Double> ress = orderService.orderRevenuesForEachCategory();

        for (int i = 0; i < ress.size(); i++) {
            Double myRes = ress.get(i);
            series.getData().add(new XYChart.Data(categories.get(i).getName(), myRes));
        }
        lineChart.getData().setAll(series);
    }

}
