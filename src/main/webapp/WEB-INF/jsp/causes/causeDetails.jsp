<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="causes">

    <h2><spring:message code="causedetail"/></h2>

    <table class="table table-striped">
        <tr>
            <th><spring:message code="name"/></th>
            <td><c:out value="${cause.name}"/></td>
        </tr>
        <tr>
            <th><spring:message code="description"/></th>
            <td><c:out value="${cause.description}"/></td>
        </tr>
        <tr>
            <th><spring:message code="totalAcum"/> / <spring:message code="objetive"/></th>
            <td><c:out value="${totalAcum}"/> / <c:out value="${cause.objetive}"/></td>
        </tr>
        <tr>
            <th><spring:message code="organization"/></th>
            <td><c:out value="${cause.organization}"/></td>
        </tr>
        <tr>
            <th><spring:message code="state"/></th>
            <td><c:if test="${cause.state == true}"><spring:message code="open" />
	        </c:if>
	        <c:if test="${cause.state == false}"><spring:message code="close" />
	        </c:if>
	        </td>
        </tr>
    </table>
     
    <h2><spring:message code="donations"/></h2>
	  
    <table id="donationsTable" class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="amount"/></th>
            <th><spring:message code="date"/></th>
            <th><spring:message code="owner"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cause.donations}" var="donation">
            <tr>
                <td>
                    <c:out value="${donation.amount}"/>
                </td>
                <td>
                    <c:out value="${donation.date}"/>
                </td>
                <td>
                    <c:out value="${donation.owner.firstName} ${donation.owner.lastName}"/>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
