<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">

	<h2><spring:message code="causes"/></h2>

	<table id="causesTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th><spring:message code="name"/></th>
	            <th><spring:message code="totalAcum"/> / <spring:message code="objetive"/></th>
	            <th></th>
	            <th></th>
	            <sec:authorize access="hasAuthority('admin')">
	            <th></th>
	            <th></th>
	            </sec:authorize>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${causes}" var="cause">
	            <tr>
	                <td>
	                    <c:out value="${cause.name}"/>
	                </td>
	                
	                 <td>
<%-- 	                    <c:out value="${totalAcum[cause.id]}"/> / <c:out value="${cause.objetive}"/> --%>
	                </td>
	                
	              	<td>
	                  <spring:url value="/causes/{causeId}" var="causeDetailsUrl">
									    <spring:param name="causeId" value="${cause.id}"/>
						        </spring:url>
	                  <a href="${causeDetailsUrl}" class="btn btn-default"/><spring:message code="details"/></a>
	                </td>
	                <td>
	                <c:if test="${cause.state == true}">
	              	
	                  <spring:url value="/causes/{causeId}/donate" var="donateUrl">
									    <spring:param name="causeId" value="${cause.id}"/>
						        </spring:url>
	                  <a href="${donateUrl}" class="btn btn-default"/><spring:message code="donate"/></a>
					</c:if>	 
					</td>             
	                <sec:authorize access="hasAuthority('admin')">
	                <td>
	                  <spring:url value="/causes/{causeId}/edit" var="editCauseUrl">
									    <spring:param name="causeId" value="${cause.id}"/>
						        </spring:url>
	                  <a href="${editCauseUrl}" class="glyphicon glyphicon-pencil"/></a>
	                </td>
	                
	                <td>
	                	<spring:url value="/causes/{causeId}/delete" var="deleteUrl">
	        			      <spring:param name="causeId" value="${cause.id}"/>
	    				      </spring:url>
	    				      <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash btn btn-danger"></a>
	                </td>
	              </sec:authorize>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	    
        <div>
        	<a class="btn btn-default"  href='<spring:url value="/causes/new" htmlEscape="true"/>'><spring:message code="addcause"/></a>
        </div>  
	    
</petclinic:layout>