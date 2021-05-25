<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*"%>

<petclinic:layout pageName="vets">
    <h2>
        <c:if test="${vet['new']}"><spring:message code="new" /> &nbsp; </c:if> <spring:message code="vet" />
    </h2>
    
    <form:form modelAttribute="vet" class="form-horizontal" id="add-vet-form">
        <div class="form-group has-feedback">
        
        <spring:message code="firstname" var="firstname"/>
        <spring:message code="lastname" var="lastname"/>
         <spring:message code="specialties" var="specialties"/>
            <petclinic:inputField label="${firstname }" name="firstName"/>
            <petclinic:inputField label="${lastname }" name="lastName"/>
			<petclinic:checkboxes label="${specialties}" name="specialties" names="${listSpecialties}"/>
        	
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${vet['new']}">
                        <button class="btn btn-default" type="submit"> <spring:message code="addvet"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"> <spring:message code="updatevet"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>