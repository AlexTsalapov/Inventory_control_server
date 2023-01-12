package com.app.DAO;

import com.app.model.Product;
import com.app.model.Storage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StorageDAO extends DAO {
    public boolean delete(Storage storage) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(storage);
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
    public List<Product> getMany(Storage object)
    {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        ArrayList list = (ArrayList) (session.createQuery(String.format("FROM Product WHERE storageId = "+object.getId())).list());
        return list;
    }
}
