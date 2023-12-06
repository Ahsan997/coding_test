package com.smallworld.model;

public class Transaction {

    private long mtn;
    private double amount;
    private SenderInformation senderInformation; //This will be database mapping in real environment
    private BeneficiaryInformation beneficiaryInformation; //This will be database mapping in real environment
    private IssueInformation issueInformation; //This will be database mapping in real environment

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

    public SenderInformation getSenderInformation() {
        return senderInformation;
    }

    public void setSenderInformation(SenderInformation senderInformation) {
        this.senderInformation = senderInformation;
    }

    public BeneficiaryInformation getBeneficiaryInformation() {
        return beneficiaryInformation;
    }

    public void setBeneficiaryInformation(BeneficiaryInformation beneficiaryInformation) {
        this.beneficiaryInformation = beneficiaryInformation;
    }

    public IssueInformation getIssueInformation() {
        return issueInformation;
    }

    public void setIssueInformation(IssueInformation issueInformation) {
        this.issueInformation = issueInformation;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "mtn=" + mtn +
                "| amount=" + amount +
                "| senderInformation=" + senderInformation.getSenderFullName()+" , "+senderInformation.getSenderAge() +
                "| beneficiaryInformation=" + beneficiaryInformation.getBeneficiaryFullName()+" , "+beneficiaryInformation.getBeneficiaryAge() +
                "| issueInformation=" + issueInformation.getIssueId()+" , "+issueInformation.isIssueSolved()+" , "+issueInformation.getIssueMessage() +
                '}';
    }
}
