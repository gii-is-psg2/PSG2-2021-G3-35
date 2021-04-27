<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">

	<h2><spring:message code="veterinarians"/></h2>

	<table id="causesTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th><spring:message code="name"/></th>
	            <th><spring:message code="description"/></th>
	            <th><spring:message code="objetive"/></th>
	            <th><spring:message code="organization"/></th>
	            <th><spring:message code="state"/></th>
	            <sec:authorize access="hasAuthority('admin')">
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
	                    <c:out value="${cause.description}"/>
	                </td>
	                 <td>
	                    <c:out value="${cause.objetive}"/>
	                </td>
	                 <td>
	                    <c:out value="${cause.organization}"/>
	                </td>
	                 <td>
	                    <c:out value="${cause.state}"/>
	                </td>
	              
	                <sec:authorize access="hasAuthority('admin')">
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
</petclinic:layout>