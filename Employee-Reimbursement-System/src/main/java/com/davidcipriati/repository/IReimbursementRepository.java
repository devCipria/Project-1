package com.davidcipriati.repository;

import com.davidcipriati.model.Reimbursement;

import java.util.List;

public interface IReimbursementRepository {
    int createReimbursement(Reimbursement reimbursement);
    int updatePendingReimbursement(int reimbursementId);
    List<Reimbursement> findAllPendingByUserId(int userId);
    List<Reimbursement> findAllResolvedByUserId(int userId);
    List<Reimbursement> findAllPending();
    List<Reimbursement> findAllResolved();
    List<Reimbursement> findAllByUserId(int userId);
}
