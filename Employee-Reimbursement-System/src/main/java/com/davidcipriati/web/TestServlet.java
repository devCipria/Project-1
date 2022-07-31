//package com.davidcipriati.web;
//
//import com.davidcipriati.model.User;
//import com.davidcipriati.repository.IUserRepository;
//import com.davidcipriati.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//@WebServlet("/test")
//public class TestServlet extends HttpServlet {
//
//    private ObjectMapper om;
////    private IUserRepository userRepository;
//    private UserService userService;
//
//    @Override
//    public void init() throws ServletException {
//        System.out.println("Initializing Servlet");
//        om = new ObjectMapper();
////        userRepository = (IUserRepository) getServletContext().getAttribute("userRepository");
//        userService = (UserService) getServletContext().getAttribute("userService");
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        List<User> userList = userRepository.findAllUsers();
//        List<User> employeeList = userService.getAllEmployees();
//        resp.setContentType("application/json");
//        resp.getWriter().write(om.writeValueAsString(employeeList));
//        resp.setStatus(200);
//    }
//}
