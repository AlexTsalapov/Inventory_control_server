package com.app.service;

import com.app.DAO.StorageDAO;
import com.app.model.Product;
import com.app.model.Storage;

import java.util.ArrayList;

public class StorageService {
    Storage storage;
    StorageDAO storageDAO;

    public StorageService() {
        storageDAO = new StorageDAO();
    }

    public String add(Storage storage) {
        if (chekStorage(storage)) {
            storageDAO.create(storage);
            return "true";
        }
        return "false";

    }

    public boolean delete(Storage storage) {

        return storageDAO.delete(storage);
    }
    public boolean update(Storage storage) {

        return storageDAO.update(storage);
    }

    public boolean chekStorage(Storage storage) {
        ArrayList<Storage> list = storageDAO.findAll(storage);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(storage.getName())) {
                this.storage = list.get(i);
                return false;
            }
        }
        return true;
    }
    public ArrayList<Product> getMany(Storage storage)
    {
        return (ArrayList<Product>) storageDAO.getMany(storage);
    }
}
