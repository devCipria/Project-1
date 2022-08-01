package com.davidcipriati.web;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.model.User;
import com.davidcipriati.service.ReimbursementService;
import com.davidcipriati.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
    private ObjectWriter ow;

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
            user = userService.getUserByUsername(user.getUsername());
            response.getWriter().write(objectMapper.writeValueAsString(user));
            response.setStatus(200);
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage("   \"detail\": \"You must log in to the system to view your profile\",\n"));
            response.setStatus(401);
        }
    }

    public void showPendingRequestsByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the showPendingRequestsByUserId method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            List<Reimbursement> pendingList = reimbursementService.getAllPendingByUserId(user.getUserId()); // what if user has no pending reimbursements ?
            if (pendingList != null) {
                response.getWriter().write(objectMapper.writeValueAsString(pendingList));
                response.setStatus(200);
            } else {
                ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = "{  \n" +
                        "   \"message\": \"" + user.getUsername() + " does not have any Pending Requests" + "\",\n" +
                        "}";
                response.getOutputStream().println(json);
                response.setStatus(200);
            }
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage("   \"detail\": \"You must log in to the system to view Pending Requests\",\n"));
            response.setStatus(401);
        }
    }

    public void showResolvedRequestsByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside the showResolvedRequestsByUserId method of UserController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            List<Reimbursement> resolvedList = reimbursementService.getAllResolvedByUserId(user.getUserId()); // what if user has no pending reimbursements ?
            if (resolvedList != null) {
                response.getWriter().write(objectMapper.writeValueAsString(resolvedList));
                response.setStatus(200);
            } else {
                ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = "{  \n" +
                        "   \"message\": \"" + user.getUsername() + " does not have any Resolved Requests" + "\",\n" +
                        "}";
                response.getOutputStream().println(json);
                response.setStatus(200);
            }
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage("   \"detail\": \"You must log in to the system to view Resolved Requests\",\n"));
            response.setStatus(401);
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
            response.getOutputStream().println(unauthorizedErrorMessage("   \"detail\": \"You must log in to the system to Submit a Reimbursement Request\",\n"));
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
            response.getOutputStream().println(unauthorizedErrorMessage("   \"detail\": \"You must log in to the system to Edit your Profile\",\n"));
            response.setStatus(401);
        }

    }

    private String unauthorizedErrorMessage(String detail) {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return "{  \n" +
                "   \"error\": \""+ "unauthorized-action" +"\",\n" +
                "   \"message\": \""+ "User not logged in " +"\",\n" + detail +
                "}";
    }


}
