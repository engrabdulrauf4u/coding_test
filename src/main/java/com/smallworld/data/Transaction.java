package com.smallworld.data;
public  class Transaction{

      int mtn;
      double amount;
      String senderFullName;
      int senderAge;
      String beneficiaryFullName;
      int beneficiaryAge;
      Integer issueId;
      boolean issueSolved;
      String issueMessage;

      public int getMtn() {
            return mtn;
      }

      public void setMtn(int mtn) {
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

      public int getSenderAge() {
            return senderAge;
      }

      public void setSenderAge(int senderAge) {
            this.senderAge = senderAge;
      }

      public String getBeneficiaryFullName() {
            return beneficiaryFullName;
      }

      public void setBeneficiaryFullName(String beneficiaryFullName) {
            this.beneficiaryFullName = beneficiaryFullName;
      }

      public int getBeneficiaryAge() {
            return beneficiaryAge;
      }

      public void setBeneficiaryAge(int beneficiaryAge) {
            this.beneficiaryAge = beneficiaryAge;
      }

      public Integer getIssueId() {
            return issueId;
      }

      public void setIssueId(Integer issueId) {
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
      @Override
      public String toString() {
            return "Transaction{" +
                    "mtn='" + mtn + '\'' +
                    ", amount=" + amount +
                    ", senderFullName='" + senderFullName + '\'' +
                    ", senderAge=" + senderAge +
                    ", beneficiaryFullName='" + beneficiaryFullName + '\'' +
                    ", beneficiaryAge=" + beneficiaryAge +
                    ", issueId='" + issueId + '\'' +
                    ", issueSolved=" + issueSolved +
                    ", issueMessage='" + issueMessage + '\'' +
                    '}';
      }

}
