<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">

	
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">

/* div#selectMenu{position: relative; top: 1px;  z-index: 1; width: 25%; }   */

 

 fieldset { border: 0;} select {width: 200px;}.overflow {height: 200px;}   

 .ui-dialog .ui-state-error { padding: .3em; }
 
</style>
<title>AddMenu</title>	
<script type="text/javascript">
/* var url = decodeURIComponent(window.location.search.substring(1));
 var newString = url.split(["="], [2]);
 var paramJson = {"reservationID" : newString[1]}; */
 
 var reservationID = ${reservationID};
 var paramJson = {"reservationID" : reservationID};

 

 </script>
<script type="text/javascript">
var numberOfGuests;
var index = 0;
var menus;
var menuTypes = ["Starter", "Side", "Main", "Dessert", "Salad"];
/* var submitPar = {}; */

</script>
<script type="text/javascript">
var restaurantResultSet;
	$(document)
			.ready(function retrieveReservation() {
				 		$.getJSON("${pageContext.request.contextPath}/api/getReservation",paramJson , function(restaurantResultSet) {
						$(function() { 
						$("#map").fadeIn();
						restaurantID	 = restaurantResultSet.newReservation.restaurant.restaurantID;
						reservationID2 = restaurantResultSet.newReservation.reservationID; $("#reservations").append($('<label />', {'text': reservationID2}, {class :"ui-widget ui-widget-content"}));
						var date = restaurantResultSet.newReservation.bookingDate; 	$("#dates").append($('<label />' , {'text' : date},{class :"ui-widget ui-widget-content"}));
						numberOfGuests = restaurantResultSet.newReservation.numberOfPerson; $("#guests").append($('<label />', {	'text' : numberOfGuests}));
						var table = restaurantResultSet.newReservation.table.tableNumber; $("#tables").append($('<label />', {'text' : table}));
						var restaurant = restaurantResultSet.newReservation.restaurant.restaurantName; $("#restaurants").append($('<label />', {'text' : restaurant }));
						var time = restaurantResultSet.newReservation.reservationTime; $("#times").append($('<label />', {'text' : time }));
						getAllMenusReservation();
											
							}) })  })
</script>		

<script type="text/javascript">
$(document).ready(function generateMenuTable (){$("#adMenuButton").on({
    mouseenter: function(){
        $(this).css("background-color", "lightgray");
    }, 
       mouseleave: function(){
        $(this).css('background-color', ""); 
    },   
    click: function(){
    	alert(index);
    	activateDialog ();
    	if(index <  numberOfGuests){index = index + 1;
    	addSingleMenu();}
    	else{$("#adMenuButton").prop('disabled', true)};
    	
    	},
});});
</script>

<script type="text/javascript">
function addSingleMenu(){
	 dialog.dialog("open");
	$("#adMenuButton").fadeOut(); 
	
	
}
</script>					
<script type="text/javascript">
var dialog;
function activateDialog (){
	
	dialog = $("#dialogDiv").dialog({ autoOpen: false, height: 300,width: 350,
	      modal: true,
	       buttons: {
	    	  "Create Menu": function() { getAllMenus();},
	          Cancel: function(){
	            dialog.dialog( "close" )}
	      },
	      close: function() { $("#adMenuButton").fadeIn(); },
	    });
	}
</script>
<script type="text/javascript">
	function  getAllMenus() {
	dialog.dialog( "close" );
	$("#map").fadeOut();
	$("#selectMenu").fadeIn();
		
	$(function() { $.each(menuTypes, function(i, item) {	
			var JsonID = {};
			var menu = item; 
			JsonID = {"restaurantID": restaurantID, "menuType": menu};	 
			$.getJSON("${pageContext.request.contextPath}/api/getAllMenusForMenuType",JsonID , function(data) 
				{
				 $("#tableDiv").fadeIn();
			 	$("#tableDiv").css({ opacity: 0.9})
				 if((!($.isEmptyObject(data))) && index == 1)   
				{
				$.each(data.menus, function(i,value) {
				var select = $("" + '#'+ menu);
				var option =  $('<option value="' + "" + '">' + "" +  '</option>');
				var option = $('<option value="' + value.menuID + '">' + value.dish +  '</option>');
				select.append(option[0]);
				$('table#myTable TBODY').append(
						'<tr><td>' + value.menuID + '</td><td>' + value.menuType
								+ '</td><td>' + value.dish + '</td><td>'  + value.dishDescription + '</td><td>'+ value.price_$ + '</td></tr>'); 				
									});}
				 else{
					 if(!($.isEmptyObject(data)))
						 {
						 $.each(data.menus, function(i,value) {
						 $('table#myTable TBODY').append(
									'<tr><td>' + value.menuID + '</td><td>' + value.menuType
											+ '</td><td>' + value.dish + '</td><td>'  + value.dishDescription + '</td><td>'+ value.price_$ + '</td></tr>'); })
						 }
				 }
						 
					})
	}
	);})
	}
		
		
</script><!-- Starter, Side, Main, Dessert, Salad -->
<script type="text/javascript">
$(document).ready(function selectedMenus(){
$( "#Starter" ).selectmenu();
$( "#Side" ).selectmenu();
$( "#Main" ).selectmenu(); 
$("#Dessert").selectmenu();
$( "#Salad" ).selectmenu().selectmenu( "menuWidget" ).addClass( "overflow" );
});
 </script>
 
 <sec:csrfMetaTags />
  <!--  If CSRF protection is enabled, this tag inserts meta tags containing the CSRF protection token form field 
and header names and CSRF protection token value. These meta tags are useful for employing CSRF protection within
JavaScript in your applications. You should place csrfMetaTags within an HTML <head></head> block, 
where you would normally place other meta tags. Once you use this tag, you can access the form field name, 
header name, and token value easily using JavaScript. 
http://docs.spring.io/spring-security/site/docs/current/reference/html/taglibs.html#the-csrfmetatags-tag
 --> 
