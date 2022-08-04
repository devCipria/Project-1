package com.davidcipriati.controller;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.model.User;
import com.davidcipriati.repository.IReimbursementRepository;
import com.davidcipriati.repository.IUserRepository;
import com.davidcipriati.services.ReimbursementService;
import com.davidcipriati.services.UserService;
import com.davidcipriati.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserService userService;
    private IUserRepository userRepository;

    private ReimbursementService reimbursementService;
    private IReimbursementRepository reimbursementRepository;
    private ObjectMapper objectMapper;
    private UserController userController;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @Before
    public void init() {
        userRepository = mock(IUserRepository.class);
        userService = new UserService(userRepository);

        reimbursementRepository = mock(IReimbursementRepository.class);
        reimbursementService = new ReimbursementService(reimbursementRepository);

        objectMapper = mock(ObjectMapper.class);

        userController = new UserController(userService, reimbursementService, objectMapper);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }


    @Test
    public void verify_showProfile() throws IOException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        HttpSession session = mock(HttpSession.class);

        User user = new User(1, "user_john", "pass123", "John", "Doe", "john@gmail.com", "employee");

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("validatedUser")).thenReturn(user);
        when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(user)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);

        Assert.assertEquals(200, userController.showProfile(request, response));
    }

    @Test
    public void verify_showPendingRequestsByUserId() throws IOException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        HttpSession session = mock(HttpSession.class);

        User user = new User(1, "user_john", "pass123", "John", "Doe", "john@gmail.com", "employee");
        List<Reimbursement> pendingReimbursements = new ArrayList<>();
        pendingReimbursements.add(new Reimbursement(1, 500.25f, "Hotel: Ramada", 1, 0, "Pending", null, "TRAVEL"));
        pendingReimbursements.add(new Reimbursement(1, 34.23f, "Gas", 1, 0, "Pending", null, "TRAVEL"));

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("validatedUser")).thenReturn(user);

        when(reimbursementService.getAllPendingByUserId(user.getUserId())).thenReturn(pendingReimbursements);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(pendingReimbursements)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);

        Assert.assertEquals(200, userController.showPendingRequestsByUserId(request, response));
    }

    @Test
    public void verify_showResolvedRequestsByUserId() throws IOException {
        User user = new User(1, "user_john", "pass123", "John", "Doe", "john@gmail.com", "employee");
        List<Reimbursement> resolvedReimbursements = new ArrayList<>();
        resolvedReimbursements.add(new Reimbursement(1, 500.25f, "Hotel: Ramada", 1, 2, "Resolved", "Approved", "TRAVEL"));
        resolvedReimbursements.add(new Reimbursement(1, 34.23f, "Gas", 1, 3, "Resolved", "Denied", "TRAVEL"));

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("validatedUser")).thenReturn(user);

        when(reimbursementService.getAllResolvedByUserId(user.getUserId())).thenReturn(resolvedReimbursements);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(resolvedReimbursements)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);
        Assert.assertEquals(200, userController.showResolvedRequestsByUserId(request, response));
    }











}
