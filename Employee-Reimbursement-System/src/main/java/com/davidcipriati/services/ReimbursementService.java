package com.davidcipriati.services;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.repository.IReimbursementRepository;

import java.util.List;

/**
 * Class that provides reimbursement services to help the controllers
 */
public class ReimbursementService {
    private IReimbursementRepository repo;

    /**
     * Constructor
     */
    public ReimbursementService(IReimbursementRepository reimbursementRepository) {
        this.repo = reimbursementRepository;
    }

    /**
     * Returns a list of all pending reimbursements in the database
     * @return the list of pending reimbursements
     */
    public List<Reimbursement> getAllPendingReimbursements() {
        return repo.findAllPending();
    }

    /**
     * returns a list of all resolved reimbursements in the database
     * @return the list resolved reimbursements
     */
    public List<Reimbursement> getAllResolvedReimbursements() {
        return repo.findAllResolved();
    }

    /**
     * Returs all the pending reimbursements requests for an employee
     * @param userId of the employee wanting to view their pending requests
     * @return a list of the employee's pending requests
     */
    public List<Reimbursement> getAllPendingByUserId(int userId) {
        return repo.findAllPendingByUserId(userId);
    }

    /**
     * all the resolved reimbursements requests for an employee
     * @param userId of the employee wanting to view their resolved requests
     * @return a list of the employee's resolved requests
     */
    public List<Reimbursement> getAllResolvedByUserId(int userId) {
        return repo.findAllResolvedByUserId(userId);
    }

    /**
     * Creates a new reimbursement request and saves it in the database
     * @param reimbursement to be created and saved.
     * @return the id of the saved request. -1 if no request was saved.
     */
    public int createReimbursementRequest(Reimbursement reimbursement) {
        return repo.createReimbursement(reimbursement);
    }

    public Reimbursement getByReimbursementId(Integer id) {
        return repo.findByReimbursementId(id);
    }

    public List<Reimbursement> getAllRequestByUserId(int id) {
        return repo.findAllByUserId(id);
    }

    /**
     * Handles the updating of a request to approved or denied
     * @param reimbursement to be approved or denied
     * @return true if the reimbursement was updated. False, if not.
     */
    public boolean resolveReimbursement(Reimbursement reimbursement) {
        return repo.updateReimbursement(reimbursement);
    }

}
