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
<style>
	*, *::before, *::after {
    box-sizing: border-box;
}
*, *::before, *::after {
    box-sizing: border-box;
}
.form-inline .form-group {
    display: inline-block;
    margin-bottom: 0;
    vertical-align: middle;
}
.radio-inline, .checkbox-inline {
    cursor: pointer;
    display: inline-block;
    font-weight: normal;
    margin-bottom: 0;
    padding-left: 20px;
    vertical-align: middle;
}
.radio input[type="radio"], .radio-inline input[type="radio"], .checkbox input[type="checkbox"], .checkbox-inline input[type="checkbox"] {
    float: left;
    margin-left: -20px;
}
</style>
</head>
<body>
	<%
		String price = request.getParameter("price");
		String donate = request.getParameter("donate");
	%>
	
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
			<div class="page-header">Donate</div>
		</div>
	</div>
</header>
<section class="innerpage contactPage">
	<div class="container">
	<div >Payment Details</div>
	<div class="donateForm">
		<script>
	window.onload = function() {
		var d = new Date().getTime();
		document.getElementById("tid").value = d;
	};
</script>
<div>
	<%--
		java.util.Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()) {
			String s = (String)en.nextElement();
			out.println(" Name - " + s + " and value is "+ request.getParameter(s));
		}
	--%>
</div>
<form name="customerData" action="/donate" method="post" enctype="multipart/form-data">
 <div class="row">
	 	<div class="col3">
	 			<input type="hidden" class="form-control" name="tid" id="tid" readonly value=""/>
						<label for="name"> Name</label>
						</span><input type="text" class="input-field" name="billing_name" value="" required="" />
					</div>
				<div class="col3">
				</div>
							<div class="col3">
						<label for="name">Name of Organization</label>
						<input type="text" id="lname" name="org" required >
						
					</div>
					<div class="col3">
						<label for="name">Designation</label>
						</span><input type="text" class="input-field" name="desig" value="" required="" />
					</div>

	 </div>
	 <div class="row">
	 	
							<div class="col3">
						<label for="name">Address</label>
						<input type="text" id="lname" name="billing_address" >
						
					</div>
							<div class="col3">
						<label for="email">Email</label>
						<input type="email" id="lname" name="billing_email" required />
						
					</div>
						 	<div class="col3">
						<label for="name">Pincode</label>
						</span><input type="text" class="input-field" name="billing_zip" value=""  />
					</div>

	 </div>
	 	 <div class="row">
	 	<div class="col3">
						<label for="name">Country</label>
						</span>	<select class="input-field" id="orderBillCountry" required name="billing_country"><option selected="selected" value="">Select Country</option>
	                     <option value="Afghanistan">Afghanistan</option>
