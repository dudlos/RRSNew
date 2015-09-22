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
<title>RRS</title>
<style type="text/css">
div#current-selection { position: absolute; /* fixed; */ 
    left: 525px; top: 325px; width: 35%; height: 50%; z-index: 1; background-color: rgba(255,255,0,0.3); 
    border-style: outset;}
//button#make-reservation{position: absolute;top: 50%;}
#myTable {	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;	width: auto;	border-collapse: collapse}
#myTable td, th {	font-size: 1em;	border: 1px solid #98bf21;	padding: 3px 7px 2px 7px;}
#myTable th {	font-size: 1.1em;	text-align: left;	padding-top: 5px;	padding-bottom: 4px;	background-color: #A7C942;	color: #ffffff;}
#myTable tr.alt td {color: #000000;	background-color: #EAF2D3;}
#myTable tbody  tr {	cursor: pointer;}
.ui-dialog .ui-state-error { padding: .3em; }

body {
	background-color: #e0ffff;
	background-image: url("${pageContext.request.contextPath}/resources/images/spencers-restaurant-bg-dining-1_54_990x660_201404212140.jpg");
	background-repeat: repeat-x; background-position: center center;
	font: helvetica;
}
#superDiv {
    margin: auto;
    width: 60%;
    border:3px solid #8AC007;
    padding: 10px;
    background-color: #CDB3E4
}
</style>
<script type="text/javascript">
	//all global variables declaration
	var RestaurantResultSet;//used to get and store list of all restaurants with properties:restaurantName, restaurantID
	var restaurantIDPr; //retrieves a restaurantID(from RestaurantResultSet) name based on restaurantName
	var restaurantNamePr;// current restautaurant name
	var reservationTimes;//used to get all reservationTimes from getJson call
	var checkedRadio;//stores the value of selected radio for reservationTime
	var numberOfGuests;//as numberOfPerson by client
	var tablesCapacity;
	var listOfTables;
	var date;//reservationDate
	var resultTableName;//selected table name
	var resultTableID;//selected table ID
	var reservationIDRsp;
	var dialog;
	var reservationID;
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#datepicker").datepicker({
			minDate : 0,
			maxDate : "+1M",
			dateFormat : "yy-mm-dd"
		});

	});
</script>

<script type="text/javascript">
	$(document)
			.ready(			
					function retrieveAllRestaurants() {
						$.getJSON("${pageContext.request.contextPath}/api/getAllRestaurants",	function(data) {
											RestaurantResultSet = data;
											$(function() {$	.each(RestaurantResultSet, function(i,	item) {
																	var select = $("#restaurantList");
																	var option = $('<option value="' + i + '">' + item.restaurantName
																			+ '</option>');
																	select.append(option[0]);
																});
																resetHomePage();
														});});});
</script>

<script type="text/javascript">
	$(document).ready(function getRestaurantID() {
		$("#restaurantList").change(function() {
			restaurantNamePr = $("#restaurantList option:selected").text();
			$.each(RestaurantResultSet, function(i, item) {
			if (item.restaurantName == restaurantNamePr) {restaurantIDPr = item.restaurantID}
			$("#dateDiv").fadeIn("slow");
			$("#datepicker").val("");
			$("#myTable").fadeOut();
			removeRadios();
			$("#submitDate").fadeOut();
			$("#current-selection").fadeOut();
			});
			})
	});
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function getDate() {
						$("#datepicker").change(function() {
						$("#submitDate").fadeIn("slow");
							removeRadios();
							$("#myTable").fadeOut("fast");
							$("#submitDate").prop('disabled', false);
							$("#current-selection").fadeOut();
						})
						$("#submitDate")	.on("click",function(){date = $("#datepicker").val();
											var json = {
												"reservationDate" : date,
												"restaurantID" : restaurantIDPr};
											$.getJSON("${pageContext.request.contextPath}/api/checkAvailibilityDate",	json,	function(data) {
																reservationTimes = data;
																radioFunction();
																$("#submitDate").prop('disabled', true);
																});
										});
					});
</script>

<script type="text/javascript">
	function removeRadios() {
		$('#radioDiv').children().remove()
		$("#numberOfPerson").fadeOut();
	};
