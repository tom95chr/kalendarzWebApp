<%--
  Created by IntelliJ IDEA.
  User: Agnieszka
  Date: 2017-06-11
  Time: 18:30
  To change this template use File | Settings | File Templates.

  NIEPOTZREBENENENE !
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Title</title>
</head>
<body>



<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Imie</th>
        <th>Nazwisko</th>
        <th>Specjalizacja</th>
        <th>Wybierz</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${therapists}" var="therapist" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${therapist.firstName}</td>
            <td>${therapist.lastName}</td>
            <td>${therapist.specialization}</td>
            <td><a href="<c:url value="choose-${therapist.therapistId}" />">Wybierz</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>
