package com.fstg.caisse.Model.service;

import com.fstg.caisse.Model.bean.Product;

import java.util.List;

public interface ProductService {
    int saveProduct(Product product, String categoryName);

    Product findByName(String productName);

    List<Product> findAll();

    List<Product> findByCategoryName(String categoryName);

    Product updateProduct(Long id, String productName, Double priceUpdate, String categoryName);

    int deleteProduct(Product product);
}
