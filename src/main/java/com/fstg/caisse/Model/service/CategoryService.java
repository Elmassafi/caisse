package com.fstg.caisse.Model.service;

import com.fstg.caisse.Model.bean.Category;

import java.util.List;

public interface CategoryService {

    boolean isCategoryExist(String categoryName);

    Category saveCategory(Category category);

    Category findByName(String categoryName);

    List<String> findAllCategoriesName();

    List<Category> findAll();

}
