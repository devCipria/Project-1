package com.davidcipriati.web;

import com.davidcipriati.model.User;
import com.davidcipriati.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Handles the logging in and logging out of an employee or manager
 */
public class LoginController {
    private UserService userService;
    private ObjectMapper objectMapper;
    private static Logger log = LogManager.getLogger(LoginController.class.getName());


    /**
     * Constructor for LoginController
     * @param userService
     */
    public LoginController(UserService userService) {
        this.userService = userService;
        objectMapper = new ObjectMapper();
    }

    /**
     * Logs the user in. Sets a HttpSession attribute to the validated user
     * @param request
     * @param response
     * @throws IOException
     */
    public void login (HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("User attempting to log in");

        System.out.println("Inside login method in the Login Controller");
        response.setContentType("/application/json");

        // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User tempUser = objectMapper.readValue(requestBody, User.class);
        String username = tempUser.getUsername();
        String password = tempUser.getPassword();

        if (userService.validateUser(username, password)) {
            User user = userService.getUserByUsername(username);
            HttpSession session = request.getSession();
            session.setAttribute("validatedUser", user);
            session.setAttribute("isManager", user.getRole().equals("manager") ? true : false);
            // send back user profile -- but don't include password -- set up a special object: LoginResponse
            user.setPassword("********************");
            response.getWriter().write(objectMapper.writeValueAsString(user));
            response.setStatus(200);
            log.info(user.getUsername() + " successfully logged in");
        } else {
            log.info("User failed to log in. Incorrect username or password");

            // Source -> https://stackoverflow.com/questions/35586643/how-to-add-error-message-to-response-body
            String json = "{  \n" +
                    "   \"error\": \""+ "incorrect-user-pass" +"\",\n" +
                    "   \"message\": \""+ "Incorrect username and password" +"\",\n" +
                    "   \"detail\": \"Ensure that the username and password included in the request are correct\",\n" +
                    "}";
            response.getOutputStream().println(json);
            response.setStatus(401);
        }
    }

    /**
     * Logs the user out of the application.
     * @param request
     * @param response
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("User logged out");
        response.getWriter().write("You are now logged out");

        request.getSession().setAttribute("validatedUser", null);
        request.getSession().invalidate();
        response.setStatus(200);
    }
}
