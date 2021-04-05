<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="owners">

    <h2>
        <c:if test="${owner['new']}"><spring:message code="new"/> </c:if> &nbsp;<spring:message code="owner"/>
    </h2>
    <spring:message code="firstname" var="firstname"/>
    <spring:message code="lastname" var="lastname"/>
    <spring:message code="address" var="address"/>
    <spring:message code="city" var="city"/>
    <spring:message code="telephone" var="telephone"/>
    <spring:message code="username" var="username"/>
    <spring:message code="password" var="password"/>
    
    <form:form modelAttribute="owner" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="${firstname}" name="firstName"/>
            <petclinic:inputField label="${lastname}" name="lastName"/>
            <petclinic:inputField label="${address}" name="address"/>
            <petclinic:inputField label="${city}" name="city"/>
            <petclinic:inputField label="${telephone}" name="telephone"/>
            <petclinic:inputField label="${username}" name="user.username"/>
            <petclinic:inputField label="${password}" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${owner['new']}">
                        <button class="btn btn-default" type="submit"><spring:message code="addowner"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><spring:message code="editowner"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    
</petclinic:layout>
