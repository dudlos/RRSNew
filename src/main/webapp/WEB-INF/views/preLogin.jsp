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



 

 fieldset { border: 0;} select {width: 200px;}.overflow {height: 200px;}   

 .ui-dialog .ui-state-error { padding: .3em; }
 
</style>
<title>Confirm Reservation</title>	

<script type="text/javascript">
/* var url = decodeURIComponent(window.location.search.substring(1));
 var newString = url.split(["="], [2]);
 var paramJson = {"reservationID" : newString[1]}; */

 var reservationDate2 = "${reservationDate}";
 var reservationDate1 = reservationDate2.toString();
 var reservationTime2 = "${reservationTime}";
 var reservationTime1 = reservationTime2.toString();
 var numberOfPerson1 = ${numberOfPerson};
 var restaurantID1 = ${restaurantID};
 var tableID1 = ${tableID};
 var userID1 = ${userID};
 var jsonPost;
 
  
 </script>
 
  
 <sec:csrfMetaTags />
 <script type="text/javascript">
 
 var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
 			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 			var csrfToken = $("meta[name='_csrf']").attr("content");

 </script>
 <script type="text/javascript">

 function makeReservation() {	
 jsonPost = {
 "reservationDate" : reservationDate1, "reservationTime" : reservationTime1, "numberOfPerson" : numberOfPerson1, "restaurantID" : restaurantID1,
 "tableID" : tableID1, "userID": userID1};
  jsonPost[csrfParameter] = csrfToken; 
 $.post("${pageContext.request.contextPath}/api/makeReservation", jsonPost  , function(result){reservationID = result;  }  ) 
  .done(function(){ 
	  alert("Booking  not failed");
	  goToSuccessBooking()})
 
 }
</script>
  <script type="text/javascript">
 function goToSuccessBooking()
 {
 location.href = ("${pageContext.request.contextPath}/api/tableBooking/?reservationID=" + reservationID);
 }
 </script>
<script type="text/javascript">
makeReservation();
</script>

<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'> 
<!--xxxx-->
<link  href= "/resources/css/styles.css"  rel="stylesheet" type="text/css" >
<!--xxxx-->	
  
</head>

<body>
<div class="pagewrapper"> <!--xxxx-->
<h2>Your Reservation Confirmation</h2>

</div>
<!--xxxx pagewrapper end-->

</body>
</html>

