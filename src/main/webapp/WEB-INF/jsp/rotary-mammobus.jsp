<?php include_once 'process_form.php'; ?>
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
			<img src="../images/v1/cancure-logo.jpg" alt="Cancure" class="img-responsive">
		</div>
		<div class="nav-container">
			<%@include file="menu.html" %>
			<br/>
			<div class="page-header">Rotary - Cancure Mobile Mammogram Project</div>
		</div>
	</div>
</header>
<section class="innerpage contactPage">
	<style>
		 ul {list-style-type:disc;
		 }
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

	<iframe width="560" height="315" style="max-width:100%;" src="https://www.youtube.com/embed/_s0oB6Ovq6M?rel=0&amp;showinfo=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe> <br/><br/>
	
		
			<h1>Rotary - Cancure Mobile Mammogram Project - <span style="float:9px;"><i style="float:9px;">An Initiative of Rotary International & Cancure Foundation</i></span></h1>
			As per the statistics available in the Cancer Registry, Regional Cancer centre, Thiruvananthapuram, cancer of the breast is the leading cancer among women in the state of Kerala. These figures are expected to increase if corrective steps are not taken now. Sizable number of women in the rural parts of the state are either illiterate or semi literate and do not have the awareness or infrastructure in terms of hospitals or clinics for periodical checkups. It is only in the advanced stage they come for treatment for breast cancer which becomes prohibitively high and adversely affect the family budget. Breast cancer is one cancer which can be detected early and cured completely. The screening tests like mammography, clinical breast examination and breast self examination can to a very large extent detect the disease. With this objective Rotary International District 3201 has come forward in association with Amrita Institute of Medical Sciences and Research Centre (AIMS) and Cancure Foundation to launch a project to enable screening for breast cancer by providing a mobile Mammo bus.   A formal MoU has been entered between Rotary International District 3201, Cancure Foundation and AIMS as parties. The Mammo bus is expected to be delivered by the end of March 2018. <br/><br/>
		<img src="../images/v1/Mammovehicle2.jpg" alt="mammobus" class="img-responsive" />
		<img src="../images/v1/Mammovehicle1.JPG" alt="mammobus" class="img-responsive" />
		<p>
			
			<b>Objective: </b> Early Detection and Awareness Creation of Cancer
			
			<br/>
			<b>Geographical reach:</b> Area covered by Rotary District 3201
			
			<br/>
			<b>Target Group:</b> All Women above 35 years of age in the rural parts of the states of Kerala and Tamil Nadu.
			<br/>
			
			
			<h1>Milestones</h1>
			Govt of Kerala has granted us Road Tax Exemption, first time for a non ambulance vehicle to receive such an exemption.<br>
			Ms. Asha Sarath has volunteered to become the brand ambassador for this project and has done a short video.
			
			<h1>Strategic Partners </h1>
			<ul style="padding-left:20px;">
			    <li>	Rotary International District 3201</li>
			    <li>Local Rotary Clubs</li>
			    <li>Amrita Institute of Medical Sciences and Research Centre (AIMS)</li>
			    <li>Cancure Foundation</li>
			</ul>
			
		</p>
	
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
