<%--
  Created by IntelliJ IDEA.
  User: Agnieszka
  Date: 2017-06-11
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Event</title>
</head>
<body>
<form:form method="POST" modelAttribute="eventad">
    <c:if test="${kolidacjapocz != null}">
        Zajęcia kolicują z zajęciami: <br/>
        Prowadzący: ${kolidacjapocz.therapist.firstName} ${kolidacjapocz.therapist.lastName} <br/>
        Data rozpoczęcia: ${kolidacjapocz.startDateTime} <br/>
        Data zakończenia: ${kolidacjapocz.endDateTime} <br/>
    </c:if>




    <td><form:select path="typ">
        <c:forEach items="${typee}" var="event" varStatus="status">
            <tr>
                <form:option value= "${event.typeEventId}" label="${event.type}" />
            </tr>
        </c:forEach>
    </form:select>

        <br/>
    </td>
    <td><c:if test="${pageContext.request.method=='POST'}">
        <form:errors path="name" /></c:if></td>
    <br/>
    <tr>
        <th>Nazwa:</th>
        <td><form:input type="text" path="name" /><c:if test="${pageContext.request.method=='POST'}">
            <form:errors path="name" /></c:if></td>
    </tr>
    <br/>
    <tr>
        <th>Data rozpoczęcia</th>
        <td><form:input type="text" path="startDateTime" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="startDateTime" /></c:if></td>
    </tr>
    <br/>
    <tr>
        <th>Data zakończenia</th>
        <td><form:input type="text" path="endDateTime" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="endDateTime" /></c:if></td>
        <br/>
    <tr>
        <th>Sala:</th>
        <td><form:input type="text" path="room" /><c:if test="${pageContext.request.method=='POST'}">
            <form:errors path="room" /></c:if></td>
    </tr>
    <br />

    Cykliczny:

    Nie <form:radiobutton   path="cykli" checked="checked" name ="cykl" value="nie" />
    Tak <form:radiobutton   path="cykli" name ="cykl" value="tak" />
    <br/>
    <tr>
        <th>Data zakończenia zajęć cyklicznych </th>
        <td><form:input type="text" path="endDateCykl" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="endDateCykl" /></c:if></td>
        <br/>
    <tr>


    </tr>
    <td colspan="2" align="right"><input type="submit" value="Dodaj" /></td>
    </tr>


</form:form>

${message}
</body>
</html>
