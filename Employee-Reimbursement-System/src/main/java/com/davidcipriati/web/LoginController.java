package com.davidcipriati.web;

import com.davidcipriati.model.User;
import com.davidcipriati.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginController {
    private UserService userService;
    private ObjectMapper objectMapper;

    public LoginController(UserService userService) {
        this.userService = userService;
        objectMapper = new ObjectMapper();
    }

    public void login (HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside login method in the Login Controller");
        response.setContentType("/application/json");


        // Source --> https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User tempUser = new ObjectMapper().readValue(requestBody, User.class);
        String username = tempUser.getUsername();
        String password = tempUser.getPassword();

        if (userService.validateUser(username, password)) {
            User user = userService.getUserByUsername(username);
            HttpSession session = request.getSession();
            session.setAttribute("validatedUser", user);
            session.setAttribute("isManager", user.getRole().equals("manager") ? true : false);
            // send back user profile -- but don't include password -- set up a special object: LoginResponse
            response.getWriter().write(objectMapper.writeValueAsString(user));
            response.setStatus(200);
        } else {
            // Source -> https://stackoverflow.com/questions/35586643/how-to-add-error-message-to-response-body
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = "{  \n" +
                    "   \"error\": \""+ "incorrect-user-pass" +"\",\n" +
                    "   \"message\": \""+ "Incorrect username and password" +"\",\n" +
                    "   \"detail\": \"Ensure that the username and password included in the request are correct\",\n" +
                    "}";
            response.getOutputStream().println(json);
            response.setStatus(401); // or is it 403. It's 401. 403 would be useful when employee tries to do Manager actions

        }
    }
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Send the user a logout message
        request.getSession().setAttribute("validatedUser", null);
        request.getSession().invalidate();
        response.setStatus(200);
    }
}
