package com.davidcipriati.web;

import com.davidcipriati.repository.ReimbursementRepository;
import com.davidcipriati.repository.UserRepository;
import com.davidcipriati.service.ReimbursementService;
import com.davidcipriati.service.UserService;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    @Resource(name="jdbc/ersDB")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        ServletContext context = servletContextEvent.getServletContext();
        try {
            Connection c = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserService userService = new UserService(new UserRepository(dataSource));
        servletContextEvent.getServletContext().setAttribute("userService", userService);

        ReimbursementService reimbursementService = new ReimbursementService(new ReimbursementRepository(dataSource));
        servletContextEvent.getServletContext().setAttribute("reimbursementService", reimbursementService);


//        UserRepository userRepository = new UserRepository(dataSource);
//
//        servletContextEvent.getServletContext().setAttribute("userRepository", userRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
