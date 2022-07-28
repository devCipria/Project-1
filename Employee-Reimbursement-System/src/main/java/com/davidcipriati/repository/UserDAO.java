package com.davidcipriati.repository;

import com.davidcipriati.model.User;

import java.util.List;

public interface UserDAO {
    List<User> findAllUsers(); // should it be findAllEmployees ?
//    User findUserById(int id);
    User findUserByUsername(String username);
    int updateById(int id);
}
