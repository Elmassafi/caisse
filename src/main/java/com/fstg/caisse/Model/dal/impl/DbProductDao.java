package com.fstg.caisse.Model.dal.impl;

import com.fstg.caisse.Model.bean.Product;
import com.fstg.caisse.Model.dal.JPAUtility;
import com.fstg.caisse.Model.dal.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class DbProductDao extends JPAUtility<Product> implements ProductDao {

    @Override
    public Product saveProduct(Product product) {
        save(product);
        return product;
    }

    @Override
    public Product findByName(String productName) {
        if (productName != null && !productName.isEmpty()) {
            String query = "SELECT p FROM Product p WHERE p.name='" + productName + "'";
            getEntityManager().createQuery(query).getResultList();
            return getSingleResult(query);
        } else {
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        return getMultipleResult("SELECT p FROM Product p Where p.active=" + true + "");
    }


    @Override
    public List<Product> findByCategoryName(String categoryName) {
        if (categoryName != null && !categoryName.isEmpty()) {
            String query = "SELECT p FROM Product p WHERE p.category.name='" + categoryName + "' and p.active=" + true + "";
            return getEntityManager().createQuery(query).getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Product updateProduct(Product product) {
        update(product);
        return product;
    }


    @Override
    public Product findById(Long id) {
        return getEntityManager().find(Product.class, id);
    }

}
