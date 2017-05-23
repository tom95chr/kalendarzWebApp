<%--
  Created by IntelliJ IDEA.
  User: Lapek
  Date: 23.05.2017
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Strona terapeuty</title>
</head>
<body>
<table border="1">
    <tbody>
    <tr>
        <th>Imię</th>
        <td>${therapist.firstName}</td>
    </tr>
    <tr>
        <th>Nazwisko</th>
        <td>${therapist.lastName}</td>
    </tr>
    <tr>
        <th>Specjalizacja</th>
        <td>${therapist.specialization}</td>
    </tr>

    </tbody>

</table>

</body>
</html>
