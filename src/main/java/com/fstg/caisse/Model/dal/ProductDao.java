package com.fstg.caisse.Model.dal;

import com.fstg.caisse.Model.bean.Product;

import java.util.List;

public interface ProductDao {
    Product saveProduct(Product product);

    Product findByName(String productName);

    List<Product> findAll();

    List<Product> findByCategoryName(String categoryName);

    Product updateProduct(Product product);

    Product findById(Long id);
}
