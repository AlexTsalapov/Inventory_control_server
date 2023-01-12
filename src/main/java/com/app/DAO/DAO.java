package com.app.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public abstract class DAO {
    protected SessionFactory sessionFactory;

    public DAO() {
        sessionFactory= sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    public <T> boolean create(T object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
        return true;
    }


    public <T> T read(T object,int id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        T tempObject = (T) session.get(object.getClass(),id);
        transaction.commit();
        session.close();
        return tempObject;
    }

    public <T> ArrayList<T> findAll(T object) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        ArrayList list = (ArrayList) (session.createQuery(String.format("FROM %s", object.getClass().getSimpleName())).list());
        transaction.commit();
        session.close();
        return list;
    }



}
