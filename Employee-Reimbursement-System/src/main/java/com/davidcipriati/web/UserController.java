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
 * Provides the functionality of the application to employees. Displays the desired results to the user.
 * The FrontControllerServlet routes the URI to the matching method in this class.
 */
public class UserController {
    private UserService userService;
    private ReimbursementService reimbursementService;
    private ObjectMapper objectMapper;
    private static Logger log = LogManager.getLogger(UserController.class.getName());


    public UserController(UserService userService, ReimbursementService reimbursementService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
        this.objectMapper = objectMapper;
    }

    /**
     * Displays a logged in employee their profile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int showProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");

            response.getWriter().write(objectMapper.writeValueAsString(user));
            response.setStatus(200);

            log.info(user.getUsername() + " has viewed their profile");
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
            return response.getStatus();
        }
    }

    /**
     * Displays all the pending requests of the employee
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int showPendingRequestsByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            List<Reimbursement> pendingList = reimbursementService.getAllPendingByUserId(user.getUserId());
            response.getWriter().write(objectMapper.writeValueAsString(pendingList));
            response.setStatus(200);

            log.info(user.getUsername() + " has viewed their pending requests");

            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
            return response.getStatus();
        }
    }

    /**
     * Displays all the resolved requests of the employee
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int showResolvedRequestsByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("validatedUser");
            List<Reimbursement> resolvedList = reimbursementService.getAllResolvedByUserId(user.getUserId()); // what if user has no pending reimbursements ?
            response.getWriter().write(objectMapper.writeValueAsString(resolvedList));
            response.setStatus(200);
            log.info(user.getUsername() + " has viewed their resolved requests");

            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
            return response.getStatus();
        }
    }

    /**
     * Provides the employee the ability to submit a new reimbursement request
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int submitRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Reimbursement reimbursement = objectMapper.readValue(requestBody, Reimbursement.class);

            int reimbId = reimbursementService.createReimbursementRequest(reimbursement);
            reimbursement = reimbursementService.getByReimbursementId(reimbId);

            response.getWriter().write(objectMapper.writeValueAsString(reimbursement));
            response.setStatus(201);
            log.info("Employee with ID:" + reimbursement.getAuthorId() + " has submitted a reimbursement request");

            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
            return response.getStatus();
        }
    }

    /**
     * Provides the employee the ability to edit their profile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public int editEmployeeProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("/application/json");
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            User user = objectMapper.readValue(requestBody, User.class);

            userService.editUserProfile(user);
            user = userService.getUserByUsername(user.getUsername());

            response.getWriter().write(objectMapper.writeValueAsString(user));

            response.setStatus(200);
            log.info(user.getUsername() + " has edited their profile");
            return response.getStatus();
        } else {
            response.getOutputStream().println(unauthorizedErrorMessage());
            response.setStatus(401);
            return response.getStatus();
        }

    }

    public String unauthorizedErrorMessage() {
        return "{  \n" +
                "   \"error\": \""+ "unauthorized-action" +"\",\n" +
                "   \"message\": \""+ "You must be logged in to the system to complete this action\"" +
                "}";
    }


}
