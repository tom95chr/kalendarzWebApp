<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>
<head>
    <!-- Site made with Mobirise Website Builder v4.3.5, https://mobirise.com -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="generator" content="Mobirise v4.3.5, mobirise.com">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="resources/assets/images/logo-418x449.png" type="image/x-icon">
    <meta name="description" content="Web Site Maker Description">
    <title>Rezerwacja</title>
    <link rel="stylesheet" href="resources/assets/web/assets/mobirise-icons/mobirise-icons.css">
    <link rel="stylesheet" href="resources/assets/tether/tether.min.css">
    <link rel="stylesheet" href="resources/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/assets/bootstrap/css/bootstrap-grid.min.css">
    <link rel="stylesheet" href="resources/assets/bootstrap/css/bootstrap-reboot.min.css">
    <link rel="stylesheet" href="resources/assets/socicon/css/styles.css">
    <link rel="stylesheet" href="resources/assets/dropdown/css/style.css">
    <link rel="stylesheet" href="resources/assets/theme/css/style.css">
    <link rel="stylesheet" href="resources/assets/mobirise/css/mbr-additional.css" type="text/css">


</head>
<body>
<section class="menu cid-qz9d6i0EaI" once="menu" id="menu1-e" data-rv-view="684">


    <nav class="navbar navbar-expand beta-menu navbar-dropdown align-items-center navbar-fixed-top navbar-toggleable-sm">
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <div class="hamburger">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>
        </button>
        <div class="menu-logo">
            <div class="navbar-brand">
                <span class="navbar-logo">
                    <a href="/">
                         <img src="resources/assets/images/logo-418x449.png" alt="Poradnia" title="" media-simple="true"
                              style="height: 4.8rem;">
                    </a>
                </span>

            </div>
        </div>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav nav-dropdown" data-app-modern-menu="true">
                <li class="nav-item">
                    <a class="nav-link link text-white display-4" href="/"><span
                            class="mbri-home mbr-iconfont mbr-iconfont-btn"></span>&nbsp;Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link link text-white display-4" href="/confirm-reservation"><span
                            class="mbri-success mbr-iconfont mbr-iconfont-btn"></span>
                        Potwierdzenie</a>
                </li>
                <li class="nav-item"><a class="nav-link link text-white display-4" href="/my-reservation"><span
                        class="mbri-desktop mbr-iconfont mbr-iconfont-btn"></span>
                    &nbsp;Moja rezerwacja</a></li>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
                    <li class="nav-item"><a class="nav-link link text-white display-4" href="/therapist-events"><span
                            class="mbri-smile-face mbr-iconfont mbr-iconfont-btn"></span>Terapeuta</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                    <li class="nav-item"><a class="nav-link link text-white display-4" href="/admin"><span
                            class="mbri-star mbr-iconfont mbr-iconfont-btn"></span>Admin</a></li>
                </sec:authorize>
            </ul>
            <!-- login button -->
            <%
                if (session.getAttribute("loggedUser") == "anonymousUser" || session.getAttribute("loggedUser") == null) {
            %>
            <div class="navbar-buttons mbr-section-btn"><a class="btn btn-sm btn-primary display-7"
                                                           href="/login"><span
                    class="mbri-unlock mbr-iconfont mbr-iconfont-btn"></span>

                Login
            </a></div>
            <%
            } else {
            %>
            <div class="navbar-buttons mbr-section-btn"><a class=" btn btn-primary display-7" data-toggle="modal"
                                                           data-target="#loginModal"><span
                    class="mbri-lock mbr-iconfont mbr-iconfont-btn"></span>
                <%= session.getAttribute("loggedUser")%>
                <%
                    }
                %>
                <!-- login button -->

            </a></div>
        </div>
    </nav>
</section>

<!-- logout modal -->
<div class="modal fade" id="loginModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title" style="color: black">Użytkownik:</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body align-center">
                <h5 class="modal-title" style="color: black; font-weight: bold"><%= session.getAttribute("loggedUser")%>
                </h5>
                <div class="navbar-buttons mbr-section-btn"><a class="btn btn-sm btn-primary display-7"
                                                               href="/logout"><span
                        class="mbri-unlock mbr-iconfont mbr-iconfont-btn"></span>

                    Wyloguj
                </a></div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-dark" data-dismiss="modal">Zamknij</button>
            </div>

        </div>
    </div>
