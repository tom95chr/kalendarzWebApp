<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible">
    <meta name="generator" content="Mobirise v4.3.5, mobirise.com">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="resources/assets/images/logo-418x449.png" type="image/x-icon">
    <meta name="description" content="">
    <title>Uczelniane Centrum Wsparcia</title>
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
<section class="menu cid-qz9d6i0EaI" once="menu" id="menu1-e" data-rv-view="71">


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
                         <img src="resources/assets/images/logo-418x449.png" alt="Centrum Wsparcia" title="" media-simple="true"
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
            <!-- not logged -->
            <sec:authorize access="isAnonymous()">

            <div class="navbar-buttons mbr-section-btn"><a class="btn btn-sm btn-primary display-7"
                                                           href="/login"><span
                    class="mbri-unlock mbr-iconfont mbr-iconfont-btn"></span>
                Login
            </a></div>
            </sec:authorize>
            <!--logged-->
            <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
            <div class="navbar-buttons mbr-section-btn"><a class=" btn btn-primary display-7" data-toggle="modal" data-target="#loginModal"><span
                class="mbri-lock mbr-iconfont mbr-iconfont-btn"></span>
                <sec:authentication property="principal.username" />
            </a></div>
            </sec:authorize>
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
                <h5 class="modal-title" style="color: black; font-weight: bold">
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
                        <sec:authentication property="principal.username" />
                    </sec:authorize>
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

<section class="header1 cid-qz9qEBoDIb" id="header1-z" data-rv-view="62">


    <div class="container">
        <div class="row justify-content-md-center">
            <div class="mbr-white col-md-10">
                <h1 class="mbr-section-title align-center mbr-bold pb-3 mbr-fonts-style display-2">
                    Uczelniane Centrum Wsparcia&nbsp;</h1>
                <h3 class="mbr-section-subtitle align-center mbr-light pb-3 mbr-fonts-style display-2"></h3>
                <p class="mbr-text align-center pb-3 mbr-fonts-style display-5 font-weight-bold">Witaj na stronie głównej Uczelnianego
                    Centrum Wsparcia<br> przy PWSZ w Tarnowie<br><br></p>

            </div>
        </div>
    </div>

</section>

<section class="features6 cid-qz9kSVYRAX" id="features6-p" data-rv-view="65">


    <div class="container">
        <div class="media-container-row">

            <div class="card p-3 col-12 col-md-6 col-lg-4">
                <div class="card-img pb-3">
                    <span class="mbr-iconfont mbri-sad-face" media-simple="true"></span>
                </div>
                <div class="card-box">
                    <h4 class="card-title py-3 mbr-fonts-style display-7">
                        Studencie masz problem ?</h4>
                    <p class="mbr-text mbr-fonts-style display-7">My pomożemy Ci go rozwiązać.<br><br><br></p>
                </div>
            </div>

            <div class="card p-3 col-12 col-md-6 col-lg-4">
                <div class="card-img pb-3">
                    <span class="mbr-iconfont mbri-smile-face" media-simple="true"></span>
                </div>
                <div class="card-box">
                    <h4 class="card-title py-3 mbr-fonts-style display-7">Spotkaj się z terapeutą... <br>ZA DARMO !</h4>
                    <p class="mbr-text mbr-fonts-style display-7">Po prostu wybierz terapeutę poniżej i zapisz się na
                        wizytę.<br></p>
                </div>
            </div>

            <div class="card p-3 col-12 col-md-6 col-lg-4">
                <div class="card-img pb-3">
                    <span class="mbr-iconfont mbri-lock" media-simple="true"></span>
                </div>
                <div class="card-box">
                    <h4 class="card-title py-3 mbr-fonts-style display-7">
                        Pełna anonimowość</h4>
                    <p class="mbr-text mbr-fonts-style display-7">Bez obaw, nie gromadzimy Twoich danych. Do zapisu
                        wymagane są jedynie email i telefon.</p>
                </div>
            </div>
        </div>

    </div>

</section>

<section class="features8 cid-qz9i5WARlL mbr-parallax-background" id="features8-m" data-rv-view="68">

    <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(35, 35, 35);">
    </div>
    <div class="container">
        <div class="row">

            <c:forEach items="${therapists}" var="therapist" varStatus="status">
                <div class="card col-sm-4 col-md-6 col-lg-4">
                    <div class="card-img">
                        <span class="mbr-iconfont mbri-users" media-simple="true"></span>
                    </div>
                    <div class="card-box align-center">
                        <h4 class="card-title mbr-fonts-style display-7">
                                ${therapist.firstName} ${therapist.lastName}
                        </h4>
                        <p class="mbr-text mbr-fonts-style display-7">${therapist.specialization}</p>
                        <p class="mbr-text mbr-fonts-style display-7">
                                ${therapist.description}
                        </p>
                        <div class="mbr-section-btn text-center"><a href=/therapist-${therapist.therapistId}
                                                                    class="btn btn-secondary display-4">
                            Wybierz</a></div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<!-- footer -->

<section class="cid-qz9d6nOi74" id="footer1-g" data-rv-view="73">


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
                        © Copyright 2018</p>
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
<script src="resources/assets/jarallax/jarallax.min.js"></script>
<script src="resources/assets/dropdown/js/script.min.js"></script>
<script src="resources/assets/touch-swipe/jquery.touch-swipe.min.js"></script>
<script src="resources/assets/smooth-scroll/smooth-scroll.js"></script>
<script src="resources/assets/theme/js/script.js"></script>

</body>
</html>