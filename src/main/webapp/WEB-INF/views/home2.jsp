<%--
  Created by IntelliJ IDEA.
  User: Agnieszka
  Date: 2017-06-11
  Time: 18:30
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

<a href="/event/addEvent">Dodaj terapeute</a><br />

<c:forEach items="${event}" var="event" varStatus="status">
    <tr>
        <td>${status.index + 1}</td>

    </tr>
</c:forEach>
<c:forEach items="${typem}" var="typem" varStatus="status">
    <tr>
        <td>${status.index + 1}</td>
        <td>${typem.type}</td>

    </tr>
</c:forEach>


</body>
</html>
