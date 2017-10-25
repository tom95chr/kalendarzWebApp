<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Terapeuta </title>
    <link rel="stylesheet" href="resources/stylescheets/main.css">
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:100,300,400">
</head>

<body class="row-alt">

<header class="primary-header container">

    <a class="logo" href="/">
        <img src="resources/images/logo.png">
    </a>

    <a class="btn btn-alt " href="/login">Login</a>

    <nav class="nav primary-nav group">
        <a href="/">Home</a>
        <sec:authorize url="/admin">
            <a href="/admin">Admin</a>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
            <a href="/therapist-events">Therapist</a>
        </sec:authorize>
        <a href="/my-reservation">Rezerwacja</a>
        <a href="/confirm-reservation">Potwierdzenie</a>
        <a href="/logout">Wyloguj</a>
    </nav>

</header>

<!-- Hero -->

<section class="hero container">

    <p>Elo</p>

</section>

<div>

    <table border="1">
        <tbody>
        <tr>
            <th>Name</th>
            <td>${therapist.firstName}</td>
        </tr>
        <tr>
            <th>Surname</th>
            <td>${therapist.lastName}</td>
        </tr>
        <tr>
            <th>Specialization</th>
            <td>${therapist.specialization}</td>
        </tr>
        </tbody>

    </table>
    <c:if test="${events.size()>0}">
        <h2>Available events</h2>

        <table border="1">
            <thead>
            <tr>
                <th>#</th>
                <th>Typ</th>
                <th>Data</th>
                <th>Godzina</th>
                <th>Trwa</th>
                    <%--<th>Room</th>--%>
                <th>Wybierz</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${events}" var="event" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                        <%--<td>${event.name}</td>--%>
                    <td>${event.eventType.eventTypeId}</td>
                    <td>${event.startDateTime.dayOfMonth}-${event.startDateTime.monthValue}-${event.startDateTime.year}</td>
                    <td>${event.startDateTime.toLocalTime()}</td>
                    <td>${duration[status.index]} min.</td>
                        <%--<td>${event.endDateTime.dayOfMonth}-${event.endDateTime.monthValue}-${event.endDateTime.year}
                            godz. ${event.endDateTime.toLocalTime()}</td>--%>
                        <%--<td>${event.room}</td>--%>
                    <td><a href="<c:url value="/therapist-${therapist.therapistId}/event-${event.eventId}/" />">Wyb</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${events.size()==0}">
        <h1>Sorry, no available events found</h1>
    </c:if>

    <iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;showCalendars=1&amp;showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;bgcolor=%23FFFFFF&amp;src=${therapist.googleCalendarId}&amp;color=%235229A3&amp;ctz=Europe%2FWarsaw"
            style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>
</div>
<footer class="primary-footer container group">

    <small>&copy; PWSZ Tarnów</small>

    <nav class="nav">
        <a href="/">Home</a>
        <a href="/my-reservation">Rezerwacja</a>
        <a href="/confirm-reservation">Potwierdzenie</a>
        <a href="/logout">Wyloguj</a>
    </nav>

</footer>

</body>
</html>
