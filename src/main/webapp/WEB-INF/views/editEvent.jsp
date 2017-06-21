<%--
  Created by IntelliJ IDEA.
  User: Agnieszka
  Date: 2017-06-20
  Time: 18:12
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
${kolidacjapocz}
<form:form method="POST" modelAttribute="eventadd">
    <table border="1">
        <tbody>



        <tr>
            <th>Nazwa</th>
            <td><form:input type="text" path="name" value="${event.name}"  /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="name" /></c:if></td>
        </tr>
        <tr>
            <th>Data rozpoczęcia</th>
            <td><form:input type="text" path="startDateTime" value="${datSt}"  /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="startDateTime" /></c:if></td>
        </tr>

        <tr>
            <th>Data zakończenia</th>
            <td><form:input type="text" path="endDateTime" value="${datEn}"  /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="endDateTime" /></c:if></td>
        </tr>


        <tr>
            <th>Sala</th>
            <td><form:input type="text" path="room" value="${event.room}"  /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="room" /></c:if></td>
        </tr>
        <tr>
            <th>Typ</th>
        <td><form:select path="typ">
                <c:forEach items="${typee}" var="event" varStatus="status">
                <form:option value= "${event.typeEventId}" label="${event.type}" />
            </c:forEach>

            </form:select>
        </tr>

        <tr>
            <td colspan="2" align="right"><input type="submit" value="Zmień" /></td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
