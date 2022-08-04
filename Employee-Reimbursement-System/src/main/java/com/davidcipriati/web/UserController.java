package com.davidcipriati.web;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.model.User;
import com.davidcipriati.services.ReimbursementService;
import com.davidcipriati.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    private UserService userService;
    private ReimbursementService reimbursementService;
    private ObjectMapper objectMapper;

    public UserController(UserService userService, ReimbursementService reimbursementService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        this.objectMapper = objectMapper;
    }

    public int showProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the showProfile method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            user = userService.getUserByUsername(user.getUsername());
            response.getWriter().write(objectMapper.writeValueAsString(user));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
            return response.getStatus();
        }
    }

    public int showPendingRequestsByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the showPendingRequestsByUserId method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            List<Reimbursement> pendingList = reimbursementService.getAllPendingByUserId(user.getUserId());
            response.getWriter().write(objectMapper.writeValueAsString(pendingList));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            return response.getStatus();
        }
    }

    public int showResolvedRequestsByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the showResolvedRequestsByUserId method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            List<Reimbursement> resolvedList = reimbursementService.getAllResolvedByUserId(user.getUserId()); // what if user has no pending reimbursements ?
            response.getWriter().write(objectMapper.writeValueAsString(resolvedList));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
            return response.getStatus();
        }
    }

    public void submitRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the submitReques method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Reimbursement reimbursement = new ObjectMapper().readValue(requestBody, Reimbursement.class);

            int reimbId = reimbursementService.createReimbursementRequest(reimbursement);
            reimbursement = reimbursementService.getByReimbursementId(reimbId);

            response.getWriter().write(objectMapper.writeValueAsString(reimbursement));
            response.setStatus(200);
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
        }
    }

    public void editEmployeeProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the editeEmployeeProfile method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            User user = new ObjectMapper().readValue(requestBody, User.class);

            userService.editUserProfile(user);
            user = userService.getUserByUsername(user.getUsername());

            response.getWriter().write(objectMapper.writeValueAsString(user));
            response.setStatus(200);
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
        }

    }

    public String unauthorizedErrorMessage() {
        return "{  \n" +
                "   \"error\": \""+ "unauthorized-action" +"\",\n" +
                "   \"message\": \""+ "You must be logged in to the system to complete this action\"" +
                "}";
    }


}
