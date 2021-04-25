<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<petclinic:layout pageName="adoptions">
		    
    <h2><spring:message code="adoptions"/></h2>

    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="adoption_offer"/></th>
            <th style="width: 150px;"><spring:message code="pet"/></th>
            <th><spring:message code="type"/></th>
            <th><spring:message code="owner"/></th>
            <th><spring:message code="publishdate"/></th>
            <th style="width: 100px;"><spring:message code="adoption_status"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptions}" var="adoption">
            <tr>
                <td>
                    <spring:url value="/adoptions/${adoption.id}" var="adoptionUrl"/>
                    <a href="${fn:escapeXml(adoptionUrl)}"><c:out value="${adoption.title}"/></a>
                </td>
                <td>
                    <c:out value="${adoption.pet}"/>
                </td>
                <spring:message code="${adoption.pet.type.name}" var="pettype"/>
            <td><c:out value="${pettype}"/></td>
                <td>
                  <a href="/owners/${adoption.owner.id}">
                  <c:out value="${adoption.owner.firstName} ${adoption.owner.lastName}"/>
                  </a>  
                </td>
                <td>
                  
                  <c:out value="${adoption.publishDate}"/>
               
                </td>
                <td>
                <c:choose>
                <c:when test= "${adoption.open}">
                    <span style="color: yellow"><spring:message code="open"/></span>
                </c:when>
                <c:otherwise>
                	<span style="color: red"><spring:message code="closed"/></span>
                 </c:otherwise>
                 </c:choose>
                </td>
                

            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