<option value="Aland Islands">Aland Islands</option>
<option value="Albania">Albania</option>
<option value="Algeria">Algeria</option>
<option value="American Samoa">American Samoa</option>
<option value="Andorra">Andorra</option>
<option value="Angola">Angola</option>
<option value="Anguilla">Anguilla</option>
<option value="Antarctica">Antarctica</option>
<option value="Antigua and Barbuda">Antigua and Barbuda</option>
<option value="Argentina">Argentina</option>
<option value="Armenia">Armenia</option>
<option value="Aruba">Aruba</option>
<option value="Australia">Australia</option>
<option value="Austria">Austria</option>
<option value="Azerbaijan">Azerbaijan</option>
<option value="Bahamas">Bahamas</option>
<option value="Bahrain">Bahrain</option>
<option value="Bangladesh">Bangladesh</option>
<option value="Barbados">Barbados</option>
<option value="Belarus">Belarus</option>
<option value="Belgium">Belgium</option>
<option value="Belize">Belize</option>
<option value="Benin">Benin</option>
<option value="Bermuda">Bermuda</option>
<option value="Bhutan">Bhutan</option>
<option value="Bolivia">Bolivia</option>
<option value="Bonaire">Bonaire</option>
<option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
<option value="Botswana">Botswana</option>
<option value="Bouvet Island">Bouvet Island</option>
<option value="Brazil">Brazil</option>
<option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
<option value="Brunei Darussalam">Brunei Darussalam</option>
<option value="Bulgaria">Bulgaria</option>
<option value="Burkina Faso">Burkina Faso</option>
<option value="Burundi">Burundi</option>
<option value="Cambodia">Cambodia</option>
<option value="Cameroon">Cameroon</option>
<option value="Canada">Canada</option>
<option value="Cape Verde">Cape Verde</option>
<option value="Cayman Islands">Cayman Islands</option>
<option value="Central African Republic">Central African Republic</option>
<option value="Chad">Chad</option>
<option value="Chile">Chile</option>
<option value="China">China</option>
<option value="Christmas Island">Christmas Island</option>
<option value="Cocos Islands">Cocos Islands</option>
<option value="Colombia">Colombia</option>
<option value="Comoros">Comoros</option>
<option value="Congo">Congo</option>
<option value="Democratic Republic of the Congo">Democratic Republic of the Congo</option>
<option value="Cook Islands">Cook Islands</option>
<option value="Costa Rica">Costa Rica</option>
<option value="Cote dIvoire">Cote dIvoire</option>
<option value="Croatia">Croatia</option>
<option value="Cuba">Cuba</option>
<option value="Curacao">Curacao</option>
<option value="Cyprus">Cyprus</option>
<option value="Czech Republic">Czech Republic</option>
<option value="Denmark">Denmark</option>
<option value="Djibouti">Djibouti</option>
<option value="Dominica">Dominica</option>
<option value="Dominican Republic">Dominican Republic</option>
<option value="Ecuador">Ecuador</option>
<option value="Egypt">Egypt</option>
<option value="El Salvador">El Salvador</option>
<option value="Equatorial Guinea">Equatorial Guinea</option>
<option value="Eritrea">Eritrea</option>
<option value="Estonia">Estonia</option>
<option value="Ethiopia">Ethiopia</option>
<option value="Falkland Islands">Falkland Islands</option>
<option value="Faroe Islands">Faroe Islands</option>
<option value="Fiji">Fiji</option>
<option value="Finland">Finland</option>
<option value="France">France</option>
<option value="French Guiana">French Guiana</option>
<option value="French Polynesia">French Polynesia</option>
<option value="French Southern Territories">French Southern Territories</option>
<option value="Gabon">Gabon</option>
<option value="Gambia">Gambia</option>
<option value="Georgia">Georgia</option>
<option value="Germany">Germany</option>
<option value="Ghana">Ghana</option>
<option value="Gibraltar">Gibraltar</option>
<option value="Greece">Greece</option>
<option value="Greenland">Greenland</option>
<option value="Grenada">Grenada</option>
<option value="Guadeloupe">Guadeloupe</option>
<option value="Guam">Guam</option>
<option value="Guatemala">Guatemala</option>
<option value="Guernsey">Guernsey</option>
<option value="Guinea">Guinea</option>
<option value="Guinea Bissau">Guinea Bissau</option>
<option value="Guyana">Guyana</option>
<option value="Haiti">Haiti</option>
<option value="Heard Island and McDonald Islands">Heard Island and McDonald Islands</option>
<option value="Vatican City">Vatican City</option>
<option value="Honduras">Honduras</option>
<option value="Hong Kong">Hong Kong</option>
<option value="Hungary">Hungary</option>
<option value="Iceland">Iceland</option>
<option value="India">India</option>
<option value="Indonesia">Indonesia</option>
<option value="Iran">Iran</option>
<option value="Iraq">Iraq</option>
<option value="Ireland">Ireland</option>
<option value="Isle of Man">Isle of Man</option>
<option value="Israel">Israel</option>
<option value="Italy">Italy</option>
<option value="Jamaica">Jamaica</option>
<option value="Japan">Japan</option>
<option value="Jersey">Jersey</option>
<option value="Jordan">Jordan</option>
<option value="Kazakhstan">Kazakhstan</option>
<option value="Kenya">Kenya</option>
<option value="Kiribati">Kiribati</option>
<option value="North Korea">North Korea</option>
<option value="South Korea">South Korea</option>
<option value="Kuwait">Kuwait</option>
<option value="Kyrgyzstan">Kyrgyzstan</option>
<option value="Laos">Laos</option>
<option value="Latvia">Latvia</option>
<option value="Lebanon">Lebanon</option>
<option value="Lesotho">Lesotho</option>
<option value="Liberia">Liberia</option>
<option value="Libya">Libya</option>
<option value="Liechtenstein">Liechtenstein</option>
<option value="Lithuania">Lithuania</option>
<option value="Luxembourg">Luxembourg</option>
<option value="Macao">Macao</option>
<option value="Republic of Macedonia">Republic of Macedonia</option>
<option value="Madagascar">Madagascar</option>
<option value="Malawi">Malawi</option>
<option value="Malaysia">Malaysia</option>
<option value="Maldives">Maldives</option>
<option value="Mali">Mali</option>
<option value="Malta">Malta</option>
<option value="Marshall Islands">Marshall Islands</option>
<option value="Martinique">Martinique</option>
<option value="Mauritania">Mauritania</option>
<option value="Mauritius">Mauritius</option>
<option value="Mayotte">Mayotte</option>
<option value="Mexico">Mexico</option>
<option value="Federated States of Micronesia">Federated States of Micronesia</option>
<option value="Moldova">Moldova</option>
<option value="Monaco">Monaco</option>
<option value="Mongolia">Mongolia</option>
<option value="Montenegro">Montenegro</option>
<option value="Montserrat">Montserrat</option>
<option value="Morocco">Morocco</option>
<option value="Mozambique">Mozambique</option>
<option value="Myanmar">Myanmar</option>
<option value="Namibia">Namibia</option>
<option value="Nauru">Nauru</option>
<option value="Nepal">Nepal</option>
<option value="Netherlands">Netherlands</option>
<option value="New Caledonia">New Caledonia</option>
<option value="New Zealand">New Zealand</option>
<option value="Nicaragua">Nicaragua</option>
<option value="Niger">Niger</option>
<option value="Nigeria">Nigeria</option>
<option value="Niue">Niue</option>
<option value="Norfolk Island">Norfolk Island</option>
<option value="Northern Mariana Islands">Northern Mariana Islands</option>
<option value="Norway">Norway</option>
<option value="Oman">Oman</option>
<option value="Pakistan">Pakistan</option>
<option value="Palau">Palau</option>
<option value="Palestine">Palestine</option>
<option value="Panama">Panama</option>
<option value="Papua New Guinea">Papua New Guinea</option>
<option value="Paraguay">Paraguay</option>
<option value="Peru">Peru</option>
<option value="Philippines">Philippines</option>
<option value="Pitcairn">Pitcairn</option>
<option value="Poland">Poland</option>
<option value="Portugal">Portugal</option>
<option value="Puerto Rico">Puerto Rico</option>
<option value="Qatar">Qatar</option>
<option value="Reunion">Reunion</option>
<option value="Romania">Romania</option>
<option value="Russian Federation">Russian Federation</option>
<option value="Rwanda">Rwanda</option>
<option value="Saint Barthelemy">Saint Barthelemy</option>
<option value="Saint Helena">Saint Helena</option>
<option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
<option value="Saint Lucia">Saint Lucia</option>
<option value="Saint Martin">Saint Martin</option>
<option value="Saint Pierre and Miquelon">Saint Pierre and Miquelon</option>
<option value="Saint Vincent and the Grenadines">Saint Vincent and the Grenadines</option>
<option value="Samoa">Samoa</option>
<option value="San Marino">San Marino</option>
<option value="Sao Tome and Principe">Sao Tome and Principe</option>
<option value="Saudi Arabia">Saudi Arabia</option>
<option value="Senegal">Senegal</option>
<option value="Serbia">Serbia</option>
<option value="Seychelles">Seychelles</option>
<option value="Sierra Leone">Sierra Leone</option>
<option value="Singapore">Singapore</option>
<option value="Sint Maarten Dutch part">Sint Maarten Dutch part</option>
<option value="Slovakia">Slovakia</option>
<option value="Slovenia">Slovenia</option>
<option value="Solomon Islands">Solomon Islands</option>
<option value="Somalia">Somalia</option>
<option value="South Africa">South Africa</option>
<option value="South Georgia and the South Sandwich Islands">South Georgia and the South Sandwich Islands</option>
<option value="South Sudan">South Sudan</option>
<option value="Spain">Spain</option>
<option value="Sri Lanka">Sri Lanka</option>
<option value="Sudan">Sudan</option>
<option value="Suriname">Suriname</option>
<option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
<option value="Swaziland">Swaziland</option>
<option value="Sweden">Sweden</option>
<option value="Switzerland">Switzerland</option>
<option value="Syrian Arab Republic">Syrian Arab Republic</option>
<option value="Taiwan">Taiwan</option>
<option value="Tajikistan">Tajikistan</option>
<option value="Tanzania">Tanzania</option>
<option value="Thailand">Thailand</option>
<option value="East Timor">East Timor</option>
<option value="Togo">Togo</option>
<option value="Tokelau">Tokelau</option>
<option value="Tonga">Tonga</option>
<option value="Trinidad and Tobago">Trinidad and Tobago</option>
<option value="Tunisia">Tunisia</option>
<option value="Turkey">Turkey</option>
<option value="Turkmenistan">Turkmenistan</option>
<option value="Turks and Caicos Islands">Turks and Caicos Islands</option>
<option value="Tuvalu">Tuvalu</option>
<option value="Uganda">Uganda</option>
<option value="Ukraine">Ukraine</option>
<option value="United Arab Emirates">United Arab Emirates</option>
<option value="United Kingdom">United Kingdom</option>
<option value="United States">United States</option>
<option value="United States Minor Outlying Islands">United States Minor Outlying Islands</option>
<option value="Uruguay">Uruguay</option>
<option value="Uzbekistan">Uzbekistan</option>
<option value="Vanuatu">Vanuatu</option>
<option value="Venezuela">Venezuela</option>
<option value="Viet Nam">Viet Nam</option>
<option value="British Virgin Islands">British Virgin Islands</option>
<option value="United States Virgin Islands">United States Virgin Islands</option>
<option value="Wallis and Futuna">Wallis and Futuna</option>
<option value="Western Sahara">Western Sahara</option>
<option value="Yemen">Yemen</option>
<option value="Zambia">Zambia</option>
<option value="Zimbabwe">Zimbabwe</option></select>
					</div>
							<div class="col3">
						<label for="name">State</label>
						<input type="text" id="lname" name="billing_state" >
						
					</div>
							<div class="col3">
						<label for="email">City</label>
						<input type="text" id="lname" name="billing_city"  />
						
					</div>

	 </div>
	 <div class="row">
	 	<div class="col3">
						<label for="name">Mobile No</label>
						</span><input type="text" class="input-field" name="billing_tel" value="" required="" />
					</div>
							<div class="col3">
						<label for="name">Tel (0)</label>
						<input type="text" id="lname" name="telo" >
						
					</div>
							<div class="col3">
						<label for="email">Tel (R)</label>
						<input type="text" id="lname" name="telr"  />
						
					</div>

	 </div>
	 	 <div class="row">

							<div class="col3">
						<label for="name">Amount </label>
						<input type="hidden" name="merchant_id" value="89352"/>
					<input required type="number" value="<%= request.getParameter("price") %>" onkeypress="return isNumberKey(event)" class="form-control" name="amount" id="amount" />
						<input type="hidden" name="currency" value="INR"/>
			<input type="hidden" name="language" value="EN"/>
			<input type="hidden" name="redirect_url" value="http://cancure.in/donateResponseHandler"/>
			<input type="hidden" name="cancel_url" value="http://cancure.in/donateResponseHandler"/>
					</div>
				   <fieldset>
   <div class="form-group" style="display:inline; ">
    <label >Payment being made towards:</label>
         <label class="radio-inline" style="display:inline;float:left; height:auto; padding 5px; "> Membership Fees
    <input type="radio"  style="display:inline; background-color: #CCC; width:12px; height:10px; padding: 0px;  float:left" name="product_name" value="Membership Fees"/>
    </label>
    <label class="radio-inline" style="display:inline; height:auto; float:left; padding 5px; margin-left:15px;"> &nbsp; &nbsp; Donation
    <input type="radio" <% if ("1".equals(request.getAttribute("donate"))) { %> checked="checked"<%}%> style="display:inline; background-color: #CCC; width:13px; height:10px; float:left" name="product_name" value="Donation"/>
    </label>
   
    </div>
   <!--form-group-->
     </fieldset>

	 </div>

<div class="row">


  <input type="hidden" name="order_id" value="ORD_ID<%= new java.util.Random().nextLong() %>"/>
  <input type="hidden" name="invoice" value="<%= new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date()) + (new java.util.Random().nextInt(9632 - 1234) + 1234) %>" />
  <input type="hidden" name="product_id" value="<%= (new java.util.Random().nextInt(99999 - 1111) + 1111) %>" />
  
   <label><span style="text-align: left;">Description about payment: <span class="required">*</span></span><textarea class="textarea-field" required name="notesValue"></textarea></label>
<input type="hidden" name="product_quantity" value="1" />
<label><span>&nbsp;</span><input type="submit" style="width: 170px;"  class="btn-sbmt" name="submit" value="Submit" /></label>
</form>
<script>
function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
</script>
</div>
</div>
</section>
<footer class="footer">
	<div class="container">
		Copyright 2016. Cancure Foundation.
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