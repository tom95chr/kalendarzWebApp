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


    asd
    <td><form:select path="typ">
        <form:option value="poj" label="poj" />
        <form:option value="gru" label="gru" />

    </form:select>
    </td>
    <td><c:if test="${pageContext.request.method=='POST'}">
        <form:errors path="name" /></c:if></td>

    <tr>
        <th>Nazwa:</th>
        <td><form:input type="text" path="name" /><c:if test="${pageContext.request.method=='POST'}">
            <form:errors path="name" /></c:if></td>
    </tr>

    <tr>
        <th>Data rozpoczęcia</th>
        <td><form:input type="text" path="startDateTime" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="startDateTime" /></c:if></td>
    </tr>

    <tr>
        <th>Data zakończenia</th>
        <td><form:input type="text" path="endDateTime" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="endDateTime" /></c:if></td>
    </tr>
        <td colspan="2" align="right"><input type="submit" value="Dodaj" /></td>
    </tr>


</form:form>

${message}
</body>
</html>
