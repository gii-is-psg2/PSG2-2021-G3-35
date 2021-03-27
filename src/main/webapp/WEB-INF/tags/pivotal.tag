<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<br/>
<br/>
<div class="container">

    <div class="row">
        <div class="col-12 text-center"><img src="<spring:url value="/resources/images/logo.png" htmlEscape="true" />"
                                             alt="Sponsored by Pivotal"/></div>
    </div>
    <div class="col-12 text-center">
    <br>
<p><fmt:message key="changelang"/></p>
<a href="?lang=es">Español</a>
/
<a href="?lang=en">English</a>
</div>
</div>
