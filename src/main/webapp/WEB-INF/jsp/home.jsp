<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Cancure Foundation</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<%@include file="links.html" %>
<link rel="stylesheet" href="css/v1/font-awesome.min.css">
<script src="css/v1/prefixfree.min.js"></script>
<!--[if lt IE 9]>
    <script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<style>
/* Resets */


@media only screen and (max-width: 1590px) and (min-width: 768px){
.drawer ul {
  margin: 10;
  padding: 0;
}

/* Decor */


.drawer {
    
  position: relative;
  z-index: 10;
  top: 0;
  
  
  padding: .4em 0;
  
  color: #000;
  text-align: left;
  /* Remove 4px gap between <li> */
  font-size: 0px;

}

.drawer li {
  pointer-events: none;
  position: relative;
  display: inline-block;
  vertical-align: middle;
  list-style: none;
  line-height: 100%;
  transform: translateZ(0);
}

.drawer a {
  pointer-events: auto;
  position: relative;
  display: block;
  min-width: 5em;
  margin-bottom: .4em;
  padding: .4em;
  line-height: 100%;
  /* Reset font-size */
  font-size: 16px;
  text-decoration: none;
  color: #000;
  transition: background 0.3s;
}

.drawer a:active,
.drawer a:focus {  }

.drawer i {
  display: block;
  margin-bottom: .2em;
  font-size: 2em;
}

.drawer span {
  font-size: .625em;
  font-family: sans-serif;
  text-transform: uppercase;
  opacity:0.1px;
}

.drawer li:hover ul {
  /* Open the fly-out menu */
  transform: translateX(0);
  opacity:0.1px;
  /* Enable this if you wish to replicate hoverIntent */
}

.drawer > li { display: block;/* Add a shadow to the top-level link */
  /* Show the shadow when the link is hovered over */
  /* Fly out menus */
}
.drawer > li > ul{
   
    
}

.drawer > li > ul > a{
    
   
}
.drawer > li > a {  opacity:0.5px; }

.drawer > li:hover { z-index: 100; opacity:1px; display: block; }

.drawer > li ul:hover a {  opacity:1px; display: block; }

.drawer > li a:hover {  opacity:1px; display: block; }

.drawer > li > a:after {
  content: "";
  position: absolute;
  display:inline-block;
  top: 0;
  bottom: 0;
  left: 100%;
  width: 4px;

 
  transition: opacity 0.5s;
}

#insidel {
 
    
}


#insidel::after {
    transition: opacity 1s ease;
     
}
#insidel:hover::after {
    opacity: 0.5;
    
}
.drawer > li ul {
  position: absolute;
  /* Stack below the top level */
  z-index: 5;
  top: 0;
  right: 100%;
  height: 100%;
  width: auto;
  display:block;
  white-space: nowrap;
  /* Close the menus */
  transform: translateY(-1024px);
  
  transition: 0.5s transform;
}
}
</style>
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
</head>
<body class="homeBody">
<!--[if lt IE 7]><p class=chromeframe>Your browser is <em>ancient!</em> <a href="http://browsehappy.com/">Upgrade to a different browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to experience this site.</p><![endif]-->

<header class="header">
	<div class="slider-container"></div>	
	<div class="container row">
		<div class="logo">
			<img src="../images/v1/cancure-logo.jpg" alt="" class="img-responsive">
		</div>
		<div class="nav-container">
			<%@include file="menu.html" %>
		</div>
		
				<div  class="normalSec" align="right" style=" padding-top:20px; ">
		<ul class="drawer" >
<li> <a href="#">  <span><img src="../images/v1/upcoming.gif" alt="santhwana"/></span> </a>
<ul >
<li id="insidel"> <a href="/news/santhwanakendram"> <img src="../images/v1/Santhwana.png" alt="santhwana"/> </a> </li>
<li id="insidel"> <a href="/news/rotary-mammobus"> <img src="../images/v1/forHerCareLogo.jpg" alt="For her care"/> </a> </li>
</ul>
</li>

</ul>
				<div   >
		
			<!--<img src="images/tick.png" alt="u" style="float:left; padding:15px;" />-->
			<body>
			   
<!--<ul class="drawer">
<li> <a href="#"> <i class="fa fa-check"></i> <span>Upcoming Events</span> </a>
<ul>
<li> <a href="#"> <i class="fa fa-calendar-check-o"></i> <span><img src="images/03.gif"  alt="" class="img-responsive" style="padding:5px;" /></span> </a> </li>

</ul>
</li>

</ul>-->
		<!--	<ul class="event1">
				<li style=" color:#FFF; "><b>Upcoming Events</b>
					
				</li>
			</ul>-->
		</div>
			
			
			<!--<a href="raagasudha.php">
				<img src="images/03.gif"  alt="" class="img-responsive" style="padding:5px;" />
			</a>-->
		</div>
	</div>
