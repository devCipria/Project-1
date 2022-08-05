package com.davidcipriati.web;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.model.User;
import com.davidcipriati.services.ReimbursementService;
import com.davidcipriati.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides the functionality of the application to managers. Displays the desired results to the user.
 * The FrontControllerServlet routes the URI to the matching method in this class.
 */
public class ManagerController {
    private UserService userService;
    private ReimbursementService reimbursementService;
    private ObjectMapper objectMapper;
    private static Logger log = LogManager.getLogger(ManagerController.class.getName());


    public ManagerController(UserService userService, ReimbursementService reimbursementService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        this.objectMapper = objectMapper;
    }

    /**
     * Displays to a manager, a list of all the employees.
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int showEmployeeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            log.info("A manager has is getting the list of employees");
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

    /**
     * Displays to a manager, a list of all pending reimbursement requests for all employees
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int showAllPendingFromAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            log.info("A manager has accessed all pending requests from all employees");
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

    /**
     * Displays to a manager, a list of all resolved reimbursement requests for all employees
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int showAllResolvedFromAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            log.info("A manager has accessed all resolved requests from all employees");
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

    /**
     * Displays a list of all requests, pending and resolved, for a single employee
     * @param request
     * @param response
     * @param id -- the user id of the employee whose requests are being displayed.
     * @return
     * @throws IOException
     */
    public int showAllRequestsForOneEmployee(HttpServletRequest request, HttpServletResponse response, int id) throws IOException {
        response.setContentType("/application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            log.info("A manager has accessed all requests from one employee");
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


    /**
     * Provides a manager the ability to approve a request
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int approveReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            User manager = (User) session.getAttribute("validatedUser");
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            int id = Integer.parseInt(requestBody.replaceAll("[^0-9]", ""));

            Reimbursement reimbursement = reimbursementService.getByReimbursementId(id);
            reimbursement.setResolverId(manager.getUserId());
            reimbursement.setStatus("Resolved");
            reimbursement.setOutcome("Approved");

            // make sure it returns true
            reimbursementService.resolveReimbursement(reimbursement);

            reimbursement = reimbursementService.getByReimbursementId(id);

            response.getWriter().write(objectMapper.writeValueAsString(reimbursement));
            response.setStatus(200);
            log.info("Manager: " + manager.getUsername() + " has approved a reimbursement request");

            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(403);
            return response.getStatus();
        }
    }

    /**
     * Provides a manager the ability to deny a reimbursement request
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int denyReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);

        if (session != null && (Boolean) session.getAttribute("isManager")) {
            User manager = (User) session.getAttribute("validatedUser");
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            int id = Integer.parseInt(requestBody.replaceAll("[^0-9]", ""));

            Reimbursement reimbursement = reimbursementService.getByReimbursementId(id);
            reimbursement.setResolverId(manager.getUserId());
            reimbursement.setStatus("Resolved");
            reimbursement.setOutcome("Denied");

            reimbursementService.resolveReimbursement(reimbursement);

            reimbursement = reimbursementService.getByReimbursementId(id);

            response.getWriter().write(objectMapper.writeValueAsString(reimbursement));
            response.setStatus(200);
            log.info("Manager: " + manager.getUsername() + " has approved a reimbursement request");

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
