package com.davidcipriati;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.model.User;
import com.davidcipriati.repository.ReimbursementDAOImpl;
import com.davidcipriati.repository.UserDAO;
import com.davidcipriati.repository.UserDAOImpl;
import com.davidcipriati.service.ReimbursementService;
import com.davidcipriati.service.UserService;

import java.util.List;

public class App {
    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAOImpl();
//        UserService userService = new UserService(new UserDAOImpl());
//        List<User> users = userService.getAllEmployees();
//        users.forEach(user -> System.out.println(user));
        System.out.println();
        ReimbursementService reimbursementService = new ReimbursementService(new ReimbursementDAOImpl());
        List<Reimbursement> allPending = reimbursementService.getAllPendingReimbursements();
        allPending.forEach(pending -> System.out.println(pending));

    }
}
