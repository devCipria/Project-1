package com.davidcipriati.service;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.repository.ReimbursementDAO;

import java.util.List;

public class ReimbursementService {
    private ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAOImpl) {
        this.reimbursementDAO = reimbursementDAOImpl;
    }

    public List<Reimbursement> getAllPendingReimbursements() {
        return reimbursementDAO.findAllPending();
    }

    public List<Reimbursement> getAllPendingByUserId(int userId) {
        return reimbursementDAO.findAllByUserId(userId);
    }

}
