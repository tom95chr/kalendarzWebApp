<%--
  Created by IntelliJ IDEA.
  User: Lapek
  Date: 22.05.2017
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Terapeuci</title>
</head>
<body>
<body>
<a href="<c:url value="dodaj" />">Dodaj kota</a><br />
<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Imie kota</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${koty}" var="kot" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td><a href="<c:url value="kot-${kot.idKota}" />">${kot.imie}</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
