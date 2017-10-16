<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  Client: Lapek
  Date: 23.05.2017
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Terapeuta</title>
</head>
<body>

<table border="1">
    <tbody>
    <tr>
        <th>Name</th>
        <td>${therapist.firstName}</td>
    </tr>
    <tr>
        <th>Surname</th>
        <td>${therapist.lastName}</td>
    </tr>
    <tr>
        <th>Specialization</th>
        <td>${therapist.specialization}</td>
    </tr>
    </tbody>

</table>
<c:if test="${events.size()>0}">
<h2>Available events</h2>

<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>Type</th>
        <th>Start</th>
        <th>End</th>
        <th>Room</th>
        <th>Reservation</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${events}" var="event" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${event.name}</td>
            <td>${event.eventType.eventTypeId}</td>
            <td>${event.startDateTime}</td>
            <td>${event.endDateTime}</td>
            <td>${event.room}</td>
            <td><a href="<c:url value="/therapist-${therapist.therapistId}/event-${event.eventId}/" />">Reserve</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</c:if>
<c:if test="${events.size()==0}">
    <h1>Sorry, no available events found</h1>
</c:if>

<iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;showCalendars=1&amp;showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;bgcolor=%23FFFFFF&amp;src=${therapist.googleCalendarId}&amp;color=%235229A3&amp;ctz=Europe%2FWarsaw"
        style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>

</body>
</html>
