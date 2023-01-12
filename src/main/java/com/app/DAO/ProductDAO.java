package com.app.DAO;

import com.app.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductDAO extends DAO{


    public boolean delete(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(product);
        session.getTransaction().commit();
        session.close();
        return true;

    }
    public <T> boolean update( T object) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        session.close();
        return true;
    }

}