</div>
<c:if test="${freeSlots>0}">
    <section class="tabs2 cid-qzgWo7cLCL" id="tabs2-1e" data-rv-view="135">


        <div class="container col-12">
            <h2 class="mbr-section-title align-center pb-5 mbr-fonts-style display-2">
                Zapisz się już teraz
            </h2>
            <div class="media-container-row">
                <div class="card px-3 align-center col-sm-4">
                    <div class="panel-item p-3">
                        <div class="card-text">
                            <h4 style="color: #0f77f6">${therapist.specialization}</h4>
                        </div>
                        <div class="card-text">
                            <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                <br>${therapist.firstName} ${therapist.lastName}
                            </h4>
                            <p class="mbr-content-text mbr-fonts-style display-7">
                                email: ${therapist.email}<br>
                                tel. ${therapist.telephone}
                            </p>
                        </div>
                    </div>
                </div>
                <div class="card px-3 align-center col-sm-4">
                    <div class="panel-item p-3">
                        <div class="card-text">
                            <h4 style="color: #0f77f6">Informacje</h4>
                        </div>
                        <div class="card-text">
                                <%--<h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                </h4>--%>
                            <p class="mbr-content-text mbr-bold mbr-fonts-style display-7">
                                Typ spotkania: ${event.eventType.eventTypeId}<br>
                                Data: ${event.startDateTime.toLocalDate()}<br>
                                Godzina: ${event.startDateTime.toLocalTime()}<br>
                                Sala nr: ${event.room}<br>
                            </p>

                        </div>
                    </div>
                </div>
                <div class="card px-3 align-center col-sm-4">
                    <div class="panel-item p-3">
                        <div class="card-text">
                            <h4 style="color: #0f77f6">Wolne miejsca</h4>
                        </div>
                        <div class="card-text">
                            <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                <br>Liczba wolnych miejsc: ${freeSlots}
                            </h4>
                            <p class="mbr-content-text mbr-fonts-style display-7">
                                Maksymalna liczba uczestników: ${event.eventType.seats} <br>

                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="mbr-section form1 cid-qzgWxdEqVc" id="form1-1f" data-rv-view="138">


        <div class="container">
            <div class="row justify-content-center">
                <div class="title col-12 col-lg-8">
                    <h2 class="mbr-section-title align-center pb-3 mbr-fonts-style display-2">
                        DANE KONTAKTOWE
                    </h2>
                    <h3 class="mbr-section-subtitle align-center mbr-light pb-3 mbr-fonts-style display-5">
                        Aby się zapisać na wybrany termin wystarczy, że podasz swój adres email. Upewnij się, podany
                        email
                        jest prawidłowy. Zostanie na niego wysłany kod potwierdzenia. Opcjonalnie możesz też dodać
                        numer telefonu co ułatwi nam kontakt w razie ewentualnych zmian.
                    </h3>
                </div>
            </div>
        </div>
        <div class="container">
                    <form:form method="POST" modelAttribute="reservationDto" class="form-signin">
                        <div class="row row-sm-offset">
                        <spring:bind path="email">
                            <div class="form-group col-md-4 multi-horizontal" data-for="email ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="email" class="form-control"
                                            placeholder="Adres email"></form:input>
                                <form:errors path="email"></form:errors>
                            </div>
                        </spring:bind>
                        <spring:bind path="emailConfirm">
                            <div class="form-group col-md-4 multi-horizontal" data-for="emailConfirm ${status.error ? 'has-error' : ''}">
                                <form:input type="email" path="emailConfirm" class="form-control"
                                            placeholder="Podaj email jeszcze raz"></form:input>
                                <form:errors path="emailConfirm"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="telephone">
                            <div class="form-group col-md-4 multi-horizontal" data-for="telephone ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="telephone" class="form-control"
                                            placeholder="Telefon (opcjonalnie)"></form:input>
                                <form:errors path="telephone"></form:errors>
                            </div>
                        </spring:bind>
                            <div class="form-group col-md-3 multi-horizontal">
                            </div>
                            <spring:bind path="recaptchaResponse">
                                <div class="form-group col-md-4 multi-horizontal" data-for="recaptchaResponse ${status.error ? 'has-error' : ''}">
                                    <div id="g-recaptcha"></div>
                                    <form:hidden path="recaptchaResponse"/>
                                    <script type="text/javascript">
                                        var onloadCallback = function () {
                                            grecaptcha.render('g-recaptcha', {
                                                'sitekey': '<c:out value="${recaptchaSiteKey}" />',
                                                'callback': function (response) {
                                                    document.getElementById('recaptchaResponse').value = response;
                                                    if(response){
                                                        $('#submitButton').attr('disabled', false);
                                                    }
                                                },
                                                'theme': 'light'
                                            });
                                        }
                                    </script>
                                    <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit" async
                                            defer></script>
                                        <%--<form:errors path="recaptchaResponse" class="help-block"/>--%>
                                    <form:errors path="recaptchaResponse" cssStyle="color: red"></form:errors>
                                </div>
                            </spring:bind>
                            <span class="form-group col-md-4 multi-horizontal" data-for="recaptchaResponse input-group-btn ">
                                <button id="submitButton" disabled type="submit" class="btn btn-primary display-4 justify-content-center">Zatwierdź</button>
                            </span>
                            <div class="form-group col-md-1 multi-horizontal">
                            </div>
                        </div>
                    </form:form>
        </div>
    </section>
