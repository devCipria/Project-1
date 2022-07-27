package com.davidcipriati.model;

import java.math.BigDecimal;

public class Reimbursement {
    private int reimbursementId;
    private float amount;
    private String description;
    private int authorId;
    private int resolverId;
    private String status;
    private String type;

    public Reimbursement(float amount, String description, int authorId, int resolverId, String status, String type) {
        this.amount = amount;
        this.description = description;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.status = status;
        this.type = type;
    }

    public Reimbursement(int reimbursementId, float amount, String description, int authorId, int resolverId, String status, String type) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.description = description;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.status = status;
        this.type = type;
    }

    public int getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(int reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getResolverId() {
        return resolverId;
    }

    public void setResolverId(int resolverId) {
        this.resolverId = resolverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + reimbursementId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", authorId=" + authorId +
                ", resolverId=" + resolverId +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
