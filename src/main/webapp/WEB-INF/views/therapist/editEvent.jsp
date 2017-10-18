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
    <link rel="stylesheet" href="resources/css/sutiStyle.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
<!--    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
    <![endif]-->
</head>
<body>

<div class="container">
    <h1 style="color: red">${editError}</h1>
    <c:if test="${collidedEvent != null}">
        <h2 style="color: red">Collision found !</h2>
        <h2 style="color: red">This room is occupied by ${collidedEvent.therapist.firstName} ${collidedEvent.therapist.lastName}
            from: ${collidedEvent.startDateTime.toLocalDate()} godz. ${collidedEvent.startDateTime.toLocalTime()}
            to: ${collidedEvent.endDateTime.toLocalDate()} ${collidedEvent.endDateTime.toLocalTime()}</h2>
    </c:if>
    <form:form method="POST" modelAttribute="eventDTO" id="formularz">
        <h2 class="form-signin-heading">Edit and save</h2>

        <%--       <spring:bind path="name">
                   <div class="form-group ${status.error ? 'has-error' : ''}">
                       <form:input type="text" path="name" class="form-control" placeholder="Event name"></form:input>
                       <form:errors path="name"></form:errors>
                   </div>
               </spring:bind>--%>

        <br>Event type:
        <spring:bind path="eventType">
            <form:select path="eventType">
                <c:forEach items="${types}" var="type"> <%--varStatus="status"--%>>
                    <tr>
                        <form:option value="${type}" label="${type}"/>
                    </tr>
                </c:forEach>
            </form:select>
        </spring:bind>
        <br>

        <spring:bind path="startDateTime">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="startDateTime" class="form-control"
                            placeholder="Start date/time: ${event.startDateTime.toLocalDate()} ${event.startDateTime.toLocalTime()}"></form:input>
                    <%--autofocus="true"></form:input>--%>
                <form:errors path="startDateTime"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="endDateTime">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="endDateTime" class="form-control"
                            placeholder="End date/time: ${event.endDateTime.toLocalDate()} ${event.endDateTime.toLocalTime()}"></form:input>
                <form:errors path="endDateTime"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="room">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="room" class="form-control" placeholder="Room: ${event.room}"></form:input>
                <form:errors path="room"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>


</div>

</body>
</html>
