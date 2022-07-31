package com.davidcipriati.web;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.model.User;
import com.davidcipriati.service.ReimbursementService;
import com.davidcipriati.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ManagerController {
    private UserService userService;
    private ReimbursementService reimbursementService;
    private ObjectMapper objectMapper;

    // pass object mapper
    public ManagerController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        objectMapper = new ObjectMapper();
    }

    public void showEmployeeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // replace sout with log4j
        System.out.println("Inside the showEmployeeList in ManagerController.java");
        response.setContentType("/application/json");
        List<User> employeeList = userService.getAllEmployees();
        response.getWriter().write(objectMapper.writeValueAsString(employeeList));
        response.setStatus(200);
    }

    public void showAllPendingFromAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside the showAllPendingFromAllEmployees in ManagerController.java");
        response.setContentType("/application/json");
        List<Reimbursement> pendingList = reimbursementService.getAllPendingReimbursements();
        response.getWriter().write(objectMapper.writeValueAsString(pendingList));
        response.setStatus(200);
    }

    public void showAllResolvedFromAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside the showAllResolvedFromAllEmployees in ManagerController.java");
        response.setContentType("/application/json");
        List<Reimbursement> resolvedList = reimbursementService.getAllResolvedReimbursements();
        response.getWriter().write(objectMapper.writeValueAsString(resolvedList));
        response.setStatus(200);
    }
}
