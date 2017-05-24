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

<iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;showCalendars=1&amp;showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;bgcolor=%23FFFFFF&amp;src=${therapist.googleCalendarId}&amp;color=%235229A3&amp;ctz=Europe%2FWarsaw" style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>

</body>
</html>
