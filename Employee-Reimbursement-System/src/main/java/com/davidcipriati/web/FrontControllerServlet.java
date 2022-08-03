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

@WebServlet(urlPatterns = {"/ers/*", "/ers/manager/requests/employee/*"})
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
//        System.out.println("getContextPath(): " + request.getContextPath());
        System.out.println(request.getMethod());
        System.out.println("getPathInfo(): " + request.getPathInfo());
        String pathVar = request.getPathInfo();

        // if URI contains /ers/manager/requests/employee/

        if (URI.contains("/ers/manager/requests/employee/") && pathVar !=  null) {
            System.out.println("****** Inside the IF Statemtn ******");
            URI = request.getRequestURI().replace(pathVar, "");
            pathVar = pathVar.replace("/", "");
            System.out.println(pathVar);
            System.out.println(URI);
        }


        // if url contains a path parameter, strip it out.
        // put the path parameter in an id variable.

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
            case "/ers/manager/requests/employee":
                System.out.println("hello, hello, hello");
                if (request.getMethod().equals("GET")) {
                    try {
                        System.out.println("Front Controller::: id = " + pathVar);
                        managerController.showAllRequestsForOneEmployee(request, response, Integer.parseInt(pathVar));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/ers/manager/requests/approve":
                if (request.getMethod().equals("PUT")) {
                    managerController.approveReimbursement(request, response);
                }
                break;
            case "/ers/manager/requests/deny":
                if (request.getMethod().equals("PUT")) {
                    managerController.denyReimbursement(request, response);
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
