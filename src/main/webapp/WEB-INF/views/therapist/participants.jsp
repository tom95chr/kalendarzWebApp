<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Event participants</title>
</head>
<body>
<td><a href="<c:url value="/therapist-events" />">Therapist events</a></td> <br/>
<a href="<c:url value="/logout" />">Logout</a><br/>
<a href="<c:url value="/" />">Home</a><br/>
<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Email</th>
        <th>Telephone</th>
        <th>Confirmed</th>
        <th>Confirmation code</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${reservationList}" var="reservation" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${reservation.client.email}</td>
            <td>${reservation.client.telephone}</td>
            <td>${reservation.confirmed}</td>
            <td>${reservation.confirmationCode}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>