<%@page import="java.net.URLEncoder"%>
<%
/*
   This is the sample Checkout Page JSP script. It can be directly used for integration with CCAvenue if your application is developed in JSP. You need to simply change the variables to match your variables as well as insert routines (if any) for handling a successful or unsuccessful transaction.
*/
%>
<%@ page import = "java.io.*,java.util.*,com.ccavenue.security.*" %>
<html>
<head>
	<title>Sub-merchant checkout page</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
	<%
	 String accessCode= (String)request.getAttribute("accessCode");		//Put in the Access Code in quotes provided by CCAVENUES.
	 String workingKey = (String)request.getAttribute("workingKey");    //Put in the 32 Bit Working Key provided by CCAVENUES.  
	 
	 Enumeration enumeration=request.getParameterNames();
	 String ccaRequest="", pname="", pvalue="";
	 while(enumeration.hasMoreElements()) {
	      pname = ""+enumeration.nextElement();
	      pvalue = request.getParameter(pname);
	      ccaRequest = ccaRequest + pname + "=" + URLEncoder.encode(pvalue,"UTF-8") + "&";
	 }
	 AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
	 String encRequest = aesUtil.encrypt(ccaRequest);
	%>
	
	<form id="nonseamless" method="post" name="redirect" action="https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction"/>
	<%-- 
	<form id="nonseamless" method="post" name="redirect" action="/mockCCAvenue"/>
	 --%> 
	
		<input type="hidden" id="encRequest" name="encRequest" value="<%= encRequest %>">
		<input type="hidden" name="access_code" id="access_code" value="<%= accessCode %>">
		<script language='javascript'>document.redirect.submit();</script>
	</form>
	
 </body> 
</html>
