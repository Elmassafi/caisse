/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fstg.caisse.Model.service.impl;

import com.fstg.caisse.Model.bean.Order;
import com.fstg.caisse.Model.bean.OrderItem;
import com.fstg.caisse.Model.dal.OrderDao;
import com.fstg.caisse.Model.dal.impl.DbOrderDao;
import com.fstg.caisse.Model.service.OrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Anas
 */
public class OrderServiceImpl implements OrderService {


    private final OrderDao orderDao = new DbOrderDao();

    private static double orderItemTotal(OrderItem c) {
        return c.getPrice() * c.getQte();
    }

    @Override
    public int saveOrder(Order order) {
        if (order == null) {
            return -1;
        } else if (order.getOrderItems().isEmpty()) {
            return -2;
        } else {
            calculateTotalOrder(order);
            saveOrderItems(order, order.getOrderItems());
            orderDao.saveOrder(order);
            return 1;
        }
    }

    private void saveOrderItems(Order order, List<OrderItem> orderItems) {
        if (order != null) {
            orderItems.forEach(c -> c.setOrder(order));
        }
    }

    private void calculateTotalOrder(Order order) {
        double total;
        List<OrderItem> orderItems = order.getOrderItems();
        total = orderItems.stream().mapToDouble(OrderServiceImpl::orderItemTotal).sum();
        order.setTotal(total);
    }

    @Override
    public List<Double> orderRevenuesForEachCategory() {
        return orderDao.orderRevenuesForEachCategory();
    }

    //Calculate Revenues
    @Override
    public List<Object[]> findRevenuesByDateMinMax(LocalDate dateMin, LocalDate dateMax) {
        return orderDao.findAllRevenues().stream()
                .filter(c -> isOrderBetween((LocalDate) c[0], dateMin, dateMax))
                .collect(Collectors.toList());
    }


    @Override
    public List<OrderItem> findOrderItemsByOrderId(Long id) {
        return orderDao.findOrderItemsByOrderId(id);
    }

    /*@Override
    public List<Order> findOrderByDateMinMax(LocalDate dateMin, LocalDate dateMax) {
        String query = "SELECT DISTINCT c.order  FROM OrderItem c where 1=1 ";
        if (dateMin != null) {
            query += " And  c.order.date >='" + dateMin + "'";
        }
        if (dateMax != null) {
            query += " And c.order.date <='" + dateMax + "'";
        }
        return getMultipleResult(query);
    }*/

    @Override
    public List<Order> findByDateMinMax(LocalDate dateMin, LocalDate dateMax) {
        return findAll().stream()
                .filter(c -> isOrderBetween(c.getDate(), dateMin, dateMax))
                .collect(Collectors.toList());
    }

    private boolean isOrderBetween(LocalDate date, LocalDate dateMin, LocalDate dateMax) {
        if (dateMin == null && dateMax == null) {
            return true;
        } else if (dateMin == null) {
            return date.isBefore(dateMax);
        } else if (dateMax == null) {
            return date.isAfter(dateMin);
        } else {
            return date.isAfter(dateMin) && date.isBefore(dateMax);
        }
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

 /*
    public List<Double> orderRevenues(LocalDate dateMin, LocalDate dateMax) {
        String query = "SELECT SUM(c.total) FROM Order c where 1=1 ";
        if (dateMin != null) {
            query += " And  c.date >='" + java.sql.Date.valueOf(dateMin) + "'";
        }
        if (dateMax != null) {
            query += " And c.date <='" + java.sql.Date.valueOf(dateMax) + "'";
        }
        query += " group by c.date";
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<LocalDate> orderRevenuesDate(LocalDate dateMin, LocalDate dateMax) {
        String query = "SELECT DISTINCT c.date FROM Order c where 1=1 ";
        if (dateMin != null) {
            query += " And  c.date >='" + java.sql.Date.valueOf(dateMin) + "'";
        }
        if (dateMax != null) {
            query += " And c.date <='" + java.sql.Date.valueOf(dateMax) + "'";
        }
        query += " group by c.date";
        return getEntityManager().createQuery(query).getResultList();
    }
*/
}
