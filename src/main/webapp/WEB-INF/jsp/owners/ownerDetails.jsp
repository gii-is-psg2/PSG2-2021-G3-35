<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<petclinic:layout pageName="owners">


    <h2><spring:message code="ownerin"/></h2>

    <table class="table table-striped">
        <tr>
            <th><spring:message code="firstname"/></th>
            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
        </tr>
        <tr>
            <th><spring:message code="address"/></th>
            <td><c:out value="${owner.address}"/></td>
        </tr>
        <tr>
            <th><spring:message code="city"/></th>
            <td><c:out value="${owner.city}"/></td>
        </tr>
        <tr>
            <th><spring:message code="telephone"/></th>
            <td><c:out value="${owner.telephone}"/></td>
        </tr>
    </table>
    
    <spring:url value="{ownerId}/edit" var="editUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>

    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><spring:message code="editowner"/></a>

 
    <spring:url value="{ownerId}/delete" var="deleteUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default"><spring:message code="deleteowner"/></a>
    
    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default"><spring:message code="addnewpet"/></a>

    <br/>
    <br/>
    <br/>
    <h2><spring:message code="petsandvisit"/></h2>

    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt><spring:message code="firstname"/></dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt><spring:message code="birth"/></dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt><spring:message code="type"/></dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th><spring:message code="visitdate"/></th>
                            <th><spring:message code="description"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                                
                                <td><spring:url value="/owners/{ownerId}/visits/{visitId}/delete" var="visitDeleteUrl">
                                    
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="visitId" value="${visit.id}"/>
                                </spring:url>
                                <a style="color:gray" href="${fn:escapeXml(visitDeleteUrl)}"><spring:message code="deletevisit"/></a>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}"><spring:message code="editpet"/></a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/delete" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}"><spring:message code="deletepet"/></a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitUrl)}"><spring:message code="addvisit"/></a>
                            </td>
                            
                        </tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>
      
    <h2>Bookings</h2>
	  
    <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 120px;">Start Date</th>
            <th style="width: 120px;">End Date</th>
            <th style="width: 100px">Room</th>
            <th style="width: 140px">Pet</th>
            <th style="width: 30px"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${owner.pets}" var="pets2">
        	<c:forEach items="${pets2.bookings}" var="booking">
            <tr>
               
                <td>
                    <c:out value="${booking.startDate}"/>
                </td>
                <td>
                    <c:out value="${booking.endDate}"/>
                </td>
                <td>
                    <c:out value="${booking.room}"/>
                </td>
                <td>
                    <c:out value="${booking.pet.name}"/>
                </td>
                <td>
                    <spring:url value="/owners/{ownerId}/bookings/{bookingId}/delete" var="deleteUrl">
						<spring:param name="ownerId" value="${owner.id}"/>
						<spring:param name="bookingId" value="${booking.id}"/>
					</spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}" class="glyphicon glyphicon-trash btn btn-danger"></a>
                </td>
              
            </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
    <spring:url value="/owners/{ownerId}/bookings/new" var="newBookingUrl">
    	<spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(newBookingUrl)}" class="btn btn-default">New Booking</a>
    
</petclinic:layout>
