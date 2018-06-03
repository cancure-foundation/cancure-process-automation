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
<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->

<header class="header">
	<div class="container row">
		<div class="logo">
			<img src="../images/v1/cancure-logo.jpg" alt="" class="img-responsive">
		</div>
		<div class="nav-container">
			<%@include file="menu.html" %>
			<div class="page-header">Newsletter</div>
		</div>
	</div>
</header>
<section class="innerpage contactPage">
	<div class="container">
		<div>
			<img src="../images/v1/news/1.jpg" class="img-responsive">
			<img src="../images/v1/news/2.jpg" class="img-responsive">
			<img src="../images/v1/news/3.jpg" class="img-responsive">
			<img src="../images/v1/news/4.jpg" class="img-responsive">
			<img src="../images/v1/news/5.jpg" class="img-responsive">
			<img src="../images/v1/news/6.jpg" class="img-responsive">
			<img src="../images/v1/news/7.jpg" class="img-responsive">
			<img src="../images/v1/news/8.jpg" class="img-responsive">
			<img src="../images/v1/news/9.jpg" class="img-responsive">
			<img src="../images/v1/news/10.jpg" class="img-responsive">
			<img src="../images/v1/news/11.jpg" class="img-responsive">
			<img src="../images/v1/news/12.jpg" class="img-responsive">
	
		</div>
	
	</div>
</section>
<footer class="footer">
	<div class="container">
		Copyright 2015. Cancure Foundation.
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
