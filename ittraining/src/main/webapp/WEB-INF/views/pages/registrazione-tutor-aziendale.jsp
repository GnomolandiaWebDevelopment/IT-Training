<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
		<!-- Favicon -->
		<link rel="shortcut icon" href="resources/images/logo.png" />
		
		<meta charset="ISO-8859-1">
		
		<title>IT Training - Registrazione tutor aziendale</title>
		
	    <!--Import Google Icon Font-->
	    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
      
		<!-- Compiled and minified CSS -->
	    <link rel="stylesheet" href="../resources/css/materialize.min.css">
	    <link rel="stylesheet" href="../resources/css/materialize.css">
	
	    <!-- Compiled and minified JavaScript -->
	    <script src="../resources/js/jquery.min.js" type="text/javascript"></script>
	    <script src="../resources/js/materialize.min.js" type="text/javascript"></script>
	    <script src="../resources/js/initialize.js"></script>
	    <script src="../resources/js/toast.js"></script>
		
	    
	    <!--Import Google Icon Font-->
	    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
		<!-- Definizione messaggi -->
		<spring:message var="notifica" code="${testoNotifica}"/> 
	
		<input id="toast" type="hidden" value="${notifica}"/>
		
	<jsp:include page="/WEB-INF/views/nav/nav-home.jsp" />
	
	<!-- Sidenav -->
	<jsp:include page="/WEB-INF/views/nav/side-nav.jsp" />
		  
	<div class="container">
		
	    <div class="row"></div>
		<div class="row"></div>
		<div class="row">
			<h4 class="teal-text">Registrazione - Tutor Aziendale</h4>
		</div>
	    <div class="row"></div>
		<div class="row"></div>
		
		<jsp:include page="/WEB-INF/views/forms/registrazione-tutor-aziendale.jsp" />
	</div>
</body>
</html>