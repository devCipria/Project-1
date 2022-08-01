package com.davidcipriati.repository;

import com.davidcipriati.model.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAllUsers(); // should it be findAllEmployees ?
//    User findUserById(int id);
    User findUserByUsername(String username);
    boolean updateUser(User user);
}
