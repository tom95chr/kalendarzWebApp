<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  User: Lapek
  Date: 25.07.2017
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Create Event</title>
</head>
<body>
<form:form method="POST" modelAttribute="eventDto">

    <th>Typ zajęć</th><br/>
    <td><form:select path="eventType">
        <c:forEach items="${eventTypes}" var="event" varStatus="status">
            <tr>
                <form:option value= "${event.typeEventId}" label="${event.typeEventId}" />
            </tr>
        </c:forEach>
    </form:select>
    </td>
    <br/>
    <tr>
        <th>Nazwa</th><br/>
        <td><form:input type="text" path="name" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="name" /></c:if></td>
    </tr>
    <br/>
    <tr>
        <th>Data rozpoczęcia</th><br/>
        <td><form:input type="text" path="startDateTime" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="startDateTime" /></c:if></td>
    </tr>
    <br/>
    <tr>
        <th>Data zakończenia</th><br/>
        <td><form:input type="text" path="endDateTime" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="endDateTime" /></c:if></td>
        <br/>
    <tr>
        <th>Sala:</th><br/>
        <td><form:input type="text" path="room" /><c:if test="${pageContext.request.method=='POST'}">
            <form:errors path="room" /></c:if></td>
    </tr>
    <br />

    </tr>
    <td colspan="2" align="right"><input type="submit" value="Utwórz" /></td>
    </tr>

</form:form>

<h2>${error}</h2>
<c:if test="${collidedEvent != null}">
    <h2>Upppps... this term is not available.</h2>
    <h2>This room is occupied by ${collidedEvent.therapist.firstName} ${collidedEvent.therapist.lastName}
        from: ${collidedEvent.startDateTime.toString()} to: ${collidedEvent.endDateTime.toString()} </h2>
</c:if>
<c:if test="${collidedEvent == null}">
    <h2>${eventCreated}</h2>
</c:if>

<h2>Wszystkie w jednym</h2>
<iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;
showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;hl=pl&amp;bgcolor=%23c0c0c0&amp;
<c:forEach items="${therapists}" var="therapist">
src=${therapist.googleCalendarId}&amp;color=${therapist.colour}&amp;
</c:forEach>
ctz=Europe%2FWarsaw" style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>

<%--<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Nazwa</th>
        <th>Początek</th>
        <th>Koniec</th>
        <th>Sala</th>
        <th>Email klienta</th>
        <th>Potwierdzony</th>
        <th>Usuń</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${events}" var="therapist" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${therapist.firstName}</td>
            <td>${therapist.lastName}</td>
            <td>${therapist.specialization}</td>
            <td><a href="<c:url value="therapist-${therapist.therapistId}/" />">Wybierz</a></td>

        </tr>
    </c:forEach>
    </tbody>
</table>--%>
</body>
</html>
