package com.davidcipriati.services;

import com.davidcipriati.model.Reimbursement;
import com.davidcipriati.repository.IReimbursementRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ReimbursementServiceTest {
    private ReimbursementService reimbursementService;
    private IReimbursementRepository reimbursementRepository;
    Reimbursement reimbursement;

    @Before
    public void init() {
        reimbursementRepository = Mockito.mock(IReimbursementRepository.class);
        reimbursementService = new ReimbursementService(reimbursementRepository);
        reimbursement = new Reimbursement(5, 400.12f, "Lunch with client", 1, 0, "Pending", null, "FOOD");

    }

    @Test
    public void verify_createReimbursementRequest() {
        Reimbursement reimbursement = new Reimbursement(400.12f, "Lunch with client", 1, 0, "Pending", null, "FOOD");
        Mockito.when(reimbursementRepository.createReimbursement(reimbursement)).thenReturn(5);
        Assert.assertNotEquals(-1, reimbursementService.createReimbursementRequest(reimbursement));
    }

    @Test
    public void verify_resolveReimbursement() {
        Mockito.when(reimbursementRepository.updateReimbursement(reimbursement)).thenReturn(true);
        Assert.assertEquals(true, reimbursementService.resolveReimbursement(reimbursement));
    }


}