</header>
<section class="bodysection">
	<div class="container">
		<div class="body-title">
			<div class="big-title">can <span>care.</span> can <span>share.</span> can <span>cure.</span></div>
			<a href="/news/donate" class="btn-trans">MAKE A DONATION</a>
		</div>
		<img src="../images/v1/kid.jpg" alt="" class="img-responsive">
		<div class="contentSec">
			<div class="row commonbg">
				<div class="splitSec">
					<div class="split-content">
						<div class="light-text">Stop dreading cancer</div>
						<div class="dark-text">prevent it.</div>
						<p>It's a myth to believe that getting cancer is simply due to bad luck or genetic factors. Experts believe that up to half of cases can be prevented through lifestyle changes. It is never too late to make changes to your lifestyles.</p>
						<p>Around 10% of cancers diagnosed are associated with poor dietary lifestyles. A great number more lives can be saved if people with symptoms seek help and get treated earlier. Why not do our bit ot spread awareness on the needy for early detection and prevention of cancer?</p>
					</div>
				</div>
				<div class="splitSec">
					<div class="split-content">
						<div class="light-text">Cancer is fatal</div>
						<div class="dark-text">if not treated in time.</div>
						<p>Cancer becomes deadly if not diagnosed and treated in time. Timely medication and proper treatment can cure cancer. However, we see cancer taking away the lives of many every day - especially the less fortunate amongst us who cannot afford expensive treatment facilities and costly medicines.</p>
						<p>They are left to perish and with them the hopes of their entire families. Can't we step in to help them in their hour of need and save such precious lives?</p>
					</div>
				</div>
			</div>
			<div class="row commonbg">
				<div class="splitSec">
					<div class="split-content">
						<div class="light-text">Life after cancer</div>
						<div class="dark-text">the tornado effect.</div>
						<p>What is more frightening than cancer is the impact of cancer. An attack of cancer can have devastating effects on the patients and their families. While treatment drains their savings on the one hand, cancer leaves them incapable of generating income on the other.</p>
						<p>Picking up the broken pieces and beginning life all over again in the aftermath of a cancer attack can be more threatening than the disease itself. Can't we extend a helping hand to usher these families back into the mainstream society?</p>
					</div>
				</div>
				<div class="splitSec">
	 				<div class="split-content">
						<div class="light-text">Cancure Foundation</div>
						<div class="dark-text">sharing is caring is curing.</div>
						<p>The aim of Cancure Foundation is to spread awareness on prevention and early detection of cancer, to provide financial support for diagnostic tests, purchase of drugs, treatment, palliative care for the terminally ill, and rehabilitation after treatment.</p>
					</div>
				</div>
			</div>
		</div>
		<div class="specialSec">
			<div class="row">
				<div class="sideA">
					<div class="title-light">Cancure Foundation</div>
					<div class="title-bold">sharing is caring is curing.</div>
					<p>The aim of Cancure Foundation is to spread awareness on prevention and early detection of cancer, to provide financial support for diagnostic tests, purchase of drugs, treatment, palliative care for the terminally ill, and rehabilitation after treatment</p>
				</div>
				<div class="sideB">
					
				</div>
			</div>
		</div>
		<div class="icoSec">
			<div class="head">Let's combat <cancer class="hilite">hand in hand</cancer></div>
			<div>Reach out, touch and help save more lives. Join the movement as</div>
			<div class="row">
				<div class="iconContainer">
					<div class="iconHolder"><i class="fa fa-male"></i></div>
					<div class="iconText">Individual Members</div>
				</div>
				<div class="iconContainer">
					<div class="iconHolder"><i class="fa fa-user"></i></div>
					<div class="iconText">Corporate Members</div>
				</div>
				<div class="iconContainer">
					<div class="iconHolder"><i class="fa fa-users"></i></div>
					<div class="iconText">Volunteers for continous or specific campaigns</div>
				</div>
				<div class="iconContainer">
					<div class="iconHolder"><i class="fa fa-recycle"></i></div>
					<div class="iconText">Partners in the Cancure ecosystem</div>
				</div>
			</div>
		</div>
		<div class="normalSec commonbg">
			<h1>TOGETHER WE CAN SAVE. NOT ONE, BUT MANY</h1>
			<div>Cancure draws support from the following partners of its ecosystem</div>
			<br>
			<ul class="commonlist">
				<li>Individuals or corporate bodies willing to contribute directly to the Foundation or indirectly through specific events and projects.</li>
				<li>A panel of medical experts for selection of beneficiaries.</li>
				<li>Doctors empanelled on a non-commercial basis for screening cases.</li>
				<li>Medical Support Providers:
					<ul>
						<li>Cancure Affiliated Hospitals (CAH)</li>
						<li>Cancure Affiliated Labs (CAL)</li>
						<li>Cancure Affiliated Pharmacists (CAP) (Pharmaceutical companies, distributors or stockists)</li>
					</ul>
				</li>
			</ul>
		</div>
		<div class="donateSec">
			<div class="subhead">You can save a life</div>
			<div class="mainhead">Today! Now!</div>
			<p>Cancure Foundation takes all efforts to identify the needy and provide support when and where it's needed the most. Many a times the expense for the treatment of cancer is quite exorbitant. Mother Teresa once said, "Everything we do is only a drop in the ocean. But the ocean would be poorer, if not for each drop".</p>
			<p>Even a contribution as low as Rs. 1000/- would add up to settle one patient's medical bills. So do not hesitate to contact us to connect, collaborate and contribute. A rupee spared could be a life saved!</p>
			<p>
				All donations to Cancure Foundation are exempt  u/s 80 G of the Income tax Act.
			</p>
			<div class="alCenter">
				<a href="donate.php" class="btn-trans color">DONATE NOW</a>
			</div>
		</div>
	</div>
</section>
<footer class="footer">
	<div class="container">
		Copyright 2015 - <%= request.getAttribute("year") %>. Cancure Foundation.
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