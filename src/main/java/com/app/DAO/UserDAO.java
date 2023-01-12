package com.app.DAO;

import com.app.model.Storage;
import com.app.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {

    public List<Storage> getMany(User object)
    {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        ArrayList list = (ArrayList) (session.createQuery(String.format("FROM Storage WHERE userId = "+object.getId())).list());
        return list;
    }

}
