<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="owners">

    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${pet['new']}"><spring:message code="new"/></c:if> &nbsp;<spring:message code="pet"/>
        </h2>
        <form:form modelAttribute="pet"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${pet.id}"/>
            <input type="hidden" name="owner" value="${pet.owner.id}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="owner"/></label>
                    <div class="col-sm-10">
                        <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
                    </div>
                </div>
                
              	<spring:message code="firstname" var="firstname"/>
   				<spring:message code="birth" var="birthday"/>
   				 <spring:message code="type" var="type"/>
                <petclinic:inputField label="${firstname}" name="name"/>
                <petclinic:inputField label="${birthday}" name="birthDate"/>
                <div class="control-group">
                    <petclinic:selectField name="type" label="${type} " names="${types}" size="5"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${pet['new']}">
                            <button class="btn btn-default" type="submit"><spring:message code="addnewpet"/></button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit"><spring:message code="updatepet"/></button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!pet['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>
