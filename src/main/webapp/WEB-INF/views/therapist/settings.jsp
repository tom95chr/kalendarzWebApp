<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  Client: Lapek
  Date: 21.08.2017
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
</head>
<body>
<a href="<c:url value="/therapist-events" />">Therapist Events</a><br/> <br/>

<div class="container">

    <form:form method="POST" modelAttribute="eventType" class="form-signin">
        <h2 class="form-signin-heading">Add your type </h2>
        <spring:bind path="eventTypeId">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="eventTypeId" class="form-control" placeholder="Event Type Id"
                            autofocus="true"></form:input>
                <form:errors path="eventTypeId"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="seats">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="seats" class="form-control" placeholder="Seats"></form:input>
                <form:errors path="seats"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>

<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Type</th>
        <th>Seats</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${eventTypes}" var="eventType" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${eventType.eventTypeId}</td>
            <td>${eventType.seats}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
