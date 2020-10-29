package com.fstg.caisse.Model.service.impl;

import com.fstg.caisse.Model.bean.Category;
import com.fstg.caisse.Model.dal.CategoryDao;
import com.fstg.caisse.Model.dal.impl.DbCategoryDao;
import com.fstg.caisse.Model.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {


    private final CategoryDao categoryDao = new DbCategoryDao();

    public boolean isCategoryExist(String categoryName) {
        Category c = findByName(categoryName);
        return c != null;
    }

    public Category saveCategory(Category category) {
        if (category != null) {
            if (!isCategoryExist(category.getName())) {
                categoryDao.saveCategory(category);
                return category;
            }
        }
        return null;
    }

    public Category findByName(String categoryName) {
        Optional<Category> category = categoryDao.findByName(categoryName);
        return category.orElse(null);
    }

    public List<String> findAllCategoriesName() {
        return this.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

}
