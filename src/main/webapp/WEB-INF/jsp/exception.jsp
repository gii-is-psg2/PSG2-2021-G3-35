<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<petclinic:layout pageName="error">

    <spring:url value="/resources/images/error.jpg" var="petsImage"/>
    <img id="perroError" src="${petsImage}"/>

    <h2  style="font-size: xx-large;margin-left: 41%;margin-top: 2%"><spring:message code="exception" text="default"/></h2>
    
    <p>${exception.message}</p>

</petclinic:layout>
