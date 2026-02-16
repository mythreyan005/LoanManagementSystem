<%@ page import="com.wipro.loan.bean.LoanBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    List<LoanBean> loanList = (List<LoanBean>) request.getAttribute("loanList");
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
%>

All Loan Applications

<%
if (loanList != null && !loanList.isEmpty()) {
    for (LoanBean loan : loanList) {
%>
Loan Id : <%= loan.getLoanId() %><br>
Applicant Name : <%= loan.getApplicantName() %><br>
Loan Type : <%= loan.getLoanType() %><br>
Loan Amount : <%= loan.getLoanAmount() %><br>
Application Date : <%= df.format(loan.getApplicationDate()) %><br>
Status : <%= loan.getStatus() %><br>
Remarks : <%= loan.getRemarks() %>


<%
    }
} else {
%>
No records found.
<%
}
%>

<br>

