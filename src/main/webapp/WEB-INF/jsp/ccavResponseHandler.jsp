<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "java.io.*,java.util.*,com.ccavenue.security.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cancure Payment Status</title>
</head>
<body>
	<center>
		<font size="4" color="blue"><b>Response Page</b></font>
		<table border="1">
			<%
				String pname=null;
				String pvalue=null;
				Hashtable hs = (Hashtable)request.getAttribute("paymentResultMap");
				Enumeration enumeration = hs.keys();
				while(enumeration.hasMoreElements()) {
					pname=""+enumeration.nextElement();
					pvalue=""+ hs.get(pname);
			%>
				<tr>
					<td><%= pname %> </td>
					<td> <%= pvalue %> </td>
				</tr>
			<%
				}
		%>
		</table>
	</center>
	
	<%
		String orderStatus = (String)request.getAttribute("orderStatus");
		if ("Success".equals(orderStatus)) {
	%>
		Download Receipt
	<%	
		} else if ("Aborted".equals(orderStatus)) {
	%>
		<p>
		<b>Transaction has been aborted</b>
		</p>
	<% } else if ("Failure".equals(orderStatus)) { %>
		<p><b>Transaction has been declined</b></p>
	<% } else { %>
		<br><b>Security Error. Illegal access detected.</b>
	<% } %>
	
	<center>
		<p>
			<a href="/"><b>HOME</b></a>
		</p>
	</center>
	
</body>
</html>