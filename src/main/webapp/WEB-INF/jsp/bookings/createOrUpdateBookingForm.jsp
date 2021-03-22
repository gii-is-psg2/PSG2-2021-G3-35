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
        <h2><c:if test="${booking['new']}">New </c:if>Booking</h2>

        <form:form modelAttribute="booking" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Start Date" name="startDate"/>
                <petclinic:inputField label="End Date" name="endDate"/>
                <div class="control-group">
                    <petclinic:selectField name="pet.name" label="Pet " names="${petsNames}" size="5"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${booking.pet.id}"/>
                    <button class="btn btn-default" type="submit">Add booking</button>
                </div>
            </div>
        </form:form>

        <br/>
        <b>Previous bookings</b>
        <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Start Date</th>
            <th style="width: 150px;">End Date</th>
            <th style="width: 80px">Room</th>
            <th style="width: 80px">Pet</th>
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
    <c:forEach items="${usedRooms}" var="usedRoom">
			<c:out value="${usedRoom}"/>
	</c:forEach>
    </jsp:body>
</petclinic:layout>
