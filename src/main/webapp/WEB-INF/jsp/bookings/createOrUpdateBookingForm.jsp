<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="bookings">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#startDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#endDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${booking['new']}"><spring:message code="new" />&nbsp; </c:if><spring:message code="booking" /></h2>
		
		<spring:message code="startdate" var="startdate"/>
		<spring:message code="enddate" var="enddate"/>
		<spring:message code="pet" var="pet"/>
		
        <form:form modelAttribute="booking" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="${startdate}" name="startDate"/>
                <petclinic:inputField label="${enddate}" name="endDate"/>
                <div class="control-group">
                    <petclinic:selectField name="pet.name" label="${pet} " names="${petsNames}" size="5"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${booking.pet.id}"/>
                    <button class="btn btn-default" type="submit"><spring:message code="addbooking" /></button>
                </div>
            </div>
        </form:form>

        <br/>
        <b><spring:message code="previousbookings" /></b>
        <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;"><spring:message code="startdate" /></th>
            <th style="width: 150px;"><spring:message code="enddate" /></th>
            <th style="width: 80px"><spring:message code="room" /></th>
            <th style="width: 80px"><spring:message code="pet" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${owner.pets}" var="pets2">
        	<c:forEach items="${pets2.bookings}" var="bookingPrev">
            <tr>
               
                <td>
                    <c:out value="${bookingPrev.startDate}"/>
                </td>
                <td>
                    <c:out value="${bookingPrev.endDate}"/>
                </td>
                <td>
                    <c:out value="${bookingPrev.room}"/>
                </td>
                <td>
                    <c:out value="${bookingPrev.pet.name}"/>
                </td>
                
            </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
    </jsp:body>
</petclinic:layout>
