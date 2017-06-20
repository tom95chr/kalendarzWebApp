<%--
  Created by IntelliJ IDEA.
  User: Agnieszka
  Date: 2017-06-19
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
</head>
<body>
${info}
Czy chcesz zapisać się na zajęcia prowadzone przez: ${event.therapist.firstName} ${event.therapist.lastName} <br>
Odbywają się: od ${event.startDateTime} do ${event.endDateTime} <br>
Są to zajęcia: ${event.type_Event.type} <br>
W sali: ${event.room} <br>

<form:form method="POST" modelAttribute="ClientDTO">
    <tr>
        <th>Email</th>
        <td><form:input type="text" path="email" /><c:if test="${pageContext.request.method=='POST'}"><form:errors path="email" /></c:if></td>

    <tr>
        <th>Telefon:</th>
        <td><form:input type="text" path="telephone" /><c:if test="${pageContext.request.method=='POST'}">
            <form:errors path="telephone" /></c:if></td>
    </tr>

    </tr>
    <td colspan="2" align="right"><input type="submit" value="Dodaj" /></td>
    </tr>
</form:form>
</body>
</html>
