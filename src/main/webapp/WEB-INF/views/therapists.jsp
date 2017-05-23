<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista terapeutow</title>
</head>
<body>
<a href="/therapists/add">Dodaj terapeute</a><br />

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
            <td><a href="<c:url value="therapist-${therapist.therapistId}" />">Wybierz mnie</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br />
<hr />
</body>
</html>