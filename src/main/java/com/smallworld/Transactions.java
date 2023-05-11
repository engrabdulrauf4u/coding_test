package com.smallworld;

public class Transactions {

    private  long mtn;
    private  double amount;
    private String senderFullName;
    private Long senderAge;
    private String beneficiaryFullName;
    private Long beneficiaryAge;
    private Long issueId;
    private boolean issueSolved;
    private String issueMessage;

    public long getMtn() {
        return mtn;
    }

    public void setMtn(long mtn) {
        this.mtn = mtn;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public Long getSenderAge() {
        return senderAge;
    }

    public void setSenderAge(Long senderAge) {
        this.senderAge = senderAge;
    }

    public String getBeneficiaryFullName() {
        return beneficiaryFullName;
    }

    public void setBeneficiaryFullName(String beneficiaryFullName) {
        this.beneficiaryFullName = beneficiaryFullName;
    }

    public Long getBeneficiaryAge() {
        return beneficiaryAge;
    }

    public void setBeneficiaryAge(Long beneficiaryAge) {
        this.beneficiaryAge = beneficiaryAge;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public boolean isIssueSolved() {
        return issueSolved;
    }

    public void setIssueSolved(boolean issueSolved) {
        this.issueSolved = issueSolved;
    }

    public String getIssueMessage() {
        return issueMessage;
    }

    public void setIssueMessage(String issueMessage) {
        this.issueMessage = issueMessage;
    }

}
