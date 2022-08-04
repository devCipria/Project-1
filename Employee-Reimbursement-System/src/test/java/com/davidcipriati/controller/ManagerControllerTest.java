package com.davidcipriati.controller;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.model.User;
import com.davidcipriati.repository.IReimbursementRepository;
import com.davidcipriati.repository.IUserRepository;
import com.davidcipriati.services.ReimbursementService;
import com.davidcipriati.services.UserService;
import com.davidcipriati.web.ManagerController;
import com.davidcipriati.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManagerControllerTest {
    private ReimbursementService reimbursementService;
    private IReimbursementRepository reimbursementRepository;

    private UserService userService;
    private IUserRepository userRepository;

    private ManagerController managerController;

    private ObjectMapper objectMapper;

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

        managerController = new ManagerController(userService, reimbursementService, objectMapper);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void verify_showEmployeeList() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("isManager")).thenReturn(true);

        User user1 = new User(1, "user_john", "pass123", "John", "Doe", "john@gmail.com", "employee");
        User user2 = new User(2, "user_bobby", "password", "Bob", "Doe", "bobby@gmail.com", "employee");
        List<User> employeeList = new ArrayList<>();
        employeeList.add(user1);
        employeeList.add(user2);

        when(userService.getAllEmployees()).thenReturn(employeeList);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(employeeList)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);

        Assert.assertEquals(200, managerController.showEmployeeList(request, response));
    }

    @Test
    public void verify_showAllPendingFromAllEmployees() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("isManager")).thenReturn(true);

        List<Reimbursement> pendingReimbursements = new ArrayList<>();
        pendingReimbursements.add(new Reimbursement(1, 500.25f, "Hotel: Ramada", 1, 0, "Pending", null, "TRAVEL"));
        pendingReimbursements.add(new Reimbursement(2, 34.23f, "Gas", 1, 0, "Pending", null, "TRAVEL"));

        when(reimbursementService.getAllPendingReimbursements()).thenReturn(pendingReimbursements);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(pendingReimbursements)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);

        Assert.assertEquals(200, managerController.showAllPendingFromAllEmployees(request, response));
    }

    @Test
    public void verify_showAllResolvedFromAllEmployees() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("isManager")).thenReturn(true);

        List<Reimbursement> resolvedReimbursements = new ArrayList<>();
        resolvedReimbursements.add(new Reimbursement(1, 500.25f, "Hotel: Ramada", 1, 2, "Resolved", "Approved", "TRAVEL"));
        resolvedReimbursements.add(new Reimbursement(2, 34.23f, "Gas", 1, 3, "Resolved", "Denied", "TRAVEL"));

        when(reimbursementService.getAllResolvedReimbursements()).thenReturn(resolvedReimbursements);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(resolvedReimbursements)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);

        Assert.assertEquals(200, managerController.showAllResolvedFromAllEmployees(request, response));
    }

    @Test
    public void verify_showAllRequestsForOneEmployee() throws IOException {
        int userId = 1;
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("isManager")).thenReturn(true);

        List<Reimbursement> allRequests = new ArrayList<>();
        allRequests.add(new Reimbursement(1, 500.25f, "Hotel: Ramada", 1, 2, "Resolved", "Approved", "TRAVEL"));
        allRequests.add(new Reimbursement(2, 34.23f, "Gas", 1, 3, "Resolved", "Denied", "TRAVEL"));
        allRequests.add(new Reimbursement(3, 230.25f, "Hotel: Sheraton", 1, 0, "Pending", null, "TRAVEL"));

        when(reimbursementService.getAllRequestByUserId(userId)).thenReturn(allRequests);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(allRequests)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);

        Assert.assertEquals(200, managerController.showAllRequestsForOneEmployee(request, response, userId));
    }

    @Test
    public void verify_approveReimbursement() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("isManager")).thenReturn(true);

        User user = new User(2, "manager", "password", "John", "Doe", "manager@yahoo.com", "manager");
        Reimbursement reimbursement = new Reimbursement(1, 77.32f, "Gas", 1, 0, "Pending", null, "TRAVEL");
        when(session.getAttribute("validatedUser")).thenReturn(user);

        // source --> https://stackoverflow.com/questions/41542703/how-to-mock-http-post-with-applicationtype-json-with-mockito-java
        when(request.getReader()).thenReturn(
                new BufferedReader(new StringReader("1")));

        when(reimbursementService.getByReimbursementId(1)).thenReturn(reimbursement);

        when(reimbursementService.resolveReimbursement(reimbursement)).thenReturn(true);

        // source --> https://stackoverflow.com/questions/23292132/mockito-and-httpservletresponse-write-output-to-textfile
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(objectMapper.writeValueAsString(reimbursement)).thenReturn("");
        when(response.getWriter()).thenReturn(printWriter);
        printWriter.flush();

        when(response.getStatus()).thenReturn(200);

        System.out.println(reimbursement);
        Assert.assertEquals(200, managerController.approveReimbursement(request, response));

    }



















}
