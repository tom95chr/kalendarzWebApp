<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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

    <%--
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Reservation details</title>
    --%>

    <%--    <link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
        <link href="${contextPath}/resources/css/common.css" rel="stylesheet">--%>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
<!--    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
    <!--[endif]-->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Potwierdzenie </title>
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

    <p>${pageTypeInfo}</p>

</section>

<!-- confirmation -->
<section class="row">
    <div class="grid">
        <section class="teaser col-1-2">
            <h5>${info1}</h5>
            <p>${info2} swoją rezerwację, proszę podać kod rezerwacji.</p>
            <%--<h2 style="color: red">${confirmationFailed}</h2>--%>
        </section><!--
        -->
        <section class="teaser col-1-2 confirm">
            <form:form method="POST" modelAttribute="confirmationCode" class="form-signin">
                    <h3 class="form-signin-heading">Unikalny kod rezerwacji</h3>
                    <spring:bind path="code">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="code" class="form-control" placeholder="Kod"
                                        autofocus="true"></form:input>
                            <form:errors path="code"></form:errors>
                        </div>
                    </spring:bind>
                <p style="color: red">${confirmationFailed}</p>
                <button class="btn btn-submit" type="submit">Wyślij</button>
            </form:form>
        </section>
    </div>
</section>
<%--<div class="container">
    <a href="<c:url value="/" />">Home</a><br />


    <h1 style="color: red">${confirmationFailed}</h1>
    <h2>${info1}</h2>
    <form:form method="POST" modelAttribute="confirmationCode" class="form-signin">
        <h2 class="form-signin-heading">${info2} your reservation, please submit reservation code</h2>
        <spring:bind path="code">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="code" class="form-control" placeholder="Confirmation code"
                            autofocus="true"></form:input>
                <form:errors path="code"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>--%>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>--%>
<!-- Footer -->

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