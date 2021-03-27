<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<petclinic:layout pageName="error">
<!DOCTYPE html>
		<html>
			<head>
				<link rel="stylesheet" href="/resources/css/CSS.css">
			</head>
    <spring:url value="/resources/images/pets.png" var="petsImage"/>
    <img src="${petsImage}"/>

    <h2><spring:message code="exception" text="default"/></h2>
    
    <p>${exception.message}</p>
</html>
</petclinic:layout>
