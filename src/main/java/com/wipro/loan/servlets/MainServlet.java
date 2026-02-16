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
        administrator = new Administrator();
    }

    // ---------------- ADD RECORD ----------------
    public String addRecord(HttpServletRequest request) {
        String result = null;

        try {
            LoanBean bean = new LoanBean();

            bean.setApplicantName(request.getParameter("applicantName"));
            bean.setLoanType(request.getParameter("loanType"));
            bean.setStatus(request.getParameter("status"));
            bean.setRemarks(request.getParameter("remarks"));

            String amount = request.getParameter("loanAmount");
            if (amount != null && !amount.isEmpty()) {
                bean.setLoanAmount(Double.parseDouble(amount));
            }

            String dateStr = request.getParameter("applicationDate");
            if (dateStr != null && !dateStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                bean.setApplicationDate(sdf.parse(dateStr));
            }

            result = administrator.addRecord(bean);

        } catch (Exception e) {
            e.printStackTrace();
            result = "FAIL";
        }

        return result;
    }

    // ---------------- VIEW SINGLE RECORD ----------------
    public LoanBean viewRecord(HttpServletRequest request) {

        try {
            String name = request.getParameter("applicantName");
            String dateStr = request.getParameter("applicationDate");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);

            return administrator.viewRecord(name, date);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------- VIEW ALL ----------------
    public List<LoanBean> viewAllRecords() {
        return administrator.viewAllRecords();
    }

    // ---------------- GET (For View All) ----------------
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if ("viewAllRecords".equals(operation)) {

            List<LoanBean> list = viewAllRecords();

            if (list == null || list.isEmpty()) {
                request.setAttribute("message", "No records found!");
            } else {
                request.setAttribute("loanList", list);
            }

            RequestDispatcher rd = request.getRequestDispatcher("displayAllLoans.jsp");
            rd.forward(request, response);

        } else {
            doPost(request, response);
        }
    }

    // ---------------- POST ----------------
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        try {

            if ("newRecord".equals(operation)) {

                String result = addRecord(request);

                if ("FAIL".equals(result)) {
                    response.sendRedirect("error.html");
                } else {
                    response.sendRedirect("success.html");
                }

            } else if ("viewRecord".equals(operation)) {

                LoanBean bean = viewRecord(request);

                if (bean == null) {
                    request.setAttribute("message", "No record found!");
                } else {
                    request.setAttribute("loanBean", bean);
                }

                RequestDispatcher rd = request.getRequestDispatcher("displayLoan.jsp");
                rd.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
