package com.fstg.caisse.Model.dal;

import com.fstg.caisse.Model.bean.Order;
import com.fstg.caisse.Model.bean.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    List<Order> findAll();

    Optional<Order> findById(Long id);

    List<Double> orderRevenuesForEachCategory();

    Optional<Order> saveOrder(Order order);

    Optional<Order> updateOrder(Order order);

    List<Object[]> findAllRevenues();

    List<OrderItem> findOrderItemsByOrderId(Long id);
}
