package com.wipro.loan.bean;

import java.util.Date;

public class LoanBean {
    
    private String loanId;
    private String applicantName;
    private String loanType;
    private double loanAmount;
    private Date applicationDate;
    private String status;
    private String remarks;

    public LoanBean() {
        super();
    }
    
    public LoanBean(String loanId, String applicantName, String loanType, 
                    double loanAmount, Date applicationDate, String status, String remarks) {
        super();
        this.loanId = loanId;
        this.applicantName = applicantName;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.applicationDate = applicationDate;
        this.status = status;
        this.remarks = remarks;
    }
    
  
    public String getLoanId() {
        return loanId;
    }
    
    
    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
  
    public String getApplicantName() {
        return applicantName;
    }
   
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    
   
    public String getLoanType() {
        return loanType;
    }
    
   
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
    
    
    public double getLoanAmount() {
        return loanAmount;
    }
    
   
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }
    
    
    public Date getApplicationDate() {
        return applicationDate;
    }
    
   
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }
    
  
    public String getStatus() {
        return status;
    }
   
    public void setStatus(String status) {
        this.status = status;
    }
    
    
    public String getRemarks() {
        return remarks;
    }
  
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Override
    public String toString() {
        return "LoanBean [loanId=" + loanId + ", applicantName=" + applicantName + 
               ", loanType=" + loanType + ", loanAmount=" + loanAmount + 
               ", applicationDate=" + applicationDate + ", status=" + status + 
               ", remarks=" + remarks + "]";
    }
}