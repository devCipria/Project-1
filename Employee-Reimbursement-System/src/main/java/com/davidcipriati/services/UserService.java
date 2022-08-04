package com.davidcipriati.services;

import com.davidcipriati.model.User;
import com.davidcipriati.repository.IUserRepository;

import java.util.List;

public class UserService {
    private IUserRepository repo;

    public UserService(IUserRepository userRepository) {
        this.repo = userRepository;
    }

    public List<User> getAllEmployees() {
        return repo.findAllUsers();
    }

    public boolean validateUser(String username, String password) {
        User user = repo.findUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return repo.findUserByUsername(username);
    }

    public boolean editUserProfile(User user) {
        return repo.updateUser(user);
    }
}
