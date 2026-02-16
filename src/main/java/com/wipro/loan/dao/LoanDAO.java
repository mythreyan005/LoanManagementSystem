package com.wipro.loan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wipro.loan.bean.LoanBean;
import com.wipro.loan.util.DBUtil;

public class LoanDAO {

    public String createRecord(LoanBean bean) {
        Connection connection = null;
        PreparedStatement ps = null;
        String result = "FAIL";

        try {
            connection = DBUtil.getDBConnection();

            String query = "INSERT INTO LOAN_TB (LOANID, APPLICANTNAME, LOANTYPE, " +
                           "LOANAMOUNT, APPLICATIONDATE, STATUS, REMARKS) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps = connection.prepareStatement(query);
            ps.setString(1, bean.getLoanId());
            ps.setString(2, bean.getApplicantName());
            ps.setString(3, bean.getLoanType());
            ps.setDouble(4, bean.getLoanAmount());
            ps.setDate(5, new java.sql.Date(bean.getApplicationDate().getTime()));
            ps.setString(6, bean.getStatus());
            ps.setString(7, bean.getRemarks());

            if (ps.executeUpdate() > 0) {
                result = bean.getLoanId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        return result;
    }

    
    public LoanBean fetchRecord(String applicantName, Date applicationDate) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LoanBean bean = null;

        try {
            connection = DBUtil.getDBConnection();

            String query = "SELECT * FROM LOAN_TB WHERE APPLICANTNAME = ? " +
                           "AND TRUNC(APPLICATIONDATE) = ?";

            ps = connection.prepareStatement(query);
            ps.setString(1, applicantName);
            ps.setDate(2, new java.sql.Date(applicationDate.getTime()));

            rs = ps.executeQuery();

            if (rs.next()) {
                bean = new LoanBean();
                bean.setLoanId(rs.getString("LOANID"));
                bean.setApplicantName(rs.getString("APPLICANTNAME"));
                bean.setLoanType(rs.getString("LOANTYPE"));
                bean.setLoanAmount(rs.getDouble("LOANAMOUNT"));
                bean.setApplicationDate(rs.getDate("APPLICATIONDATE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setRemarks(rs.getString("REMARKS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        return bean;
    }

    public boolean recordExists(String applicantName, Date applicationDate) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            connection = DBUtil.getDBConnection();

            String query = "SELECT COUNT(*) FROM LOAN_TB WHERE APPLICANTNAME = ? " +
                           "AND TRUNC(APPLICATIONDATE) = ?";

            ps = connection.prepareStatement(query);
            ps.setString(1, applicantName);
            ps.setDate(2, new java.sql.Date(applicationDate.getTime()));

            rs = ps.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        return exists;
    }

    public String generateLoanID(String applicantName, Date applicationDate) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String loanId = null;

        try {
            connection = DBUtil.getDBConnection();

            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String dateStr = df.format(applicationDate);
            String prefix = applicantName.substring(0, 2).toUpperCase();

            ps = connection.prepareStatement("SELECT LOAN_SEQ.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();

            if (rs.next()) {
                loanId = dateStr + prefix + rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        return loanId;
    }

    public List<LoanBean> fetchAllRecords() {
        List<LoanBean> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getDBConnection();
            ps = connection.prepareStatement("SELECT * FROM LOAN_TB ORDER BY APPLICATIONDATE DESC");
            rs = ps.executeQuery();

            while (rs.next()) {
                LoanBean bean = new LoanBean();
                bean.setLoanId(rs.getString("LOANID"));
                bean.setApplicantName(rs.getString("APPLICANTNAME"));
                bean.setLoanType(rs.getString("LOANTYPE"));
                bean.setLoanAmount(rs.getDouble("LOANAMOUNT"));
                bean.setApplicationDate(rs.getDate("APPLICATIONDATE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setRemarks(rs.getString("REMARKS"));
                list.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (connection != null) connection.close(); } catch (Exception e) {}
        }

        return list;
    }
}
