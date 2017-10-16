<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Therapist list</title>
</head>
<body>

<a href="/login">Login</a><br><br>
<sec:authorize url="/admin">
    <a href="/admin">Admin</a><br><br>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
    <a href="/therapist-events">Therapist</a><br><br>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
    <a href="/logout">Logout</a><br><br>
</sec:authorize>
<a href="/my-reservation">My reservation</a><br><br>
<table border="1" style="border-color: red">
    <thead>
    <tr>
        <th>#</th>
        <th>First name</th>
        <th>Surname</th>
        <th>Speciality</th>
        <th>Choose me</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${therapists}" var="therapist" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${therapist.firstName}</td>
            <td>${therapist.lastName}</td>
            <td>${therapist.specialization}</td>
            <td><a href="<c:url value="therapist-${therapist.therapistId}/" />">Choose</a></td>

        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>