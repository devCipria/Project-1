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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerController {
    private UserService userService;
    private ReimbursementService reimbursementService;
    private ObjectMapper objectMapper;


    public ManagerController(UserService userService, ReimbursementService reimbursementService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        this.objectMapper = objectMapper;
    }

    public int showEmployeeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // replace System.out.println with log4j
        System.out.println("Inside the showEmployeeList in ManagerController.java");
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            List<User> employeeList = userService.getAllEmployees();
            for (User user : employeeList) {
                user.setPassword("********************");
            }

            response.getWriter().write(objectMapper.writeValueAsString(employeeList));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(403);
            return response.getStatus();
        }
    }

    public int showAllPendingFromAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside the showAllPendingFromAllEmployees in ManagerController.java");
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            List<Reimbursement> pendingList = reimbursementService.getAllPendingReimbursements();
            response.getWriter().write(objectMapper.writeValueAsString(pendingList));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(403);
            return response.getStatus();
        }
    }

    public int showAllResolvedFromAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside the showAllResolvedFromAllEmployees in ManagerController.java");
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            List<Reimbursement> resolvedList = reimbursementService.getAllResolvedReimbursements();
            response.getWriter().write(objectMapper.writeValueAsString(resolvedList));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(403);
            return response.getStatus();
        }
    }

    public int showAllRequestsForOneEmployee(HttpServletRequest request, HttpServletResponse response, int id) throws IOException {
        // error handling the id
        // validate that the user is a manager
        // verify that the user has logged in -- or not; maybe the first validation covers it.
        System.out.println("Inside the showAllRequestsForOneEmployee in ManagerController.java");
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            List<Reimbursement> requestList = reimbursementService.getAllRequestByUserId(id);
            response.getWriter().write(objectMapper.writeValueAsString(requestList));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(403);
            return response.getStatus();
        }
    }


    public int approveReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside approveReimbursement in ManagerController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            User manager = (User) session.getAttribute("validatedUser");
            System.out.println("inside the if statement of approveReimbursement");
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            int id = Integer.parseInt(requestBody.replaceAll("[^0-9]", ""));
            System.out.println(id);

            Reimbursement reimbursement = reimbursementService.getByReimbursementId(id);
            reimbursement.setResolverId(manager.getUserId());
            reimbursement.setStatus("Resolved");
            reimbursement.setOutcome("Approved");

            // make sure it returns true
            reimbursementService.resolveReimbursement(reimbursement);

            reimbursement = reimbursementService.getByReimbursementId(id);

            response.getWriter().write(objectMapper.writeValueAsString(reimbursement));
            response.setStatus(200);
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(403);
            return response.getStatus();
        }
    }

    public int denyReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside denyReimbursement in ManagerController");
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            User manager = (User) session.getAttribute("validatedUser");
            System.out.println("inside the if statement of approveReimbursement");
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            int id = Integer.parseInt(requestBody.replaceAll("[^0-9]", ""));
            System.out.println(id);

            Reimbursement reimbursement = reimbursementService.getByReimbursementId(id);
            reimbursement.setResolverId(manager.getUserId());
            reimbursement.setStatus("Resolved");
            reimbursement.setOutcome("Denied");

            // make sure it returns true
            reimbursementService.resolveReimbursement(reimbursement);

            reimbursement = reimbursementService.getByReimbursementId(id);

            response.getWriter().write(objectMapper.writeValueAsString(reimbursement));
            response.setStatus(200);
            return response.getStatus();

        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(403);
            return response.getStatus();
        }
    }
    private String unauthorizedErrorMessage() {
        return "{  \n" +
                "   \"error\": \""+ "unauthorized-action" +"\",\n" +
                "   \"message\": \""+ "You must be logged in as a Manager to complete this action\"" +
                "}";
    }
}
