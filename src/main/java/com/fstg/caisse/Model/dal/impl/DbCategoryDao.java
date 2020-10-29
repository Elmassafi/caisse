package com.fstg.caisse.Model.dal.impl;

import com.fstg.caisse.Model.bean.Category;
import com.fstg.caisse.Model.dal.CategoryDao;
import com.fstg.caisse.Model.dal.JPAUtility;

import java.util.List;
import java.util.Optional;

public class DbCategoryDao extends JPAUtility<Category> implements CategoryDao {
    @Override
    public List<Category> findAll() {
        return getMultipleResult("SELECT c FROM Category c where 1=1");
    }

    @Override
    public Optional<Category> findById(Long id) {
        Category category = super.getEntityManager().find(Category.class, id);
        return category != null ? Optional.of(category) : Optional.empty();
    }

    @Override
    public List<Category> orderCategoriesByCategory() {
        String query = "SELECT DISTINCT c.product.category FROM OrderItem c where 1=1 group by  c.product.category";
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        Category result = null;
        if (categoryName != null) {
            String query = "SELECT c FROM Category c where c.name='" + categoryName.toLowerCase() + "'";
            result = getSingleResult(query);
        }
        return result != null ? Optional.of(result) : Optional.empty();
    }

    @Override
    public Optional<Category> saveCategory(Category category) {
        save(category);
        return category != null ? Optional.of(category) : Optional.empty();
    }

    @Override
    public Optional<Category> updateCategory(Category category) {
        return Optional.empty();
    }
}
