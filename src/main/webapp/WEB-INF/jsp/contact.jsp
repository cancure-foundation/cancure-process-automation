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

.text-danger {
	color: red;
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
			<div class="page-header">Contact Us</div>
		</div>
	</div>
</header>
<section class="innerpage contactPage">
	<div class="container">
		<div class="title">ADDRESS</div>
		<span class="color">CANCURE FOUNDATION</span><br>
		<span>2nd Floor, Engineer's Tower</span><br/>
		<span>Manikkath Cross Road Jn.,</span><br>
		<span>Ravipuram, Cochin - 682016</span><br/>
		<br>
		<div class="title">PHONE</div>
		<span>7025003333, 7025003332,  0484 2356332</span><br>
		<br>
		<div class="title">EMAIL</div>
		<span>info@cancure.in</span>

		<div class="contactForm">
			<div class="title">Please fill in the form below for your queries and we will contact you soon.</div>
			<% if ("success".equals(request.getAttribute("status"))) {%>
			<p class='text-danger'><%= request.getAttribute("result") != null ? request.getAttribute("result") : "" %></p>
			<% } else { %>
			<form action="/contactus" method="POST">
				
				<div class="row">
					<div class="col3">
						<label for="name">Your Full Name</label>
						<input type="text" id="name" name="name" required value="<%=request.getParameter("name") != null ? request.getParameter("name") : "" %>" >
						<p class='text-danger'><%= request.getAttribute("errName")  != null ? request.getAttribute("errName") : "" %></p>
					</div>
					<div class="col3">
						<label for="email">Your Email Address</label>
						<input type="text" id="email" name="email" required value="<%=request.getParameter("email") != null ? request.getParameter("email") : ""%>">
						<p class='text-danger'><%= request.getAttribute("errEmail")  != null ? request.getAttribute("errEmail") : "" %></p>
					</div>
					<div class="col3">
						<label for="email">Your Phone Number</label>
						<input type="text" id="phone" name="phone" value="<%=request.getParameter("phone") != null ? request.getParameter("phone") : ""%>">
						<p class='text-danger'><%= request.getAttribute("errPhone")  != null ? request.getAttribute("errPhone") : "" %></p>
					</div>
				</div>
				<div style="width:20px; float: left;">
				<input type="checkbox" name="click" value="Please contact me with details on how to become a part of Cancure 
Foundation by being Member/ Volunteer/ Ecosystem Partner" /></div><div style="width:auto;float: left; "> <label for="msg">Please contact me with details on how to become a part of Cancure 
Foundation by being Member/ Volunteer/ Ecosystem Partner</label></div>
				<div class="row">
					
					
				</div>
				<div class="row">
					<label for="msg">Your Message to Us</label>
					<textarea id="message" name="message" required><%=request.getParameter("message")  != null ? request.getParameter("message") : ""%></textarea>
					<p class='text-danger'><%= request.getAttribute("errMessage")  != null ? request.getAttribute("errMessage") : "" %></p>
				</div>
				<input type="submit" style="width: 170px;" class="btn-sbmt" name="submit" value="Send" />
			</form>
			<% } %>
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
<script src="js/easelibs.jquery.js"></script>
<script src="js/jump.jquery.js"></script>
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