</script>
<script type="text/javascript">
function resetHomePage()
{
			$("#datepicker").val("");
			$("#dateDiv").fadeOut("slow");
			$("#myTable").fadeOut();
			removeRadios();
			$("#submitDate").fadeOut();
			$("#current-selection").fadeOut();
};
</script>

<script type="text/javascript">
	//populates radio buttons with available reservationTimes
	function radioFunction() {
		$('#radioDiv').fadeIn();
		for (var i = 0; i < reservationTimes.freeSlots.length; i++) {
		$("#radioDiv").append($('<label />', {
		'text' : reservationTimes.freeSlots[i]	}).prepend($('<input />', {type : 'radio',	name : 'radio',	id : 'radio', 
		class :"ui-widget ui-widget-content" + i,value : reservationTimes.freeSlots[i]})
		));
		};
	};
</script>

<script type="text/javascript">
	$(document).ready(function getRadioAndCapacity() {
		$("#radioDiv").change(function() {
			$("#persons").children().remove();
			$("#myTable").fadeOut("fast");
			$("#current-selection").fadeOut();
			$('#myTable tbody > tr').remove();
			checkedRadio = $('input:radio[name=radio]:checked').val();
			$("#numberOfPerson").fadeIn("slow");
			tablesCapacity = 5;//only temporary to simulate a number of person capacity
			for (var i = 0; i < tablesCapacity; i++) {
				var select = $("#persons");
				var option = $('<option value="' + i + '">' + i + '</option>');
				select.append(option[0]);
				};
				$("#persons option:first").attr('selected','selected');
				$("#persons option:first").prop('disabled', true);
		});
	});
</script>

<script type="text/javascript">
	$(document)	.ready(	function() {$("#persons").change(
										function getAllAvailableTables() { numberOfGuests = $("#persons option:selected").val();
											$("#myTable").fadeOut("fast");
											$("#current-selection").fadeOut();
											$('#myTable tbody > tr').remove();
											listOfTables = null;
											var json2 = {
												"restaurantID" : restaurantIDPr,
												"reservationDate" : date,
												"reservationTime" : checkedRadio,
												"numberOfPerson" : numberOfGuests};
												$.getJSON("${pageContext.request.contextPath}/api/checkAvailibilityTime", json2,
															function(data2) {	listOfTables = data2;	
															populateTablesTree();})
														})}); 
															
</script>

<script type="text/javascript">
	function populateTablesTree() {
		$("#myTable").fadeIn("slow");
		$("#tableDiv").css({ opacity: 0.9})
		$.each(listOfTables.freeTables, function(idx, elem) {
			$('table#myTable TBODY').append(
					'<tr><td>' + elem.tableID + '</td><td>' + elem.tableNumber
							+ '</td><td>' + elem.capacity + '</td></tr>');
		});
		activateDialog ();
		if($.isEmptyObject(listOfTables.freeTables ))
																{;
																dialog.dialog("open");}
	};
</script>

<script type="text/javascript">
	var img;
	window.onload = function() {
		var c = document.getElementById("myCanvas");
		var ctx = c.getContext("2d");
		img = new Image();
		img.src = "${pageContext.request.contextPath}/resources/images/spencers-restaurant-bg-dining-1_54_990x660_201404212140.jpg";
		ctx.drawImage(img, 10, 10);
		
	}
</script>

<script type="text/javascript">
$(document).ready(function selectableRows(){
    $( "#myTable > tbody" ).selectable({
	     // Don't allow individual table cell selection.
        filter: ":not(td)",
         // When a row is selected, add the highlight class to the row and
        // update the total.
        selected: function( e, ui ) {
        resultTableID = null;
        resultTableName  = null;
        $( ui.selected ).addClass( "ui-state-highlight" );
            resultTableID = $(ui.selected).find("td").eq(0).html();
            resultTableName = $(ui.selected).find("td").eq(1).html();
           	populateSelectionDiv();
            //$(this).select(dialogInitial ());
          },
         // When a row is unselected, remove the highlight class from the row
        unselected: function( e, ui ) {
            $( ui.unselected ).removeClass( "ui-state-highlight" );
           }
           }); });
</script>

<script type="text/javascript">
function activateDialog (){
dialog = $("#dialogDiv").dialog({ autoOpen: false,
      height: 300,
      width: 350,
      modal: true,
      buttons: {
        "Acknowledge": function(){ },
        Cancel: function() {
          dialog.dialog( "close" );}
      },
      close: function() {alert("close");},
    });}
 </script>
  
