<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*"%>

<petclinic:layout pageName="causes">
    <h2>
        <c:if test="${cause['new']}"><spring:message code="new" /> &nbsp; </c:if> <spring:message code="cause" />
    </h2>
    <form:form modelAttribute="cause" class="form-horizontal" id="add-cause-form">
        <div class="form-group has-feedback">
        
        <spring:message code="name" var="name"/>
        <spring:message code="description" var="description"/>
        <spring:message code="objetive" var="objetive"/>
        <spring:message code="organization" var="organization"/>
            <petclinic:inputField label="${name}" name="name"/>
            <petclinic:inputField label="${description}" name="description"/>
            <petclinic:inputField label="${objetive}" name="objetive"/>
            <petclinic:inputField label="${organization}" name="organization"/>
            <input type="hidden" name="state" value="true"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${cause['new']}">
                        <button class="btn btn-default" type="submit"> <spring:message code="addcause"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"> <spring:message code="updatecause"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>