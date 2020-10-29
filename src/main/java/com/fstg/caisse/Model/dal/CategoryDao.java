package com.fstg.caisse.Model.dal;

import com.fstg.caisse.Model.bean.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String CategoryName);

    Optional<Category> saveCategory(Category category);

    Optional<Category> updateCategory(Category category);

    List<Category> orderCategoriesByCategory();

}
