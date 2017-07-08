<%--
  Created by IntelliJ IDEA.
  User: Lapek
  Date: 23.05.2017
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dodaj terapeute</title>
</head>
<body>
<a href="/">Powrot do strony glownej</a><br />
<form:form method="POST" modelAttribute="therapistDto" >
    <table border="1">
        <tbody>

        <tr>
            <th>Login</th>
            <td><form:input type="text" path="therapistId" /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="therapistId" /></c:if></td>
        </tr>
        <th>Haslo</th>
        <td><form:input type="text" path="password" /><c:if test="${pageContext.request.method=='POST'}">
            <form:errors path="password" /></c:if></td>
        </tr>
        <th>Powtorz haslo</th>
        <td><form:input type="text" path="password_confirmed" /><c:if test="${pageContext.request.method=='POST'}">
            <form:errors path="password" /></c:if></td>
        </tr>
        <tr>
            <th>Imię</th>
            <td><form:input type="text" path="firstName" /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="firstName" /></c:if></td>
        </tr>
        <tr>
            <th>Nazwisko</th>
            <td><form:input type="text" path="lastName" /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="lastName" /></c:if></td>
        </tr>
        <tr>
            <th>Specjalizacja</th>
            <td><form:input type="text" path="specialization" /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="specialization" /></c:if></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><form:input type="text" path="email" /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="email" /></c:if></td>
        </tr>
        <tr>
            <th>Telefon kontaktowy</th>
            <td><form:input type="text" path="telephone" /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="telephone" /></c:if></td>
        </tr>
        <tr>
            <th>Dodatkowy opis</th>
            <td><form:input type="text" path="description" /><c:if test="${pageContext.request.method=='POST'}">
                <form:errors path="description" /></c:if></td>
        </tr>
        <tr>
            <td colspan="2" align="right"><input type="submit" value="Dodaj" /></td>
        </tr>
        </tbody>
    </table>
</form:form>

</body>
</html>