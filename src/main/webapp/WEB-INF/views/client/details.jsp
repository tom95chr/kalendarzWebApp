<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  Client: Lapek
  Date: 06.10.2017
  Time: 19:38
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
    <title>Reservation details</title>

    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>


<div class="container">
    <a href="<c:url value="/" />">Home</a><br />

    <c:if test="${cancelSuccess!=null}">
        <h2>${cancelSuccess}</h2>
    </c:if>
    <c:if test="${cancelSuccess==null}">
        <h1>Hi, ${information}<br> We are waiting for you... bla bla bla</h1>
        <h1>Host</h1>
        <h2>${therapist.firstName} ${therapist.lastName} <br>
            Specialization: ${therapist.specialization} <br>
            Description: ${therapist.description}
        </h2>
        <h1>Event details</h1>
        <h2>
            name: ${event.name} <br>
            start: ${event.startDateTime.dayOfMonth}-${event.startDateTime.monthValue}-${event.startDateTime.year}
            godz. ${event.startDateTime.toLocalTime()}<br>
            end: ${event.endDateTime.dayOfMonth}-${event.endDateTime.monthValue}-${event.endDateTime.year}
            godz. ${event.endDateTime.toLocalTime()}<br>
            room: ${event.room}<br>
        </h2>
        <h3>To cancel your reservation, please click <a href="/my-reservation-${confirmationCode}/cancel">here</a></h3>
    </c:if>


</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>