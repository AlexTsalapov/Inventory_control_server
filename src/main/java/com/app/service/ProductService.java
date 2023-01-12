package com.app.service;

import com.app.DAO.ProductDAO;
import com.app.model.Product;

public class ProductService {
    ProductDAO productDAO=new ProductDAO();
    public String add(Product product) {

           return productDAO.create(product)+"";
    }
    public boolean delete(Product product) {

        return productDAO.delete(product);
    }
    public boolean update(Product product) {

        return productDAO.update(product);
    }
}
