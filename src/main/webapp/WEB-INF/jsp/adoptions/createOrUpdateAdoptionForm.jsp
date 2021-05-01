<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2>
        <c:if test="${adoption['new']}"><spring:message code="new" text="default"/>  </c:if> &nbsp; <spring:message code="adoption" text="default"/>
    </h2>
    <form:form modelAttribute="adoption" class="form-horizontal" id="add-adoption-form">
        <div class="form-group has-feedback">
        	<spring:message code="title" var="title"/>
        	<spring:message code="description" var="description"/>
    		<spring:message code="pets" var="pets"/>
            <petclinic:inputField label="${title}" name="title"/>
           <petclinic:inputField label="${description}" name="description"/>
            <petclinic:selectField label="${pet}" name="pet" names="${petsList}" itemvalue="id" itemlabel="name" size="3"/>
        	<form:input path="owner" value="${owner}" type="hidden"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                
                        <button class="btn btn-default" type="submit"><spring:message code="addadoption"/></button>
           
            </div>
        </div>
    </form:form>
</petclinic:layout>
