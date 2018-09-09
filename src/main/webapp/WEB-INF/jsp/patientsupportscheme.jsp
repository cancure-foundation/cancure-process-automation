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
			<div class="page-header">PATIENT SUPPORT SCHEME</div>
		</div>
	</div>
</header>
<section class="innerpage contactPage">
	<style>
		
.contactForm1 input, .contactForm1 select, .contactForm1 textarea {
    border: 1px solid #d7d7d7;
    box-shadow: none;
    box-sizing: border-box;
    font-family: "Raleway",sans-serif;
    height: 44px;
    margin: 5px 0;
    padding: 0 15px;
   
}
.col3{
	width:10%;
}
	</style>

	<div class="container">
	    
	    <h2>PATIENT SUPPORT SCHEME</h2>
	     <img src="../images/v1/pss.jpg" class="img-responsive" alt="camp" /> <br/>
Cancure Foundation began its activities by supporting poor cancer patients
by providing them medicines. Cancure continues to support economically
backward patients by providing medicines as it has been doing over the
years. During the year 2016-17 we have had 28 new patients registered
under the patient support scheme of the Foundation. We have as of now 148
patients who have availed the patient support scheme so far and 42 are on
the active patient’s list. The patient support scheme is still sort after
by many poor cancer patients. The Secretariat contacts the patients
supported by the scheme at regular intervals to get updated on the status
of their physical condition.

	</div>
</section>
<footer class="footer">
	<div class="container">
		Copyright <?php echo date('Y');?>. Cancure Foundation.
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
