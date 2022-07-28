package com.davidcipriati.repository;

import com.davidcipriati.model.Reimbursement;

import java.util.List;

public interface ReimbursementDAO {
    int createReimbursement(Reimbursement reimbursement);
    int updatePendingReimbursement(int reimbursementId);
    List<Reimbursement> findAllPendingByUserId(int userId);
    List<Reimbursement> findAllResolvedByUserId(int userId);
    List<Reimbursement> findAllPending();
    List<Reimbursement> findAllByUserId(int userId);
}
