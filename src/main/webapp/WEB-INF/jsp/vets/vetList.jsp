<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="vets">
 
    <h2><spring:message code="veterinarians"/></h2>


    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th><spring:message code="firstname"/></th>
            <th><spring:message code="specialties"/></th>
            <sec:authorize access="hasAuthority('admin')">
            <th></th>
            <th></th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                    	<spring:message code="${specialty.name}" var="specialty"/>
                    	
                        <c:out value="${specialty} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}"><spring:message code="ninguna"/></c:if>
                </td>
              
                <sec:authorize access="hasAuthority('admin')">
                <td>
                  <spring:url value="/vets/{vetId}/edit" var="editVetUrl">
								    <spring:param name="vetId" value="${vet.id}"/>
					        </spring:url>
                  <a href="${editVetUrl}" class="glyphicon glyphicon-pencil"/></a>
                </td>
                
                <td>
                	<spring:url value="/vets/{vetId}/delete" var="deleteUrl">
        			      <spring:param name="vetId" value="${vet.id}"/>
    				      </spring:url>
    				      <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash btn btn-danger"></a>
                </td>
              </sec:authorize>
                

            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons" style="float:left">
        <tr>
        	<sec:authorize access="hasAuthority('admin')">
        	<td style="width: 80px;">
        		<a class="btn btn-default"  href='<spring:url value="/vets/new" htmlEscape="true"/>'><spring:message code="addvet"/></a>
            </td>
            </sec:authorize>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />"><spring:message code="viewasxml"/></a>
            </td>    
        </tr>
    </table>
         
</petclinic:layout>
