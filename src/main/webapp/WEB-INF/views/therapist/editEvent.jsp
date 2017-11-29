<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lapek
  Date: 15.10.2017
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Edit event</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>

<div class="container">

            <a class="btn btn-primary" style="margin-top: 2%" href="/therapist-events">Powrót</a>
            <h2>Edytuj wybrany termin spotkania</h2>
            <h1 style="color: red">${editError}
            <c:if test="${collidedEvent != null}">
                NIE UTWORZONO SPOTKANIA !<br> Wybrana sala jest zajęta przez: <br>
                ${collidedEvent.therapist.specialization} ${collidedEvent.therapist.firstName} ${collidedEvent.therapist.lastName}
                <br>dnia ${collidedEvent.startDateTime.dayOfMonth}-${collidedEvent.startDateTime.monthValue}-${collidedEvent.startDateTime.year}
                od godz: ${collidedEvent.startDateTime.toLocalTime()}
                do godz. ${collidedEvent.endDateTime.toLocalTime()}
                <c:if test="${!collidedEvent.startDateTime.toLocalDate().isEqual(collidedEvent.endDateTime.toLocalDate())}">
                    dnia: ${collidedEvent.endDateTime.dayOfMonth}-${collidedEvent.endDateTime.monthValue}-${collidedEvent.endDateTime.year}
                </c:if>
            </c:if>
            </h1>
            <form:form method="POST" modelAttribute="eventDTO" class="form-signin">
                <div class="form-group">
                    <spring:bind path="eventType">
                        <label style="font-weight: bold" for="typ">Wybierz typ spotkania</label>
                        <form:select class="form-control" path="eventType" id="typ">
                            <c:forEach items="${types}" var="type">
                                <form:option value="${type}" label="${type}"></form:option>
                            </c:forEach>
                        </form:select>
                    </spring:bind>
                </div>
                <spring:bind path="startDate">
                    <label style="font-weight: bold" for="typ">
                        Data przed zmianą: ${event.startDateTime.dayOfMonth}-${event.startDateTime.monthValue}-${event.startDateTime.year}
                    </label>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="date" path="startDate" class="form-control"
                                    placeholder="Data"></form:input>
                        <form:errors path="startDate"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="startTime">
                    <label style="font-weight: bold" for="typ">
                        Godzina przed zmianą: ${event.startDateTime.toLocalTime()}
                    </label>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="time" path="startTime" class="form-control"
                                    placeholder="Godzina"></form:input>
                        <form:errors path="startTime"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="duration">
                    <label style="font-weight: bold" for="typ">
                        Czas trwania przed zmianą: ${event.calculateDuration()}
                    </label>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="number" path="duration" min="5" max="180"
                                    class="form-control "
                                    placeholder="Czas trwania (minuty)"></form:input>
                        <form:errors path="duration"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="room">
                    <label style="font-weight: bold" for="typ">
                        Sala przed zmianą: ${event.room}
                    </label>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="room" class="form-control"
                                    placeholder="Sala"></form:input>
                        <form:errors path="room"></form:errors>
                    </div>
                </spring:bind>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Potwierdź</button>
            </form:form>

        </div>
        <div class="col-sm-6 col-6 col-lg-6" style="min-width: 200px">
            <div class="container">
                <div>
                    <h4 class="mbr-section-title align-center pb-3 mbr-fonts-style display-5">
                        <br>
                        Kalendarz wizyt wszystkich terapeutów
                    </h4>
                </div>
                <iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;
showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;hl=pl&amp;bgcolor=%23c0c0c0&amp;
<c:forEach items="${therapists}" var="therapist">
src=${therapist.googleCalendarId}&amp;color=${therapist.colour}&amp;
</c:forEach>
ctz=Europe%2FWarsaw" style="border-width:0" width="500" height="600" frameborder="0" scrolling="no"></iframe>
            </div>
        </div>
    </div>
</div>

</body>
</html>
