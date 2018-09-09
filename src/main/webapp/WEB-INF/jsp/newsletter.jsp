<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import = "java.io.*" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Cancure Foundation</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<%@include file="links.html" %>
<!--[if lt IE 9]>
    <script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
	<style>
.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
  
    background: #606060;
    min-width: 100%;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown-content a:hover {}

.dropdown:hover .dropdown-content {
    display: block;
}

.dropdown:hover .dropbtn {
    
}
.mainmenu li{
    width:150px;
}
.mainmenu ul{
    valign:top;
}
	</style>
<header class="header">
	<div class="container row">
		<div class="logo">
			<img src="../images/v1/cancure-logo.jpg" alt="" class="img-responsive">
		</div>
		<div class="nav-container">
			<%@include file="menu.html" %>
			<br/>
			<div class="page-header">Newsletter</div>
		</div>
	</div>
</header>
<section class="innerpage contactPage">
	<div class="container">
		<div style="">
			
			<table>
			    <tr>
			    
			    <%
			    String newsLetterPath = getServletContext().getRealPath("/images/v1/newsletter/");
			    File newsLetterDir = new File(newsLetterPath);
			    String[] dirs = newsLetterDir.list(new FilenameFilter() {
			    	  @Override
			    	  public boolean accept(File current, String name) {
			    	    return new File(current, name).isDirectory();
			    	  }
			    	});
			    SimpleDateFormat fmt = new SimpleDateFormat("MMM-yyyy");
			    List<java.util.Date> dirsList = new ArrayList<>();
			    for (String str : dirs) {
			    	dirsList.add(fmt.parse(str));
			    }
			    java.util.Collections.sort(dirsList);
			    
			    for (int i=dirsList.size(); i > 0; i--) {
			    	java.util.Date newsLetterMonthDate = dirsList.get(i-1);
			    	String newsLetterMonth = fmt.format(newsLetterMonthDate);
			    %>
				
				<td style="padding:50px;text-align:center;">
                 <a href="newslettermore?id=<%= newsLetterMonth %>"> 
                  <img src="../images/v1/newsletter/<%= newsLetterMonth %>/thumb.jpg" style="width:120px; height:170px; "  alt="newsletter <%= newsLetterMonth %>" />
                  </a>
              <br/>
              <span style="text-align:center; font-size:12px;"> <a href="newslettermore?id=<%= newsLetterMonth %>"> 
          <%= newsLetterMonth %> </a></span> </td>
             		

				<%}%>			    
</tr>
</table>
		</div>
	
	</div>
</section>
<footer class="footer">
	<div class="container">
		Copyright 2018. Cancure Foundation.
	</div>
</footer>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-1.10.1.min.js"><\/script>')</script>
<script>
$(function() {  
	    var pull        = $('#trigger');  
	        menu        = $('.mainmenu');  
	        menuHeight  = menu.height();  
	  
	    $(pull).on('click', function(e) {  
	        e.preventDefault();  
	        menu.slideToggle();  
	    });  
	}); 
</script>
</body>
</html>
