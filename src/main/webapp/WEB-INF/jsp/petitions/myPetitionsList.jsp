<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="petitions">
    <h2><spring:message code="petitions" /></h2>

    <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><spring:message code="description" /></th>
            <th style="width: 200px;"><spring:message code="status" /></th>
            <th style="width: 120px"><spring:message code="firstname" /></th>
            <th style="width: 120px"><spring:message code="lastname" /></th>
            <th style="width: 20px"></th>
            <th style="width: 20px"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${petitions}" var="petition">
            <tr>
         
                <td>
                    <c:out value="${petition.description}"/>
                </td>
                <td>
                   <spring:message code="${petition.status}" var="status"/>
                      
                    <c:out value="${status}"/>
                </td>
                <td>
                    <c:out value="${petition.applicant.firstName}"/>
                </td>
                
                <td>
                    <c:out value="${petition.applicant.lastName}"/>
                </td>

                <td>
                  <spring:url value="/petitions/mypetitions/{petitionId}/delete" var="deletePetition">
								    <spring:param name="petitionId" value="${petition.id}"/>
					        </spring:url>
                  <a href="${deletePetition}" class="glyphicon glyphicon-trash"/></a>
                </td>
                
                <td>
                  <spring:url value="/petitions/mypetitions/{petitionId}/edit" var="editPetition">
								    <spring:param name="petitionId" value="${petition.id}"/>
					        </spring:url>
                  <a href="${editPetition}" class="glyphicon glyphicon-pencil"/></a>
                </td>
               
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
