package com.davidcipriati.service;

import com.davidcipriati.model.User;
import com.davidcipriati.repository.UserDAO;
import com.davidcipriati.repository.UserDAOImpl;

import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAOImpl) {
        this.userDAO = userDAOImpl;
    }

    public List<User> getAllEmployees() {
        return userDAO.findAllUsers();
    }
}
