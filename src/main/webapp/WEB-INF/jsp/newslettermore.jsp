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
			<img src="images/cancure-logo.jpg" alt="" class="img-responsive">
		</div>
		<div class="nav-container">
			<%@include file="menu.html" %>
			<br/>
			<div class="page-header"><a href="newsletter.php">Newsletter</a></div>
		</div>
	</div>
</header>
<section class="innerpage contactPage">
	<div class="container">
		<div style="">
			
			<%
			String month = request.getParameter("id");
			String newsLetterPath = getServletContext().getRealPath("/images/v1/newsletter/" + month);
			File newsLetterDir = new File(newsLetterPath);
			String[] files = newsLetterDir.list();
			for (String file : files) {
				if (!"thumb.jpg".equals(file)) {
			%>
			<img src="../images/v1/newsletter/<%=month%>/<%=file%>" style="width:1000px;"  alt="newsletter" class="img-responsive" />
			
			<% } 
			} %>
			
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
