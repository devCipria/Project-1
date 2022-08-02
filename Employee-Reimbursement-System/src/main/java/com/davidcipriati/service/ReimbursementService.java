package com.davidcipriati.service;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.repository.IReimbursementRepository;

import java.util.List;

public class ReimbursementService {
    private IReimbursementRepository repo;

    public ReimbursementService(IReimbursementRepository reimbursementRepository) {
        this.repo = reimbursementRepository;
    }

    public List<Reimbursement> getAllPendingReimbursements() {
        return repo.findAllPending();
    }

    public List<Reimbursement> getAllResolvedReimbursements() {
        return repo.findAllResolved();
    }

    public List<Reimbursement> getAllPendingByUserId(int userId) {
        return repo.findAllPendingByUserId(userId);
    }

    public List<Reimbursement> getAllResolvedByUserId(int userId) {
        return repo.findAllResolvedByUserId(userId);
    }

    public int createReimbursementRequest(Reimbursement reimbursement) {
        // You should validate the reimbursement here -- throw an exception if reimbursement is not formatted
        // create a validate reimbursement request helper
        // does the database provide the validation for me ? if it returns -1 then it wasn't added.
        return repo.createReimbursement(reimbursement);
    }

    public Reimbursement getByReimbursementId(Integer id) {
        return repo.findByReimbursementId(id);
    }

    public List<Reimbursement> getAllRequestByUserId(int id) {
        return repo.findAllByUserId(id);
    }

}