<script type="text/javascript">
var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
			var csrfToken = $("meta[name='_csrf']").attr("content");
			
 $(document).ready(function(){$("#addMenu")
	  .on
	 ({
		 mouseenter: function(){
		        $(this).css("background-color", "lightgray");
		    }, 
		     mouseleave: function()
		     {
		        $(this).css("background-color", "");
		     },
		     click:  function addMenu()
		     {
		    	
		    	 var requestData = { "reservationID": reservationID };
		    	 var starterID = $( "#Starter" ).val(); if(starterID  != null){  requestData.menuID1 = starterID;};//adding object k/v to existing object knowing the key
		    	 var sideID = $( "#Side" ).val(); if(sideID != null){  requestData.menuID2 = sideID;};
		    	 var mainID = $("#Main").val(); if(mainID != null){  requestData.menuID3 = mainID;};
		    	 var dessertID = $("#Dessert").val(); if(dessertID != null){  requestData.menuID4 = dessertID;};
		    	 var saladID = $("#Salad").val(); if(saladID != null){  requestData.menuID5 = saladID;};
		    	 requestData[csrfParameter] = csrfToken;
		    	 
		    	 $.post("${pageContext.request.contextPath}/api/addMenus", requestData, function(result){menus = result})
		    	   	 .done(function() {
		    	   	 $("#myTable  tbody > tr").remove();
		    		 $("#tableDiv").fadeOut();
		    		 $("#selectMenu").fadeIn();
		    		 requestData = null; starterID = null;  sideID = null; mainID  = null; dessertID = null; saladID = null;
		    		  
		    	 $( "#Starter").prop('selectedIndex',0).selectmenu( "refresh" );
		    		 $( "#Side").prop('selectedIndex',0).selectmenu( "refresh" );
		    		 $("#Main").prop('selectedIndex',0).selectmenu( "refresh" );
		    		 $("#Dessert").prop('selectedIndex',0).selectmenu( "refresh" );
		    		 $("#Salad").prop('selectedIndex',0).selectmenu( "refresh" ); 
		    		 $("#selectMenu").fadeOut();
		    		 getAllMenusReservation();
		    		 $("#map").fadeIn();
		    		
		    		/*  location.reload(); */
		    		 
				 })
		    	 .fail(function()	{alert("Not able to process your request at the moment please select the menu again");}) 
		     }
		     
	 })})

			
  </script>
  <script type="text/javascript">
function getAllMenusReservation(){
  $.getJSON("${pageContext.request.contextPath}/api/findAllMenusPerReservation", paramJson, function(data)
			{
	  			$("#menuu label").remove();
	  			if($.isEmptyObject(data)) 
				{
				$("#menuu").append($('<label>', { 'text' : "No menu selected yet"}));
				}
	  			else{
	  				$.each(data, function(i,value) {
	  					menus = value.dish;
	  									{$("#menuu").append($('<label />', {'text' : menus + ", " }))  
	  									}; 
	  					})}
	  			
	  		
				
  })}
  </script>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'> 
<!--xxxx-->
<link  href= "/resources/css/styles.css"  rel="stylesheet" type="text/css" >
<!--xxxx-->	
  
</head>

<body>
<div class="pagewrapper"> <!--xxxx-->
<h2>Your Reservation Confirmation</h2>
<div id = "map"  class='jqm-demos ui-page ui-page-theme-a ui-page-active'  style = "display: none" > 
				<p id = "reservations">Unique Reservation Number: </p>
				<p id ="dates">Date: </p>
				<p id = "guests">Number of Guests: </p>
				<p id = "tables">Table ID: </p>
				<p id = "restaurants">Restaurant: </p>
				<p id = "times">Reservation Time: </p>
				<p id = "menuu">Menus: </p>
<button id = "adMenuButton" class="ui-widget">Add Menu</button>
</div>

<div id="dialogDiv" title="Basic dialog"  style = "display: none">
  	<p>Please Note that you can add only one menu per person at one time</p>
</div>
<div id = "selectMenu" class = "ui-widget"  style="display: none">
<form action="#">
 <fieldset>
    <label for="starters" style = "display: block; margin: 30px 0 0 0;" >Select from Starters</label>
    <select name="starter" id="Starter"><option value=""  >Please Select</option>
     </select>
 
    <label for="sides" style = "display: block; margin: 30px 0 0 0;" >Select a Side Dish</label>
    <select name="files" id="Side"><option value=""   >Please Select</option>
      <!-- <optgroup label="Scripts"> -->
      </select>
 
    <label for="mains" style = "display: block; margin: 30px 0 0 0;" >Select a Main Course</label>
    <select name="main" id="Main"><option value="" >Please Select</option>
    </select>
    
    <label for="desserts" style = "display: block; margin: 30px 0 0 0;" >Select a Dessert</label>
    <select name="dessert" id="Dessert"><option value="">Please Select</option>
    </select>
     
     <label for="salads" style = "display: block; margin: 30px 0 0 0;" >Select a Salad</label>
    <select name="salad" id="Salad"><option value="">Please Select</option>
     </select>
  </fieldset>
 </form>
 <button id = "addMenu" type = "submit">Add To Reservation</button>
</div>


<div id="tableDiv"   style="display: none">
	<table id="myTable" >
			<thead>
				<tr >
					<th>Menu ID</th>
					<th>Menu Type</th>
					<th>Dish</th>
					<th>DishDescription</th>
					<th>Price_$</th>
				</tr>
			</thead >
			<tbody >
			</tbody>
	</table>
</div>

</div>
<!--xxxx pagewrapper end-->

</body>
</html>

