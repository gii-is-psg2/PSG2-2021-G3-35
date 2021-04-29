<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.samples.petclinic.model.PetitionStatus" %>


<petclinic:layout pageName="adoption">


    <h2><spring:message code="adoptionin"/></h2>

    <table class="table table-striped">
        <tr>
            <th style="width: 200px;"><spring:message code="title"/></th>
            <td><b><c:out value="${adoption.title}"/></b></td>
        </tr>
        <tr>
            <th><spring:message code="description"/></th>
            <td><c:out value="${adoption.description}"/></td>
        </tr>
        <tr>
            <th><spring:message code="pet"/></th>
            <td><c:out value="${adoption.pet.name}"/></td>
        </tr>
        <tr>
            <th><spring:message code="type"/></th>
            <spring:message code="${adoption.pet.type.name}" var="pettype"/>
            <td><c:out value="${pettype}"/></td>
        </tr>
        
        <tr>
            <th><spring:message code="owner"/></th>
            <td><a href="/owners/${adoption.owner.id}">
                  <c:out value="${adoption.owner.firstName} ${adoption.owner.lastName}"/></a></td>
        </tr>
        
        <tr>
            <th><spring:message code="publishdate"/></th>
            <td>
              <c:out value="${adoption.publishDate}"/>
            </td>
        </tr>
        
        <tr>
            <th><spring:message code="adoption_status"/></th>
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
        
        
    </table>

    <sec:authorize access="hasAuthority('admin') || hasAuthority('owner') && @isSameAdoptionOwner.hasPermission(${adoption.id})">
    <a href="/adoptions/${adoption.id}/delete" class="btn btn-default"><spring:message code="adoption_delete"/></a>
    </sec:authorize>
    <br>
    <br>
     <h2><spring:message code="petitions"/></h2>
  	
  			<table id="petitionsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><spring:message code="description" /></th>
            <th style="width: 200px;"><spring:message code="status" /></th>
            <th style="width: 200px"><spring:message code="name" /></th>
            <th style="width: 50px"></th>
            <th style="width: 50px"></th>
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
                  <a href="/owners/${petition.applicant.id}">
                  <c:out value="${petition.applicant.firstName} ${petition.applicant.lastName}"/>
                  </a>  
                </td>

					<td><c:choose>
							<c:when test="${adoption.open}">
								<spring:url
									value="/adoptions/{adoptionId}/petitions/{petitionId}/decline"
									var="declinePetition">
									<spring:param name="adoptionId" value="${adoption.id}" />
									<spring:param name="petitionId" value="${petition.id}" />
								</spring:url>
								<a href="${declinePetition}" class="glyphicon glyphicon-remove" /></a>
							</c:when>
						</c:choose></td>

					<td><c:choose>
							<c:when test="${adoption.open}">
								<spring:url
									value="/adoptions/{adoptionId}/petitions/{petitionId}/accept"
									var="declinePetition">
									<spring:param name="adoptionId" value="${adoption.id}" />
									<spring:param name="petitionId" value="${petition.id}" />
								</spring:url>
								<a href="${declinePetition}" class="glyphicon glyphicon-ok" /></a>
							</c:when>
						</c:choose></td>

				</tr>
        </c:forEach>
        </tbody>
    </table>
  			
</petclinic:layout>
