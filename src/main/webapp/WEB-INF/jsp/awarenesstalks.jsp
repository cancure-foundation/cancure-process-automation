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
			<div class="page-header">CANCER AWARENESS TALKS</div>
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
	     
	    <h2>CANCER AWARENESS TALKS</h2>
	     <img src="../images/v1/talk.jpg" class="img-responsive" alt="talk" /> <br/>
The Cancure Foundation has conducted several cancer awareness talks educating more than 2000 participants. The cancer awareness talks are led by leading oncologists who during the talk touch up on the cause and prevention of cancer. The session is always followed by Q & A session when the participants clear their doubts on cancer. While conducting the cancer awareness talks we always urge the participants to pass on whatever they have learnt to atleast four more peoples. 
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
