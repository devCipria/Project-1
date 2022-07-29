package com.davidcipriati.web;

import com.davidcipriati.repository.UserDAOImpl;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class ContextListener implements ServletContextListener {
    @Resource(name="jdbc/ersDB")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        UserDAOImpl userRepo = new UserDAOImpl(dataSource);

        servletContextEvent.getServletContext().setAttribute("userRepo", userRepo);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
