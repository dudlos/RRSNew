<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
 p{ color: blue; margin: 10px;} label {color: red } 
/* div#map { position: fixed; left: 500px; top: 150px;  width: 25%; height: 50%; z-index: 1; background-color: #e0ffff;; 
    border-style: outset;} */
div#selectMenu{position: relative; top: 1px;  z-index: 1; width: 25%; }
/* div#tableDiv{position: fixed; } made the table to addjust to the menuselect accordingly*/
/*  button#adMenuButton {position: relative; left:125px; top: 95px}   */
body{ background-color: #f9f9f9; }
 

 fieldset { border: 0;} select {width: 200px;}.overflow {height: 200px;}  
 #myTable {	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;	width: auto;	border-collapse: collapse;} 
#myTable td, th {	font-size: 1em;	border: 1px solid #98bf21;	padding: 3px 7px 2px 7px;}
#myTable th {	font-size: 1.1em;	text-align: left;	padding-top: 5px;	padding-bottom: 4px;	background-color: #A7C942;	color: #ffffff;}
#myTable tr.alt td {color: #000000;	background-color: #EAF2D3;}
#myTable tbody  tr {	cursor: pointer;}  
#myTable {position: relative; /* left:500px;  top: 500px ; width: 500px; } 
 #p1{position: absolute}  
 .ui-dialog .ui-state-error { padding: .3em; }
 
</style>
<title>AddMenu</title>	
<script type="text/javascript">
var url = decodeURIComponent(window.location.search.substring(1));
 var newString = url.split(["="], [2]);
 var paramJson = {"reservationID" : newString[1]};
 

 </script>
<script type="text/javascript">
var numberOfGuests;
var index = 0;
var reservationID;
var menus;
var reservationID = paramJson.reservationID;
var menuTypes = ["Starter", "Side", "Main", "Dessert", "Salad"];
var submitPar = {};

</script>
<script type="text/javascript">
var restaurantResultSet;
	$(document)
			.ready(function retrieveReservation() {
						$.getJSON("${pageContext.request.contextPath}/api/getReservation",	paramJson , function(restaurantResultSet) {
						$(function() { 
						$("#map").fadeIn();
						restaurantID	 = restaurantResultSet.newReservation.restaurant.restaurantID;
						reservationID2 = restaurantResultSet.newReservation.reservationID; $("#reservations").append($('<label />', {'text': reservationID2}, {class :"ui-widget ui-widget-content"}));
						var date = restaurantResultSet.newReservation.bookingDate; 	$("#dates").append($('<label />' , {'text' : date},{class :"ui-widget ui-widget-content"}));
						numberOfGuests = restaurantResultSet.newReservation.numberOfPerson; $("#guests").append($('<label />', {	'text' : numberOfGuests}));
						var table = restaurantResultSet.newReservation.table.tableNumber; $("#tables").append($('<label />', {'text' : table}));
						var restaurant = restaurantResultSet.newReservation.restaurant.restaurantName; $("#restaurants").append($('<label />', {'text' : restaurant }));
						var time = restaurantResultSet.newReservation.reservationTime; $("#times").append($('<label />', {'text' : time }));
											
							}) })  })
</script>		

<script type="text/javascript">
$(document).ready(function ConfirmMenu (){$("#adMenuButton").on({
    mouseenter: function(){
        $(this).css("background-color", "lightgray");
    }, 
     mouseleave: function(){
        $(this).css("background-color", "white");
    },   
    click: function(){
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
	      close: function() {$("#adMenuButton").fadeIn();},
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
				 i = 0; 
				 $("#tableDiv").fadeIn();
				$("#myTable").fadeIn();
				$("#tableDiv").css({ opacity: 0.9})
				 if(!($.isEmptyObject(data)))   
				{
				$.each(data.menus, function(i,value) {
				var select = $("" + '#'+ menu);
				var option =  $('<option value="' + "" + '">' + "" +  '</option>');
				var option = $('<option value="' + value.menuID + '">' + value.dish +  '</option>');
				submitPar[value.menuType]=value.menuID ;
				/* for (var i in submitPar)
					{
					alert(submitPar[i])}; or alert(Object.keys(obj)[1]); */
				select.append(option[0]);
				$('table#myTable TBODY').append(
						'<tr><td>' + value.menuID + '</td><td>' + value.menuType
								+ '</td><td>' + value.dish + '</td><td>'  + value.dishDescription + '</td><td>'+ value.price_$ + '</td></tr>'); 				
									});}
						 
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
  <script type="text/javascript">
 $(document).ready(function(){$("#addMenu")
	  .on
	 ({
		 mouseenter: function(){
		        $(this).css("background-color", "lightgray");
		    }, 
		     mouseleave: function()
		     {
		        $(this).css("background-color", "lightblue");
		     },
		     click:  function addMenu()
		     {
		    	
		    	 var requestData = { "reservationID": reservationID };
		    	 var starterID = $( "#Starter" ).val(); if(starterID  != null){  requestData.menuID1 = starterID;};//adding object k/v to existing object knowing the key
		    	 var sideID = $( "#Side" ).val(); if(sideID != null){  requestData.menuID2 = sideID;};
		    	 var mainID = $("#Main").val(); if(mainID != null){  requestData.menuID3 = mainID;};
		    	 var dessertID = $("#Dessert").val(); if(dessertID != null){  requestData.menuID4 = dessertID;};
		    	 var saladID = $("#Salad").val(); if(saladID != null){  requestData.menuID5 = saladID;};
		     	 
		    	 $.post("${pageContext.request.contextPath}/api/addMenus", requestData, function(result){menus = result})
		    	   	 .done(function() {
		    		 $("#tableDiv").fadeOut();
		    		 $("#selectMenu").fadeIn();
		    		 $("#myTable  tbody > tr").remove();
		    		  requestData = null; starterID = null;  sideID = null; mainID  = null; dessertID = null; dessertID = null;
		    		 $( "#Side").prop('selectedIndex',0);
		    		 $("#mainOption").prop('selectedIndex',0);
		    		 $("#Dessert").prop('selectedIndex',0);
		    		 $("#Salad").prop('selectedIndex',0);
		    		 $("#selectMenu").fadeOut();
		    		 $("#map").fadeIn();
		    		 alert("Success:Your Menu has bee adedded to the list");
		    		/*  location.reload(); */
		    		 
						    		 /*  $.getJSON("${pageContext.request.contextPath}/api/findAllMenusPerReservation", paramJson, function(menuList)
												{
													menus = menuList.dish;
													menuLabel = "menuLabel";
													if(menus == null) 
												{
													$("#menuu").append($('<label>', { 'text' :"No menu selected yet"}));
												}
												else
													{$("#menuu").append($('<label />', {'text' : menus }))}}) */
		    	 })
		    	 .fail(function()	{alert("Not able to process your request at the moment please select the menu again");}) 
		     }
		     
	 })})
			
  </script>
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
<!--xxxx-->
<link  href= "${pageContext.request.contextPath}/resources/css/styles.css"  rel="stylesheet" type="text/css" >
<!--xxxx-->
  
</head>

<body>
<div class="pagewrapper"> <!--xxxx-->

<h2>Your Reservation Confirmation</h2>
<div id = "selectMenu" class = "ui-widget"  style="display: none;">
<form action="#">
 
 <fieldset>
    <label for="starters" style = "display: block; margin: 30px 0 0 0;" >Select from Starters</label>
    <select name="starter" id="Starter"><option value=""  disabled selected>Please Select</option>
     </select>
 
    <label for="sides" style = "display: block; margin: 30px 0 0 0;" >Select a Side Dish</label>
    <select name="files" id="Side"><option value="" id=""   disabled selected>Please Select</option>
      <!-- <optgroup label="Scripts"> -->
      </select>
 
    <label for="mains" style = "display: block; margin: 30px 0 0 0;" >Select a Main Course</label>
    <select name="main" id="Main"><option value="" id="mainOption"   disabled selected>Please Select</option>
    </select>
    
    <label for="desserts" style = "display: block; margin: 30px 0 0 0;" >Select a Dessert</label>
    <select name="dessert" id="Dessert"><option value="dll" id=""   disabled selected>Please Select</option>
    </select>
     
     <label for="salads" style = "display: block; margin: 30px 0 0 0;" >Select a Salad</label>
    <select name="salad" id="Salad"><option value="" id="dll"  disabled selected>Please Select</option>
     </select>
  </fieldset>
 </form>
 <button id = "addMenu" type = "submit">Add To Reservation</button>
</div>
<div id = "map"  class='jqm-demos ui-page ui-page-theme-a ui-page-active '  style = "display: none"> <!-- ui-page-footer-fixed  -->
				<p id = "reservations">Unique Reservation Number: </p>
				<p id ="dates">Date: </p>
				<p id = "guests">Number of Guests: </p>
				<p id = "tables">Table ID: </p>
				<p id = "restaurants">Restaurant: </p>
				<p id = "times">Reservation Time: </p>
				<p id = menuu>Menus: </p>
<button id = "adMenuButton" class="ui-widget">Add Menu</button>
</div>
<div id="dialogDiv" title="Basic dialog"  style = "display: none">
  	<p>Please Note that you can add only one menu per person at one time</p>
</div>

<div id="tableDiv" >
	<table id="myTable" class="ui-widget ui-widget-content" 	style="display: none;">
			<thead>
				<tr class="ui-widget-header">
					<th>Menu ID</th>
					<th>Menu Type</th>
					<th>Dish</th>
					<th>DishDescription</th>
					<th>Price_$</th>
				</tr>
			</thead >
			<tbody>
			</tbody>
	</table>
</div>

</div>
<!--xxxx pagewrapper end-->

</body>
</html>

