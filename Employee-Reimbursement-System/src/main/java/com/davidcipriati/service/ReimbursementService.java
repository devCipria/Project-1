package com.davidcipriati.service;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.repository.IReimbursementRepository;

import java.util.List;

public class ReimbursementService {
    private IReimbursementRepository IReimbursementRepository;

    public ReimbursementService(IReimbursementRepository reimbursementRepository) {
        this.IReimbursementRepository = reimbursementRepository;
    }

    public List<Reimbursement> getAllPendingReimbursements() {
        return IReimbursementRepository.findAllPending();
    }

    public List<Reimbursement> getAllResolvedReimbursements() {
        return IReimbursementRepository.findAllResolved();
    }

    public List<Reimbursement> getAllPendingByUserId(int userId) {
        return IReimbursementRepository.findAllByUserId(userId);
    }

}
