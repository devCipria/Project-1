package com.davidcipriati.model;

public class Reimbursement {
    private Integer reimbursementId;
    private Float amount;
    private String description;
    private Integer authorId;
    private Integer resolverId;
    private String status;
    private String outcome;
    private String type;

    public Reimbursement(){}

    public Reimbursement(Float amount, String description, Integer authorId, Integer resolverId, String status, String outcome, String type) {
        this.amount = amount;
        this.description = description;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.status = status;
        this.outcome = outcome;
        this.type = type;
    }

    public Reimbursement(Integer reimbursementId, Float amount, String description, Integer authorId, Integer resolverId, String status, String outcome, String type) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.description = description;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.status = status;
        this.outcome = outcome;
        this.type = type;
    }

    public Integer getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(Integer reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getResolverId() {
        return resolverId;
    }

    public void setResolverId(Integer resolverId) {
        this.resolverId = resolverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
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
                ", outcome='" + outcome + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
