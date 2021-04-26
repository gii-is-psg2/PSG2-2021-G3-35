<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="petitions">
    <jsp:body>
        <h2><c:if test="${petition['new']}"><spring:message code="new" />&nbsp; </c:if><spring:message code="petition" /></h2>
		
		<spring:message code="description" var="description"/>
		<spring:message code="status" var="status"/>
		
        <form:form modelAttribute="petition" class="form-horizontal">
            <div class="form-group has-feedback">
            	
            	<div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="pet"/></label>
                    <div class="col-sm-10">
                        <c:out value="${adoption.pet.name}"/>
                    </div>
                </div>
    			
    			<div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="owner"/></label>
                    <div class="col-sm-10">
                        <c:out value="${adoption.owner.firstName} ${adoption.owner.lastName}"/>
                    </div>
                </div>
    			
                <petclinic:textareaField label="${description}" name="description"/>
                <spring:message code="${petition.status}" var="petitionStatus"/>
                      
                <div class="${cssGroup}">
        			<label class="col-sm-2 control-label">${status}</label>

        			<div class="col-sm-10">
        				
            			<c:out value="${petitionStatus}"/>
            		</div>
    			</div>
    			
    			<input type="hidden" name="id" value="${petition.id}">
    			<input type="hidden" name="status" value="${petition.status}">
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${petition['new']}">
                            <button class="btn btn-default" type="submit"><spring:message code="addnewpetition"/></button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit"><spring:message code="updatepetition"/></button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
