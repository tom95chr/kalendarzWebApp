<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create event</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/sutiStyle.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
<!--    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
    <![endif]-->
</head>

<body>

<a href="<c:url value="/therapist-events" />">Home</a><br/>

<div class="container">

    <form:form method="POST" modelAttribute="eventDTO" id="formularz">
        <h2 class="form-signin-heading">Event data </h2>

        <%--       <spring:bind path="name">
                   <div class="form-group ${status.error ? 'has-error' : ''}">
                       <form:input type="text" path="name" class="form-control" placeholder="Event name"></form:input>
                       <form:errors path="name"></form:errors>
                   </div>
               </spring:bind>--%>

        <br>Event type
        <spring:bind path="eventType">
            <form:select path="eventType">
                <c:forEach items="${eventTypes}" var="event"> <%--varStatus="status"--%>>
                    <tr>
                        <form:option value="${event.eventTypeId}" label="${event.eventTypeId}"/>
                    </tr>
                </c:forEach>
            </form:select>
        </spring:bind>
        <br>
        <spring:bind path="startDateTime">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="startDateTime" class="form-control"
                            placeholder="Start date/time    Example 06-09-2017 18:00"></form:input>
                    <%--autofocus="true"></form:input>--%>
                <form:errors path="startDateTime"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="endDateTime">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="endDateTime" class="form-control"
                            placeholder="End date/time    Example 06-09-2017 18:00"></form:input>
                <form:errors path="endDateTime"></form:errors>
            </div>
        </spring:bind>


        <spring:bind path="room">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="room" class="form-control" placeholder="Room"></form:input>
                <form:errors path="room"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="numberOfRepetitions">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="number" path="numberOfRepetitions" min="0" max="53" class="form-control"
                            placeholder="If you want to create weekly repeated event, just add weeks"></form:input>
                <form:errors path="numberOfRepetitions"></form:errors>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>
<div class="container">
    <h2>${error}</h2>
    <c:if test="${collidedEvent != null}">
        <h2 style="color: red">Upppps... this term is not available.</h2>
        <h2 style="color: red">This room is occupied by ${collidedEvent.therapist.firstName} ${collidedEvent.therapist.lastName}
            from: ${collidedEvent.startDateTime.toString()} to: ${collidedEvent.endDateTime.toString()} </h2>
    </c:if>
    <h2 style="color: red">${info}</h2>
    <%--    <c:if test="${collidedEvent == null}">
            <h2>${eventCreated}</h2>
        </c:if>--%>
</div>
<div class="container">
    <h2>All therapist events in one calendar</h2>
    <iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;
showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;hl=pl&amp;bgcolor=%23c0c0c0&amp;
<c:forEach items="${therapists}" var="therapist">
src=${therapist.googleCalendarId}&amp;color=${therapist.colour}&amp;
</c:forEach>
ctz=Europe%2FWarsaw" style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>
</div>

<!-- /container -->
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>--%>
</body>
</html>

