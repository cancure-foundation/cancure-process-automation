<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Cancure Foundation</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<%@include file="link.html" %>
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
			<div class="page-header">KARUTHAL KENDRAM</div>
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
	       
	    <h2>KARUTHAL KENDRAM</h2>
	     <img src="../images/v1/center.jpg" class="img-responsive" alt="camp" /> <br/>
With the enormous success of our community outreach program called Karuthal – Early Cancer Detection Camps, we found the crying need for even more frequent early detection programs to screen and treat those who have even the minutest symptoms, at a very early stage. One of the initial plans of our Foundation was to setup our own full-fledged Early Cancer Detection Centre. While this was being discussed actively in various forums, there was a suggestion that instead of Cancure starting a centre it would be worthwhile if the Foundation can in association with Hospitals start multiple Early Cancer Detection Centers. This way we have the added advantage of making use of the existing infrastructure and equipments that already exist in such hospitals and their experienced technicians. This thought found expression in the creation of Karuthal Centre’s. Anyone who has any doubt with regard to any of the symptom(s), that they feel as a cancer symptom, can consult the doctor at the centre, and on his recommendation undergo tests available there. All this will be done free of cost and Cancure and the partner hospitals will share the expenses. The objective of this project is to allow people to freely walk in and clear their doubts and thereby detect cancer at an earlier stage. In addition to the screening process that helps those who visit the centers, these Karuthal Kendrams will also serve to enhance the awareness about the importance of early detection and treatment. The formal launch of Karuthal Kendram was held on October 22, 2016 at CUSAT, Kalamassery, coinciding with National Breast Cancer Awareness Month, followed by the formal launch we had the inauguration of the Karuthal Kendram at Sree Sudheendra medical Mission on January 11, 2017 by Sri. Hibi Eden MLA. <br/> <br/>

The second Karuthal Kendram (Cancer Doubt Clearing Centre) a unique project of Cancure Foundation was inaugurated on June 19, 2017 at Rajagiri Hospital, Chunangamvely. The centre at Rajagiri Hospital became functional from Monday June 26, 2017. 
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
