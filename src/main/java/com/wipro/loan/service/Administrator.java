package com.wipro.loan.service;

import java.util.Date;
import java.util.List;

import com.wipro.loan.bean.LoanBean;
import com.wipro.loan.dao.LoanDAO;
import com.wipro.loan.util.InvalidInputException;

public class Administrator {
    
    private LoanDAO loanDAO;
  
    public Administrator() {
        loanDAO = new LoanDAO();
    }
    
   
    public String addRecord(LoanBean bean) {
        String result = null;
        
        try {
      
            if (bean == null || bean.getApplicantName() == null || 
                bean.getLoanType() == null || bean.getApplicationDate() == null) {
                throw new InvalidInputException();
            }
            
      
            if (bean.getApplicantName().length() < 2) {
                return "INVALID APPLICANT NAME";
            }
            
           
            if (bean.getLoanAmount() <= 0) {
                return "INVALID LOAN AMOUNT";
            }
            
      
            if (bean.getLoanType().length() < 2) {
                return "INVALID LOAN TYPE";
            }
         
            if (loanDAO.recordExists(bean.getApplicantName(), bean.getApplicationDate())) {
                return "ALREADY EXISTS";
            }
            
          
            String loanId = loanDAO.generateLoanID(bean.getApplicantName(), 
                                                    bean.getApplicationDate());
           
            bean.setLoanId(loanId);
            
            result = loanDAO.createRecord(bean);
            
        } catch (InvalidInputException e) {
            result = "INVALID INPUT";
        } catch (Exception e) {
            System.err.println("Error in addRecord: " + e.getMessage());
            e.printStackTrace();
            result = "FAIL";
        }
        
        return result;
    }
    
    
    public LoanBean viewRecord(String applicantName, Date applicationDate) {
        LoanBean bean = null;
        
        try {
            bean = loanDAO.fetchRecord(applicantName, applicationDate);
        } catch (Exception e) {
            System.err.println("Error in viewRecord: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bean;
    }
    
    
    public List<LoanBean> viewAllRecords() {
        List<LoanBean> loanList = null;
        
        try {
            loanList = loanDAO.fetchAllRecords();
        } catch (Exception e) {
            System.err.println("Error in viewAllRecords: " + e.getMessage());
            e.printStackTrace();
        }
        
        return loanList;
    }
}