package com.wipro.loan.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.wipro.loan.bean.LoanBean;
import com.wipro.loan.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private Administrator administrator;
    
  
    public MainServlet() {
        super();
        administrator = new Administrator();
    }
    
  
    public String addRecord(HttpServletRequest request) {
        String result = null;
        
        try {
            
            LoanBean bean = new LoanBean();
       
            String applicantName = request.getParameter("applicantName");
            String loanType = request.getParameter("loanType");
            String loanAmountStr = request.getParameter("loanAmount");
            String applicationDateStr = request.getParameter("applicationDate");
            String status = request.getParameter("status");
            String remarks = request.getParameter("remarks");
 
            bean.setApplicantName(applicantName);
            bean.setLoanType(loanType);
         
            if (loanAmountStr != null && !loanAmountStr.trim().isEmpty()) {
                double loanAmount = Double.parseDouble(loanAmountStr);
                bean.setLoanAmount(loanAmount);
            }
            
           
            if (applicationDateStr != null && !applicationDateStr.trim().isEmpty()) {
                Date applicationDate = null;
                
                String[] dateFormats = {"yyyy-MM-dd", "dd-MM-yyyy", "MM-dd-yyyy", 
                                       "M/d/yyyy", "d/M/yyyy", "dd/MM/yyyy"};
                
                for (String format : dateFormats) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                        dateFormat.setLenient(false);
                        applicationDate = dateFormat.parse(applicationDateStr);
                        break; 
                    } catch (ParseException e) {
                       
                    }
                }
                
                if (applicationDate != null) {
                    bean.setApplicationDate(applicationDate);
                } else {
                    System.err.println("Invalid date format: " + applicationDateStr);
                    return "FAIL";
                }
            }
            
            bean.setStatus(status);
            bean.setRemarks(remarks);
    
            result = administrator.addRecord(bean);
            
        } catch (NumberFormatException e) {
            System.err.println("Error parsing loan amount: " + e.getMessage());
            e.printStackTrace();
            result = "FAIL";
        } catch (Exception e) {
            System.err.println("Error in addRecord: " + e.getMessage());
            e.printStackTrace();
            result = "FAIL";
        }
        
        return result;
    }
    
    public LoanBean viewRecord(HttpServletRequest request) {
        LoanBean bean = null;

        try {
            String applicantName = request.getParameter("applicantName");
            String applicationDateStr = request.getParameter("applicationDate");

            if (applicantName == null || applicationDateStr == null ||
                applicantName.trim().isEmpty() || applicationDateStr.trim().isEmpty()) {
                return null;
            }

            Date applicationDate = null;

            
            String[] formats = {
                "yyyy-MM-dd",   
                "d/M/yyyy",    
                "dd/MM/yyyy",  
                "M/d/yyyy",    
                "MM/dd/yyyy"   
            };

            for (String f : formats) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(f);
                    sdf.setLenient(false);
                    applicationDate = sdf.parse(applicationDateStr);
                    break;
                } catch (ParseException ignored) {}
            }

            if (applicationDate == null) {
                System.err.println("Invalid date entered: " + applicationDateStr);
                return null;
            }

           
            bean = administrator.viewRecord(applicantName, applicationDate);

        } catch (Exception e) {
            System.err.println("Error in viewRecord: " + e.getMessage());
            e.printStackTrace();
        }

        return bean;
    }

    
    public List<LoanBean> viewAllRecords(HttpServletRequest request) {
        List<LoanBean> loanList = null;
        
        try {
            
            loanList = administrator.viewAllRecords();
        } catch (Exception e) {
            System.err.println("Error in viewAllRecords: " + e.getMessage());
            e.printStackTrace();
        }
        
        return loanList;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String operation = request.getParameter("operation");
        
        try {
            if (operation != null && operation.equals("newRecord")) {
                
                String result = addRecord(request);
                
                if (result == null || result.equals("FAIL") || 
                    result.equals("INVALID INPUT") || 
                    result.equals("INVALID APPLICANT NAME") || 
                    result.equals("INVALID LOAN AMOUNT") || 
                    result.equals("INVALID LOAN TYPE") || 
                    result.equals("ALREADY EXISTS")) {
                    
                    response.sendRedirect("error.html");
                } else {
                    
                    response.sendRedirect("success.html");
                }
                
            } else if (operation != null && operation.equals("viewRecord")) {
                
                LoanBean bean = viewRecord(request);
                
                if (bean == null) {
                    
                    request.setAttribute("message", 
                                       "No matching records exists! Please try again!");
                    RequestDispatcher dispatcher = 
                        request.getRequestDispatcher("displayLoan.jsp");
                    dispatcher.forward(request, response);
                } else {
                   
                    request.setAttribute("loanBean", bean);
                    RequestDispatcher dispatcher = 
                        request.getRequestDispatcher("displayLoan.jsp");
                    dispatcher.forward(request, response);
                }
                
            } else if (operation != null && operation.equals("viewAllRecords")) {
               
                List<LoanBean> loanList = viewAllRecords(request);
                
                if (loanList == null || loanList.isEmpty()) {
                    
                    request.setAttribute("message", "No records available!");
                    RequestDispatcher dispatcher = 
                        request.getRequestDispatcher("displayAllLoans.jsp");
                    dispatcher.forward(request, response);
                } else {
                    
                    request.setAttribute("loanList", loanList);
                    RequestDispatcher dispatcher = 
                        request.getRequestDispatcher("displayAllLoans.jsp");
                    dispatcher.forward(request, response);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error in doPost: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}