<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Strona terapeuty</title>
</head>
<body>
Dear <strong>${user}</strong>, Manage your events <br/> <br/>
<td><a href="<c:url value="/therapist-events/createEvent-${user}/" />">Create event</a></td> <br/>
<a href="<c:url value="/logout" />">Logout</a><br/>
<a href="<c:url value="/" />">Home</a><br/>
<h1 style="color: red">${info}</h1>
<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <%--<th>Name</th>--%>
        <th>Start</th>
        <th>End</th>
        <th>Room</th>
        <th>Participants</th>
        <th>Free places</th>
        <th>Drop</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${events}" var="event" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>

            <%--<td>${event.name}</td>--%>
            <td>${event.startDateTime}</td>
            <td>${event.endDateTime}</td>
            <td>${event.room}</td>
            <td><a href="<c:url value="/therapist-events/event-${event.eventId}/participants" />">${participants[status.index]}</a></td>
            <td>${event.free}</td>
            <td><a href="<c:url value="/event-${event.eventId}/drop" />">Drop</a></td>

        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>All therapist events in one calendar</h2>
<iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;
showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;hl=pl&amp;bgcolor=%23c0c0c0&amp;
<c:forEach items="${therapists}" var="therapist">
src=${therapist.googleCalendarId}&amp;color=${therapist.colour}&amp;
</c:forEach>
ctz=Europe%2FWarsaw" style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>

</body>
</html>