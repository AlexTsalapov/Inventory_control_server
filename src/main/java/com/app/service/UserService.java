package com.app.service;

import com.app.model.Storage;
import com.app.DAO.UserDAO;
import com.app.model.User;

import java.util.ArrayList;

public class UserService {

    User user;
    UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }


    public String add(User user) {
        if (chekAccount(user))
        {
            userDAO.create(user);
            return "true";
        }
        return "false";

    }
public ArrayList<Storage> getMany(User user)
{
    return (ArrayList<Storage>) userDAO.getMany(user);
}
    public User checkAcount(User user) {
        if(!chekAccount(user))
        {
            return userDAO.read(this.user,this.user.getId());
        }
        return null;
    }

    public boolean chekAccount(User user) {
        ArrayList<User> list = userDAO.findAll(user);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLogin().equals(user.getLogin()) && list.get(i).getPassword().equals(user.getPassword())) {
                this.user=list.get(i);
                return false;
            }
        }
        return true;
    }

}
