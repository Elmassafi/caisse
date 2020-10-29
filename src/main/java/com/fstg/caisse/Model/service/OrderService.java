package com.fstg.caisse.Model.service;

import com.fstg.caisse.Model.bean.Order;
import com.fstg.caisse.Model.bean.OrderItem;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    int saveOrder(Order order);

    List<Double> orderRevenuesForEachCategory();

    //Calculate Revenues
    List<Object[]> findRevenuesByDateMinMax(LocalDate dateMin, LocalDate dateMax);

    // List<Category> orderCategoryByCategory();

    List<OrderItem> findOrderItemsByOrderId(Long id);

    // List<Order> findOrderByDateMinMax(LocalDate dateMin, LocalDate dateMax);

    List<Order> findByDateMinMax(LocalDate dateMin, LocalDate dateMax);

    List<Order> findAll();
}