</c:if>
<!-- footer -->

<section class="cid-qz9d6nOi74" id="footer1-g" data-rv-view="686">


    <div class="container">
        <div class="media-container-row content text-white">
            <div class="col-12 col-md-3">
                <div class="media-wrap">
                    <a href="/">
                        <img src="resources/assets/images/logo-418x449.png" alt="Mobirise" title="" media-simple="true">
                    </a>
                </div>
            </div>
            <div class="col-12 col-md-3 mbr-fonts-style display-7">
                <h5 class="pb-3">
                    Adres</h5>
                <p class="mbr-text">Uczelniane Centrum Wsparcia<br>PWSZ Tarnów<br>ul. Mickiewicza 8,<br> 33-100 Tarnów<br>
                </p>
            </div>
            <div class="col-12 col-md-3 mbr-fonts-style display-7">
                <h5 class="pb-3">Linki</h5>
                <p class="mbr-text">
                    <a class="text-primary" href="https://pwsztar.edu.pl/">PWSZ w Tarnowie</a>
                    <br><a class="text-primary" href=""></a>
                    <br><a class="text-primary" href=""></a>
                </p>
            </div>
            <div class="col-12 col-md-3 mbr-fonts-style display-7">
                <h5 class="pb-3">Twórcy</h5>
                <p class="mbr-text">
                    <a class="text-primary" data-toggle="modal" data-target="#myModal">
                        click
                    </a>
                </p>
                <!-- The Modal -->
                <div class="modal fade" id="myModal">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title" style="color: black">Twórcy:</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body">
                                <h5 class="modal-title" style="color: black">Tomasz Chrapusta</h5>
                                <h6 style="color: black">PWSZ Tarnów<br>Informatyka 2014-2018</h6>
                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" data-dismiss="modal">Zamknij</button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-lower">
            <div class="media-container-row">
                <div class="col-sm-12">
                    <hr>
                </div>
            </div>
            <div class="media-container-row mbr-white">
                <div class="col-sm-6 copyright">
                    <p class="mbr-text mbr-fonts-style display-7">
                        © Copyright 2017</p>
                </div>

            </div>
        </div>
    </div>
    </div>
</section>


<script src="resources/assets/web/assets/jquery/jquery.min.js"></script>
<script src="resources/assets/popper/popper.min.js"></script>
<script src="resources/assets/tether/tether.min.js"></script>
<script src="resources/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="resources/assets/dropdown/js/script.min.js"></script>
<script src="resources/assets/touch-swipe/jquery.touch-swipe.min.js"></script>
<script src="resources/assets/smooth-scroll/smooth-scroll.js"></script>
<script src="resources/assets/theme/js/script.js"></script>
<script src="resources/assets/formoid/formoid.min.js"></script>


</body>
</html>
