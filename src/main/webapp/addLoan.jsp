<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<h2>Add Loan Application</h2>
</center>
<form action="MainServlet" method="post">
<input type="hidden" name="operation" value="newRecord">
<table>
<tr>
<td>Applicant Name:</td>
<td><input type="text" name="applicantName"></td>
</tr>
<tr>
<td>Loan Type:</td>
<td><input type="text" name="loanType"></td>
</tr>
<tr>
<td>Loan Amount:</td>
<td><input type="text" name="loanAmount"></td>
</tr>
<tr>
<td>Application Date:</td>
<td><input type="text" name="applicationDate"></td>
</tr>
<tr>
<td>Status:</td>
<td><input type="text" name="status"></td>
</tr>
<tr>
<td>Remarks:</td>
<td><input type="text" name="remarks"></td>
</tr>
<tr>
<td></td>
<td><input type="submit" value="Submit"></td>
</tr>
</table>
</form>
<br>
<a href="menu.html">Back to Menu</a>
</body>
</html>