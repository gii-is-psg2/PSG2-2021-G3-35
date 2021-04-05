<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="bookings">
    <h2><spring:message code="bookings" /></h2>

    <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><spring:message code="startdate" /></th>
            <th style="width: 200px;"><spring:message code="enddate" /></th>
            <th style="width: 120px"><spring:message code="pet" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookings}" var="booking">
            <tr>
                <!--
                <td>
                    <spring:url value="/owners/{ownerId}" var="ownerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${owner.firstName} ${owner.lastName}"/></a>
                </td>
                -->
                <td>
                    <c:out value="${booking.startDate}"/>
                </td>
                <td>
                    <c:out value="${booking.endDate}"/>
                </td>
                <td>
                    <c:out value="${booking.pet.name}"/>
                </td>
                <!-- 
                <td>
                    <c:forEach var="pet" items="${owner.pets}">
                        <c:out value="${pet.name} "/>
                    </c:forEach>
                </td>
                 -->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
