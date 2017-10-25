<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Poradnia </title>
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

    <h2>Poradnia psychoterapeutyczna przy PWSZ</h2>

    <p>Jeśli szukasz pomocy psychoterapeuty to dobrze trafiles. Poniżej znajduje się przegląd
        naszych specjalistów. Aby zapisać się na wybrany termin spotkania kliknij w "terapeutę"</p>


</section>

<!-- Therapists -->

<section class="row">
    <div class="grid">
        <c:forEach items="${therapists}" var="therapist" varStatus="status">
        <section class="teaser col-1-3">
            <h5>${therapist.specialization}</h5>
            <a href=/therapist-${therapist.therapistId}>
            <h3>${therapist.firstName} ${therapist.lastName}</h3>
            </a>
            <p>${therapist.description}</p>
        </section>
    </c:forEach>

    </div>
</section>

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