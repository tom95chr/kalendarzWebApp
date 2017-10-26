<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  Client: Lapek
  Date: 06.10.2017
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>


<div class="container">
    <h1>Host</h1>
    <h2>${therapist.firstName} ${therapist.lastName} <br>
        Specialization: ${therapist.specialization} <br>
        Description: ${therapist.description}
    </h2>
    <h1>Event details</h1>
    <h2>

        start: ${event.startDateTime.dayOfMonth}-${event.startDateTime.monthValue}-${event.startDateTime.year}
        godz. ${event.startDateTime.toLocalTime()}<br>
        end: ${event.endDateTime.dayOfMonth}-${event.endDateTime.monthValue}-${event.endDateTime.year}
        godz. ${event.endDateTime.toLocalTime()}<br>
        room: ${event.room}<br>
        free places: ${(freeSlots)}<br>

    </h2>


    <form:form method="POST" modelAttribute="client" class="form-signin">
        <h2 class="form-signin-heading">If you want to join this event please submit your email and confirm reservation </h2>
        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email" class="form-control" placeholder="Your email"
                            autofocus="true"></form:input>
                <form:errors path="email"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="telephone">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="telephone" class="form-control" placeholder="Telephone(optional)"></form:input>
                <form:errors path="telephone"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>