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
    <link rel="shortcut icon" href="assets/images/logo-418x449.png" type="image/x-icon">
    <meta name="description" content="">
    <title>Rezerwacja/Potwierdzenie</title>
    <link rel="stylesheet" href="resources/assets/web/assets/mobirise-icons/mobirise-icons.css">
    <link rel="stylesheet" href="resources/assets/tether/tether.min.css">
    <link rel="stylesheet" href="resources/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/assets/bootstrap/css/bootstrap-grid.min.css">
    <link rel="stylesheet" href="resources/assets/bootstrap/css/bootstrap-reboot.min.css">
    <link rel="stylesheet" href="resources/assets/dropdown/css/style.css">
    <link rel="stylesheet" href="resources/assets/socicon/css/styles.css">
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
                         <img src="resources/assets/images/logo-418x449.png" alt="Mobirise" title="" media-simple="true"
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

<!-- content -->
<section class="engine"><a href="https://mobirise.co/a">web page builder</a>
</section>
<section class="mbr-section form3 cid-qz9nB3ZLjc" id="form3-q" data-rv-view="691">

    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-12 col-lg-8">
                <h2 class="align-center pb-2 mbr-fonts-style display-2">
                    ${info2}</h2>
                <h3 class="mbr-section-subtitle align-center pb-5 mbr-light mbr-fonts-style display-5"><strong>
                    Tu możesz ${pageTypeInfo} swoją rezerwację, wystarczy podać unikalny kod rezerwacji.
                </strong></h3>
                <p class="mbr-section-subtitle align-center pb-5 mbr-light mbr-fonts-style display-5 small">
                    Sprawdź swój email wklej otrzymany od nas kod potwierdzenia i kliknij ,,Zatwierdź".
                </p>
            </div>
        </div>

        <%--<h5>${info1}</h5>
            <p>${info2} swoją rezerwację, proszę podać kod rezerwacji.</p>--%>
        <div class="row py-2 justify-content-center">
            <div class="col-12 col-lg-8  col-md-8 " data-form-type="formoid">

                <form:form class="form-group" method="POST" modelAttribute="confirmationCode">
                <div class="row row-sm-offset">
                    <spring:bind path="code">
                        <div class="form-group col-md-10 multi-horizontal" data-for="code ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="code" class="form-control" placeholder="Kod"></form:input>
                            <form:errors path="code"></form:errors>
                        </div>
                    </spring:bind>


                    <p style="color: red">${confirmationFailed}</p>

                    <spring:bind path="recaptchaResponse">
                        <div class="form-group col-md-6 multi-horizontal" data-for="recaptchaResponse ${status.error ? 'has-error' : ''}">
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
                    </form:form>
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
<script src="resources/assets/dropdown/js/script.min.js"></script>
<script src="resources/assets/touch-swipe/jquery.touch-swipe.min.js"></script>
<script src="resources/assets/theme/js/script.js"></script>


</body>
</html>

