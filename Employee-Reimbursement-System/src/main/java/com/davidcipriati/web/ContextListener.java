package com.davidcipriati.web;

import com.davidcipriati.repository.ReimbursementRepository;
import com.davidcipriati.repository.UserRepository;
import com.davidcipriati.services.ReimbursementService;
import com.davidcipriati.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    private static Logger log = LogManager.getLogger(ContextListener.class.getName());


    @Resource(name="jdbc/ers")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Context Listener initialized");
        try {
            Connection c = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserService userService = new UserService(new UserRepository(dataSource));
        servletContextEvent.getServletContext().setAttribute("userService", userService);

        ReimbursementService reimbursementService = new ReimbursementService(new ReimbursementRepository(dataSource));
        servletContextEvent.getServletContext().setAttribute("reimbursementService", reimbursementService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Context Listener destoryed");
    }
}
