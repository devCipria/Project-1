package com.davidcipriati.services;

import com.davidcipriati.model.User;
import com.davidcipriati.repository.IUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserServiceTest {
    private UserService userService;
    private IUserRepository userRepository;

    User user;
    String username;
    String password;
    Integer id;

    @Before
    public void init() {
        userRepository = Mockito.mock(IUserRepository.class);
        userService = new UserService(userRepository);

        user = new User(1, "user_john", "pass123", "John", "Doe", "john@gmail.com", "employee");
        username = "user_john";
        password = "pass123";
        id = 1;
    }

    @Test
    public void verify_validateUser() {
        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(user);
        Assert.assertEquals(true, userService.validateUser(username, password));
    }

    @Test
    public void verify_getUserByUserName() {
        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(user);
        Assert.assertEquals(id, user.getUserId());
    }

    @Test
    public void verify_editUserProfile() {
        Mockito.when(userRepository.updateUser(user)).thenReturn(true);
        Assert.assertEquals(true, userService.editUserProfile(user));
    }

}
