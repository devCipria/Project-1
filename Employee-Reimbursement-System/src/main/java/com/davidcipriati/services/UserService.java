package com.davidcipriati.services;

import com.davidcipriati.model.User;
import com.davidcipriati.repository.IUserRepository;

import java.util.List;

/**
 * Class that provides the user services to help the controllers
 */
public class UserService {
    private IUserRepository repo;

    /**
     * Userservice constructor
     */
    public UserService(IUserRepository userRepository) {
        this.repo = userRepository;
    }

    /**
     * Retrieves all the employees in the database
     * @return
     */
    public List<User> getAllEmployees() {
        return repo.findAllUsers();
    }

    /**
     * Checks the credentials of the user attempting to log in to the application
     * @param username username from the request body
     * @param password password from the request body
     * @return true if the user is in the database. False if they are not in the database.
     */
    public boolean validateUser(String username, String password) {
        User user = repo.findUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a user by their username
     * @param username
     * @return the user if found
     */
    public User getUserByUsername(String username) {
        return repo.findUserByUsername(username);
    }

    /**
     * Passes the edited user to the database to update the record
     * @param user who wants to edit their record
     * @return true if the user was edited
     */
    public boolean editUserProfile(User user) {
        return repo.updateUser(user);
    }
}
