<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
        <h2><spring:message code="new"/>&nbsp;<spring:message code="donation"/></h2>
		
		<spring:message code="amount" var="amount"/>
		
        <form:form modelAttribute="donation" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="${amount}" name="amount"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="owner" value="${owner.id}"/>
                    <input type="hidden" name="cause" value="${causeId}"/>
                    <button class="btn btn-default" type="submit"><spring:message code="donate"/></button>
                </div>
            </div>
        </form:form>
        
</petclinic:layout>
