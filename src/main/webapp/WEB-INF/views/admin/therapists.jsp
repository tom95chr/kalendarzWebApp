<%--
  Created by IntelliJ IDEA.
  User: Lapek
  Date: 11.06.2017
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Therapists</title>
</head>
<body>
<a href="<c:url value="/" />">Home</a><br />
<a href="<c:url value="/admin/registration" />">Create account</a><br />

<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>First name</th>
        <th>Surname</th>
        <th>Specjalization</th>
        <th>Choose me</th>
        <th>Drop</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${therapists}" var="therapist" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${therapist.firstName}</td>
            <td>${therapist.lastName}</td>
            <td>${therapist.specialization}</td>
            <td><a href="<c:url value="/therapist-${therapist.therapistId}" />">Choose</a></td>
            <td><a href="<c:url value="therapist-${therapist.therapistId}/drop" />">Drop</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