<script type="text/javascript">
function populateSelectionDiv()
{
$("#current-selection").fadeIn();
$("#restaurantName").val(restaurantNamePr);
$("#reservationDate").val(date);
$("#reservationTime").val(checkedRadio);
$("#tableName").val(resultTableName);
$("#guests").val(numberOfGuests);
}
</script>
<script type="text/javascript">
$(document).ready(function(){$("#make-reservation")
.on({
    mouseenter: function(){
        $(this).css("background-color", "lightgray");
    }, 
     mouseleave: function(){
        $(this).css("background-color", "lightblue");
    },   
    click: function(){
        makeReservation();
    },
});});
</script>
<script type="text/javascript">

function makeReservation(){
var jsonPost;
jsonPost = {
"reservationDate" : date, "reservationTime" : checkedRadio, "numberOfPerson" : numberOfGuests, "restaurantID" : restaurantIDPr,
"tableID" : resultTableID};
$.post("${pageContext.request.contextPath}/api/makeReservation", jsonPost,  function(result){reservationID = result})
.done(function() {
	alert(reservationID);
    location.href = ("${pageContext.request.contextPath}/api/tableBooking/?reservationID=" + reservationID);
               
      }
   );
  
 };

 </script>


</head>

<body>
<div id = "superDiv" class="ui-widget">
<div id="mydiv" style="width: 100px height:200px" class="ui-widget">
		<select id="restaurantList" required="required" autofocus="autofocus"
			class="ui-widget ui-widget-content">
			<option value="" id="ddl" style="display: none;" disabled selected>Select
				Restaurant...</option>
		</select>
</div>


<div id="dateDiv" style="width: 300px height:600px; display: none" class="ui-widget">
		<p>Date: <input type="text" name="date" id="datepicker" onload="" contenteditable="false"></p>
		<button id="submitDate" type="submit" style="width: 300px height:600px; display: none; background-color: red;">
		Get Available Times</button>
</div>

<div id="radioDiv" style="display: none" class="ui-widget">	
	<select class="ui-widget ui-widget-content">
	</select>
</div>

<div id="numberOfPerson" style="display: none;" class="ui-widget">
		<p>Select Number of Guests </p><select id="persons" required="required"
				autofocus="autofocus">
				<option value="" id="personOption" style="display: none;" disabled selected>Select Guests...</option>
			</select>
</div>
</div>
<div id="tableDiv">
		<table id="myTable" class="ui-widget"
			style="display: none;">
			<thead>
				<tr class="ui-widget-header">
					<th>ID</th>
					<th>Table</th>
					<th>Capacity</th>
				</tr>
			</thead >
			<tbody class="ui-widget-content ui-state-default">
			</tbody>
	</table>
</div>

	<canvas id="myCanvas" width="240" height="297"
		style="border: 1px solid #d3d3d3; display: none"> Your browser does not support the HTML5 canvas tag.
	</canvas>

<div id="current-selection" class="ui-widget"  style = "display: none">
	<h3>Current Selection:</h3>
 	<fieldset>
	<table id="Selection" class="ui-widget ui-widget-content" >
	    <tr class="ui-widget-header "><td>Restaurant: </td><td><input type="text"  id = "restaurantName" /></td></tr>
	    <tr class="ui-widget-header "><td>Reservation Date: </td><td><input type="text"  id = "reservationDate" readonly="readonly"/></td></tr>
	    <tr class="ui-widget-header "><td>Reservation Time: </td><td><input type="text"  id = "reservationTime" readonly="readonly"/></td></tr>
	    <tr class="ui-widget-header "><td>Table: </td><td><input type="text" id = "tableName" readonly="readonly"/></td></tr>
	    <tr class="ui-widget-header "><td>Number of Guests: </td><td><input type="text"  id = "guests" readonly="readonly"/></td></tr>
	</table>
	</fieldset>
			<button type="submit" value="send" id="make-reservation" class="ui-widget" style="align: auto">Confirm your Selection</button>
</div>

<div id="dialogDiv" title="Basic dialog"  style = "display: none">
  	<p>No More Tables available for this time slot try a different time</p>
</div>
</body>
</head>
</html>