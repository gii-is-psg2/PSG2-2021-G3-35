<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="names" required="true" rtexprvalue="true" type="java.util.List"
              description="Names in the list" %>

<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">${label}</label>

        <div class="col-sm-10">
        	<c:forEach items="${listSpecialties}" var="specialty">
        	
        		<label for="${specialty}">
                           <form:checkbox value="${specialty}" path="specialties"/>
                           <spring:message code="${specialty}" />
                </label>
        	
        	
        	</c:forEach>
            
         
        </div>
    </div>
</spring:bind>
