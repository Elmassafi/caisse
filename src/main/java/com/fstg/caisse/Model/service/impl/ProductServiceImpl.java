package com.fstg.caisse.Model.service.impl;

import com.fstg.caisse.Model.bean.Category;
import com.fstg.caisse.Model.bean.Product;
import com.fstg.caisse.Model.dal.ProductDao;
import com.fstg.caisse.Model.dal.impl.DbProductDao;
import com.fstg.caisse.Model.service.CategoryService;
import com.fstg.caisse.Model.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService = new CategoryServiceImpl();
    private final ProductDao productDao = new DbProductDao();

    @Override
    public int saveProduct(Product product, String categoryName) {
        if (product == null) {
            return -1;
        }
        Product p = findByName(product.getName());
        if (p != null) {
            return -2;
        } else {
            Category category = categoryService.findByName(categoryName);
            if (category == null) {
                return -2;
            }
            product.setCategory(category);
            productDao.saveProduct(product);
            return 1;
        }
    }

    @Override
    public Product findByName(String productName) {
        return productDao.findByName(productName);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return productDao.findByCategoryName(categoryName);
    }

    @Override
    public Product updateProduct(Long id, String productName, Double priceUpdate, String categoryName) {
        Product product = findByName(productName);
        if (product == null) {
            product = findById(id);
            if (product != null) product.setName(productName);
            else return null;
        }
        if (priceUpdate != null && priceUpdate > 0) {
            product.setPrice(priceUpdate);
        }
        if (!product.getCategory().getName().equals(categoryName)) {
            Category category = categoryService.findByName(categoryName);
            if (category != null) product.setCategory(category);
        }
        return productDao.updateProduct(product);
    }


    private Product findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public int deleteProduct(Product product) {
        if (product == null) {
            return -1;
        }
        Product p = findById(product.getId());
        if (p == null) {
            p = findByName(product.getName());
            if (p == null) {
                return -2;
            }
        }
        p.setActive(false);
        productDao.updateProduct(p);
        return 1;
    }

}
