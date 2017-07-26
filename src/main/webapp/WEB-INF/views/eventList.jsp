<%--
  Created by IntelliJ IDEA.
  User: Agnieszka
  Date: 2017-06-20
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;showCalendars=1&amp;showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;bgcolor=%23FFFFFF&amp;src=${therapist.googleCalendarId}&amp;color=%235229A3&amp;ctz=Europe%2FWarsaw" style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>



<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Nazwa</th>
        <th>Typ</th>
        <th>Rozpoczęcie</th>
        <th>Koniec</th>
        <th>Edytuj</th>
        <th>Usuń</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${events}" var="eve" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${eve.name}</td>
            <td>${eve.type_Event.type}</td>
            <td>${eve.startDateTime}</td>
            <td>${eve.endDateTime}</td>
            <td><a href="<c:url value="editEvent-${eve.eventId}" />">Edytuj</a></td>
            <td><a href="<c:url value="delEvent-${eve.eventId}" />">Usuń</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
