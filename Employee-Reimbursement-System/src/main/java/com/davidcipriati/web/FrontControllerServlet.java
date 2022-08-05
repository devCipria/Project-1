package com.davidcipriati.web;

import com.davidcipriati.services.ReimbursementService;
import com.davidcipriati.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Exposes every endpoint in the application and routes the request to a controller, which when combined
 * with the methods in the services package, provides all the functionality of the app to the user
 */

@WebServlet(urlPatterns = {"/ers/*", "/ers/manager/requests/employee/*"})
public class FrontControllerServlet extends HttpServlet {

    private UserService userService;
    private ReimbursementService reimbursementService;
    private ManagerController managerController;
    private LoginController loginController;
    private UserController userController;
    private static Logger log;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        reimbursementService = (ReimbursementService) getServletContext().getAttribute("reimbursementService");
        managerController = new ManagerController(userService, reimbursementService, new ObjectMapper());
        userController = new UserController(userService, reimbursementService, new ObjectMapper());
        loginController = new LoginController(userService);
        log = LogManager.getLogger(FrontControllerServlet.class.getName());
        log.info("FrontControllerServlet init()");
    }

    /**
     * Routes the URI, provided by the user, to the appropriate Controller class
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URI = request.getRequestURI();
        log.info(request.getMethod() + "  " + URI);

        // Get path variable
        String pathVar = request.getPathInfo();
        if (URI.contains("/ers/manager/requests/employee/") && pathVar !=  null) {
            URI = request.getRequestURI().replace(pathVar, "");
            pathVar = pathVar.replace("/", "");
            System.out.println(pathVar);
            System.out.println(URI);
        }

        switch (URI) {
            case "/ers/login":
                if (request.getMethod().equals("POST")) {
                    loginController.login(request, response);
                }
                break;
            case "/ers/manager/employees":
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
                break;
            default:
                log.info("Client entered incorrect path");
                response.getWriter().write("No resource found at " + URI);
                response.setStatus(404);
        }
    }
}
