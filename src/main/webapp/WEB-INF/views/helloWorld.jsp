<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
	function crunchifyAjax() {
		$.ajax({
			url : 'ajaxtest',
			data: {restaurantID:2136},
			success : function(data) {
				$('#result').html(data);
			},
			error: function(exception) {
				$('#result').html(exception);
			}
		});
	}
	
	function menusForRestaurant(restaurantId) {
		$.ajax({
			url : 'menusForRestaurant',
			data: {restaurantID:2136},
			success : function(data) {
				$('#menusForRestaurant').html(data);
			},
			error: function(exception) {
				$('#menusForRestaurant').html(exception);
			}
		});
	}
	
	
</script>

<script type="text/javascript">

	var intervalId = 0;
	intervalId = setInterval(crunchifyAjax, 10000);
	$(function(){$("#restaurants").change(function() {
		alert("BUFUUFUFUFUFU"); //this.value
		//menusForRestaurant(2136);
	});
	});
	
	
	//alert($('#restaurants'));
	
</script>
</head>
<body>
	<h1>Spring 4.0.2 MVC web service</h1>

	<select id="restaurants">
		 <c:forEach var="listVar" items="${bufu}"> 
		 <%-- <c: means declaring a core custom tag this e:forEach but setting up a variable
		 c:set var=..." /> --%>
	    	<option value="2136"><c:out value="${listVar.restaurantName}"/></option>
		
		<option value="10000">Test restaurant</option> 
		</c:forEach>
	</select>
	
	<button onclick="menusForRestaurant()">Klik</button>
		
	<div id="result"></div>
	
	<div id="menusForRestaurant"></div>
</body>
</html>