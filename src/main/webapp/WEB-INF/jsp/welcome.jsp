
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
	<!DOCTYPE html>
		<html>
			<head>
				<link rel="stylesheet" href="/resources/css/CSS.css">
			</head>
    		<h2><fmt:message key="welcome"/></h2>
    		<div class="row">
        		<div class="col-md-12">
            		<spring:url value="/resources/images/pets2.jpg" htmlEscape="true" var="petsImage"/>
            		<img class="img-responsive" src="${petsImage}"/>
        		</div>
    		</div>
    	</html>
</petclinic:layout>
