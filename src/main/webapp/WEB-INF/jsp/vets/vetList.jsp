<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="vets">
    <h2>Veterinarians</h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Specialties</th>
            <sec:authorize access="hasAuthority('admin')">
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
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                <sec:authorize access="hasAuthority('admin')">
                <td>
                	<spring:url value="/vets/{vetId}/edit" var="editVetUrl">
								<spring:param name="vetId" value="${vet.id}"/>
					</spring:url>
                    <a href="${editVetUrl}" class="glyphicon glyphicon-pencil"/></a>
                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons" style="float:left">
        <tr>
        	<sec:authorize access="hasAuthority('admin')">
        	<td style="width: 100px;">
        		<a class="btn btn-default"  href='<spring:url value="/vets/new" htmlEscape="true"/>'>Add Vet</a>
            </td>
            </sec:authorize>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">View as XML</a>
            </td>    
        </tr>
    </table>
      
</petclinic:layout>
