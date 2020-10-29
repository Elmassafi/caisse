package com.fstg.caisse.Model.dal.impl;

import com.fstg.caisse.Model.bean.Order;
import com.fstg.caisse.Model.bean.OrderItem;
import com.fstg.caisse.Model.dal.JPAUtility;
import com.fstg.caisse.Model.dal.OrderDao;

import java.util.List;
import java.util.Optional;

public class DbOrderDao extends JPAUtility<Order> implements OrderDao {

    @Override
    public List<Order> findAll() {
        String query = "SELECT o FROM Order o ";
        return getMultipleResult(query);
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = super.getEntityManager().find(Order.class, id);
        return order != null ? Optional.of(order) : Optional.empty();
    }

    @Override
    public Optional<Order> saveOrder(Order order) {
        save(order);
        return Optional.of(order);
    }

    @Override
    public Optional<Order> updateOrder(Order order) {
        update(order);
        return Optional.of(order);
    }

    @Override
    public List<Double> orderRevenuesForEachCategory() {
        String query = "SELECT SUM(c.total) FROM OrderItem c where 1=1 group by  c.product.category";
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findAllRevenues() {
        String query = "SELECT c.date,SUM(c.total) FROM Order c where 1=1 group by c.date ";
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(Long id) {
        String query = "SELECT c FROM OrderItem c WHERE c.order.id=" + id + "";
        return getEntityManager().createQuery(query).getResultList();
    }

}
