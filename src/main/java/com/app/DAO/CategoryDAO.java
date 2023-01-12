package com.app.DAO;

import com.app.model.Category;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DAO{
    public List<Category> getMany(int id)
    {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        ArrayList list = (ArrayList) (session.createQuery(String.format("FROM Category WHERE storageId = "+id)).list());
        return list;
    }

    public boolean delete(Category category) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Category> list=getMany(category.getStorageId());
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(category.getName()))
            {
                category=list.get(i);
            }
        }
        session.remove(category);
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
