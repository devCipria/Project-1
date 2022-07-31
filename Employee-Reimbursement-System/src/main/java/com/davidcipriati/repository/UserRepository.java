package com.davidcipriati.repository;

import com.davidcipriati.model.User;
import com.davidcipriati.utils.ConnectionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> userList = new ArrayList<>();
//        try (Connection connection = ConnectionManager.getConnection()) {
//        try (Connection connection = dataSource.getConnection()) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select user_id, username, password, first_name, last_name, email, role from users";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("role")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }
        return userList;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select user_id, username, password, first_name, last_name, email, role from users where username=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

        return user;
    }

    @Override
    public int updateById(int id) {
        return 0;
    }
}
