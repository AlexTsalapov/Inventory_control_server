package com.app.service;

import com.app.DAO.CategoryDAO;
import com.app.model.Category;
import com.app.model.Storage;

import java.util.ArrayList;

public class CategoryService {
    CategoryDAO categoryDAO = new CategoryDAO();
    Category category = new Category();

    public ArrayList<Category> getMany(Storage storage) {
        return (ArrayList<Category>) categoryDAO.getMany(storage.getId());
    }
    public boolean delete(Category category) {

        return categoryDAO.delete(category);
    }

    public String add(Category category) {
        if (chekCategory(category)) {
            return categoryDAO.create(category) + "";
        }
        return "false";
    }

    public boolean chekCategory(Category category) {
        ArrayList<Category> list = (ArrayList<Category>) categoryDAO.getMany(category.getStorageId());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(category.getName())) {
                this.category = list.get(i);
                return false;
            }
        }
        return true;
    }
    public boolean update(Category category) {

        return categoryDAO.update(category);
    }
}
