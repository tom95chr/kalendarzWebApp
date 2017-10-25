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

    <p>imie nazw dane</p>

</section>

<section class="row">
    <div class="grid2">
        <section class="teaser col-2-3a">
            <iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;showCalendars=1&amp;showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;bgcolor=%23FFFFFF&amp;src=${therapist.googleCalendarId}&amp;color=%235229A3&amp;ctz=Europe%2FWarsaw"
                    style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>
        </section><!--
        --><section class="teaser col-1-3a">
            <c:if test="${events.size()>0}">
                <%--<h2>Available events</h2>--%>

                <table class="table-fill">
                    <thead>
                    <tr>
                        <th class="text-left">#</th>
                        <th class="text-left">Typ</th>
                        <th class="text-left">Data</th>
                        <th class="text-left">Godzina</th>
                        <th class="text-left">Trwa</th>
                            <%--<th>Room</th>--%>
                        <th>Wybierz</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${events}" var="event" varStatus="status">
                        <tr>
                            <td class="text-right">${status.index + 1}</td>
                                <%--<td>${event.name}</td>--%>
                            <td class="text-left">${event.eventType.eventTypeId}</td>
                            <td class="text-right">${event.startDateTime.dayOfMonth}-${event.startDateTime.monthValue}-${event.startDateTime.year}</td>
                            <td class="text-right">${event.startDateTime.toLocalTime()}</td>
                            <td>${duration[status.index]}min.</td>
                                <%--<td>${event.endDateTime.dayOfMonth}-${event.endDateTime.monthValue}-${event.endDateTime.year}
                                    godz. ${event.endDateTime.toLocalTime()}</td>--%>
                                <%--<td>${event.room}</td>--%>
                            <td class="text-right"><a
                                    href="<c:url value="/therapist-${therapist.therapistId}/event-${event.eventId}/" />">Wyb</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${events.size()==0}">
                <h1>Sorry, no available events found</h1>
            </c:if>
        </section>
    </div>
</section>
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
