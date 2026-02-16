<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wipro.loan.bean.LoanBean" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<h2>Loan Application Details</h2>
</center>
<%
LoanBean loanBean = (LoanBean) request.getAttribute("loanBean");
String message = (String) request.getAttribute("message");

if (message != null && !message.isEmpty()) {
%>
<center>
<h3><%= message %></h3>
</center>
<%
} else if (loanBean != null) {
SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
String formattedDate = dateFormat.format(loanBean.getApplicationDate());
%>
<table border="1">
<tr>
<td>Loan ID</td>
<td><%= loanBean.getLoanId() %></td>
</tr>
<tr>
<td>Applicant Name</td>
<td><%= loanBean.getApplicantName() %></td>
</tr>
<tr>
<td>Loan Type</td>
<td><%= loanBean.getLoanType() %></td>
</tr>
<tr>
<td>Loan Amount</td>
<td><%= loanBean.getLoanAmount() %></td>
</tr>
<tr>
<td>Application Date</td>
<td><%= formattedDate %></td>
</tr>
<tr>
<td>Status</td>
<td><%= loanBean.getStatus() %></td>
</tr>
<tr>
<td>Remarks</td>
<td><%= loanBean.getRemarks() != null ? loanBean.getRemarks() : "N/A" %></td>
</tr>
</table>
<%
} else {
%>
<center>
<h3>No matching records exists! Please try again!</h3>
</center>
<%
}
%>
<br>

</body>
</html>