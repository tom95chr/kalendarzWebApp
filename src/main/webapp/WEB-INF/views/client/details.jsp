<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <!-- Site made with Mobirise Website Builder v4.3.5, https://mobirise.com -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="generator" content="Mobirise v4.3.5, mobirise.com">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="resources/assets/images/logo-418x449.png" type="image/x-icon">
    <meta name="description" content="Site Maker Description">
    <title>Szczegóły</title>
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
                if (session.getAttribute("loggedUser")=="anonymousUser" || session.getAttribute("loggedUser")==null){
                    %>
            <div class="navbar-buttons mbr-section-btn"><a class="btn btn-sm btn-primary display-7"
                                                           href="/login"><span
                    class="mbri-unlock mbr-iconfont mbr-iconfont-btn"></span>

                Login
            </a></div>
            <%
            } else{
            %>
            <div class="navbar-buttons mbr-section-btn"><a class=" btn btn-primary display-7" data-toggle="modal" data-target="#loginModal"><span
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
                <h5 class="modal-title" style="color: black; font-weight: bold"><%= session.getAttribute("loggedUser")%></h5>
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


    <section class="engine"><a href="https://mobirise.co/f">free bootstrap builder</a></section>
    <section class="countdown2 cid-qzbXDziNBS" id="countdown2-1a" data-rv-view="311">


        <div class="container">
            <h3 class="mbr-section-subtitle align-center mbr-fonts-style display-5">

            </h3>
            <h2 class="mbr-section-title pb-3 align-center mbr-fonts-style display-2 font-weight-bold"
                style="color: white; background-color:rgba(0, 0, 0, 0.8)">
                Szczegóły Twojej rezerwacji
            </h2>
        </div>
        <div class="container pt-5 mt-2">
            <div class=" countdown-cont align-center p-4">
                <div class="event-name align-left mbr-white ">
                    <h4 class="mbr-fonts-style display-5" style="color: black;">Do rozpoczęcia spotkania pozostało</h4>
                </div>
                <div class="countdown align-center py-2" data-due-date="${event.startDateTime.year}/${event.startDateTime.monthValue}/${event.startDateTime.dayOfMonth}/${event.startDateTime.hour}:${event.startDateTime.minute}">
            </div>
            <div class="daysCountdown" title="Dni"></div>
            <div class="hoursCountdown" title="Godzin"></div>
            <div class="minutesCountdown" title="Minut"></div>
            <div class="secondsCountdown" title="Sekund"></div>
            <div class="event-date align-left mbr-white">
                <h5 class="mbr-fonts-style display-7" style="color: black;">${event.startDateTime.year}/${event.startDateTime.monthValue}/${event.startDateTime.dayOfMonth} godz. ${event.getStartDateTime().toLocalTime()}
            </div>
        </div>

        </div>
    </section>

    <section class="counters2 counters cid-qzbW9mqBmg" id="counters2-18" data-rv-view="314">

        <div class="container pt-4 mt-2">
            <div class="media-container-row">
                <div class="media-block" style="width: 50%;">
                    <h2 class="mbr-section-title pb-3 align-left mbr-fonts-style display-2">
                        Wskazówki dojazdu
                    </h2>
                         <h3 class="mbr-section-subtitle pb-5 align-left mbr-fonts-style display-5">

                         </h3>
                    <div class="mbr-figure">
                        <div class="google-map"><iframe frameborder="0" style="border:0; height: 400px"  src="https://www.google.com/maps/embed/v1/place?key=AIzaSyA0Dx_boXQiwvdz8sJHoYeZNVTdoWONYkU&amp;q=place_id:ChIJaaxo_lSDPUcRno-mYFwjFrA" allowfullscreen=""></iframe></div>
                    </div>
                </div>
                <div class="cards-block">
                    <h2 class="mbr-section-title mb-3" style="color: red"> Twoja rezerwacja została potwierdzona pomyślnie !
                    </h2>
                    <p class="mb-5">Dziękujemy za skorzystanie z naszj strony.
                        Zapraszamy na spotkanie. Poniżej znajdziesz szczegóły Twojej rezerwacji.
                    </p>
                    <div class="cards-container">

                        <div class="card px-3 align-center col-12 col-md-6">
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
                                        tel.
                                        <c:if test="${therapist.telephone.length()==0}">
                                                brak
                                        </c:if>
                                        ${therapist.telephone}
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="card px-3 align-center col-12 col-md-6">
                            <div class="panel-item p-3">
                                <div class="card-text">
                                    <h4 style="color: #0f77f6">Typ spotkania</h4>
                                </div>
                                <div class="card-text">
                                    <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                        <br>${event.eventType.eventTypeId}
                                    </h4>
                                    <p class="mbr-content-text mbr-fonts-style display-7">
                                        Maksymalna liczba uczestników : ${event.eventType.seats} <br>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="card px-3 align-center col-12 col-md-6">
                            <div class="panel-item p-3">
                                <div class="card-text">
                                    <h4 style="color: #0f77f6">Czas i miejsce spotkania</h4>
                                </div>
                                <div class="card-text">
           <%--                         <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">

                                    </h4>--%>
                                    <p class="mbr-content-text mbr-fonts-style display-7">
                                        <br>Data: ${event.startDateTime.dayOfMonth} - ${event.startDateTime.monthValue} - ${event.startDateTime.year}
                                        <br>Godzina: ${event.getStartDateTime().toLocalTime()}
                                        <br>Sala nr: ${event.room}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="card px-3 align-center col-12 col-md-6">
                            <div class="panel-item p-3">
                                <div class="card-text">
                                    <h4 style="color: red">Anuluj rezerwację</h4>
                                </div>
                                <div class="card-text">
                                    <h4 class="mbr-content-title mbr-bold mbr-fonts-style display-7">
                                        <!-- informacje dodatkowe pogrubione -->
                                    </h4>
                                    <p class="mbr-content-text mbr-fonts-style display-7">
                                        Aby anulować swoją rezerwację, <a
                                            href="/my-reservation-${confirmationCode}/cancel">kliknij tutaj</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

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
                <div class="col-md-6">

                </div>
            </div>
        </div>
    </div>
</section>


<script src="resources/assets/web/assets/jquery/jquery.min.js"></script>
<script src="resources/assets/popper/popper.min.js"></script>
<script src="resources/assets/tether/tether.min.js"></script>
<script src="resources/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="resources/assets/smooth-scroll/smooth-scroll.js"></script>
<script src="resources/assets/touch-swipe/jquery.touch-swipe.min.js"></script>
<script src="resources/assets/countdown/jquery.countdown.min.js"></script>
<script src="resources/assets/viewport-checker/jquery.viewportchecker.js"></script>
<script src="resources/assets/dropdown/js/script.min.js"></script>
<script src="resources/assets/theme/js/script.js"></script>


</body>
</html>