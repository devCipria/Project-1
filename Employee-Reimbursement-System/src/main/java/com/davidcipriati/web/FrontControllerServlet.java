package com.davidcipriati.web;

import com.davidcipriati.service.ReimbursementService;
import com.davidcipriati.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ers/*")
public class FrontControllerServlet extends HttpServlet {
    // put 1 ObjectMapper in ContextListener
    private ObjectMapper om;
    private UserService userService;
    private ReimbursementService reimbursementService;
    private ManagerController managerController;
    private LoginController loginController;
    private UserController userController;

    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Servlet");
        om = new ObjectMapper();
        userService = (UserService) getServletContext().getAttribute("userService");
        reimbursementService = (ReimbursementService) getServletContext().getAttribute("reimbursementService");
        managerController = new ManagerController(userService, reimbursementService);
        userController = new UserController(userService, reimbursementService);
        loginController = new LoginController(userService);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URI = request.getRequestURI();
        System.out.println(URI);
        System.out.println(request.getMethod());

        switch (URI) {
            case "/ers/login":
                if (request.getMethod().equals("POST")) {
                    loginController.login(request, response);
                }
                break;
            case "/ers/manager/employees":
                // pass to ManagerController
                if (request.getMethod().equals("GET")) {
                    managerController.showEmployeeList(request, response);
                }
                break;
            case "/ers/manager/requests/pending":
                if (request.getMethod().equals("GET")) {
                    managerController.showAllPendingFromAllEmployees(request, response);
                }
                break;
            case "/ers/manager/requests/resolved":
                if (request.getMethod().equals("GET")) {
                    managerController.showAllResolvedFromAllEmployees(request, response);
                }
                break;
            case "/ers/employee/profile":
                if (request.getMethod().equals("GET")) {
                    userController.showProfile(request, response);
                }
                break;
            case "/ers/employee/profile/update":
                if (request.getMethod().equals("PUT")) {
                    userController.editEmployeeProfile(request, response);
                }
                break;
            case "/ers/employee/requests/submit":
                if (request.getMethod().equals("POST")) {
                    userController.submitRequest(request, response);
                }
                break;
            case "/ers/employee/requests/pending":
                if (request.getMethod().equals("GET")) {
                    userController.showPendingRequestsByUserId(request, response);
                }
                break;
            case "/ers/employee/requests/resolved":
                if (request.getMethod().equals("GET")) {
                    userController.showResolvedRequestsByUserId(request, response);
                }
                break;
            case "/ers/logout":
                loginController.logout(request, response);
        }
    }
}
