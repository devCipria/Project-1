package com.davidcipriati.web;

import com.davidcipriati.model.User;
import com.davidcipriati.service.ReimbursementService;
import com.davidcipriati.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserController {
    private UserService userService;
    private ReimbursementService reimbursementService;
    private ObjectMapper objectMapper;

    public UserController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        this.objectMapper = new ObjectMapper();
    }

    public void showProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the showProfile method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            response.getWriter().write(objectMapper.writeValueAsString(user));
            response.setStatus(200);
        }
    }
}
