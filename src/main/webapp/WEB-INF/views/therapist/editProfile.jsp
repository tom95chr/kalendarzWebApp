<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <!-- Site made with Mobirise Website Builder v4.3.5, https://mobirise.com -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="generator" content="Mobirise v4.3.5, mobirise.com">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="resources/assets/images/logo-418x449.png" type="image/x-icon">
    <meta name="description" content="Web Site Maker Description">
    <title>Edutuj profil</title>
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
<section class="menu cid-qz9d6i0EaI" once="menu" id="menu1-s" data-rv-view="47">


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
                            class="mbri-home mbr-iconfont mbr-iconfont-btn"></span>Home</a>
                </li>
                <%--<li class="nav-item">
                    <a class="nav-link link text-white display-4" href="/confirm-reservation"><span
                            class="mbri-success mbr-iconfont mbr-iconfont-btn"></span>
                        Potwierdzenie</a>
                </li>--%>
                <li class="nav-item"><a class="nav-link link text-white display-4" href="/therapist-createEvent"><span
                        class="mbri-plus mbr-iconfont mbr-iconfont-btn"></span>
                    Utwórz spotkanie</a></li>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
                    <li class="nav-item"><a class="nav-link link text-white display-4" href="/therapist-events"><span
                            class="mbri-smile-face mbr-iconfont mbr-iconfont-btn"></span>Terapeuta</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_DBA')">
                    <li class="nav-item"><a class="nav-link link text-white display-4" href="/edit-profile"><span
                            class="mbri-contact-form mbr-iconfont mbr-iconfont-btn"></span>Edytuj profil</a></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                    <li class="nav-item"><a class="nav-link link text-white display-4" href="/admin"><span
                            class="mbri-star mbr-iconfont mbr-iconfont-btn"></span>Admin</a></li>
                </sec:authorize>
            </ul>
            <!-- login button -->
            <%
                if (session.getAttribute("loggedUser") == "anonymousUser" || session.getAttribute("loggedUser") == null) { %>
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

<section class="features8 cid-qz9i5WARlL mbr-parallax-background" id="features8-m" data-rv-view="68">

    <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(35, 35, 35);">
    </div>
    <div class="container">
        <div class="row">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="title col-12 col-lg-8">
                        <h2 class="mbr-section-title align-center pb-3 mbr-fonts-style display-2"
                            style="color: white; font-weight: bold">
                            <br>
                            Edytuj swój profil
                        </h2>
                        <c:if test="${emailChanged}">
                        <h2 class="mbr-section-title align-center pb-3 mbr-fonts-style display-2"
                            style="color: red; font-weight: bold">
                            Zmieniłeś adres email. Wyloguj się aby zatwierdzić zmiany.
                        </h2>
                        </c:if>
                    </div>
                </div>
            </div>
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
                                                                class="btn btn-secondary display-4 disabled">
                        Wybierz</a></div>
                </div>
            </div>
            <%-- form--%>
            <div class="col-sm-8">
                <div class="container" style="color: white">
                    <div class="row justify-content-center">
                        <div class="media-container-column col-lg-8" data-form-type="formoid">
                            <div data-form-alert="" hidden="">
                                Dziękujemy za wypełnienie formularza.
                            </div>

                            <form:form method="POST" modelAttribute="editProfileDTO" class="form-signin">
                                <div class="row row-sm-offset">

                                    <div class="col-md-6 multi-horizontal" data-for="firstName">
                                        <spring:bind path="firstName">
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <label class="form-control-label mbr-fonts-style display-7"
                                                       for="firstName-form1-1n">Imię</label>
                                                <form:input type="text" path="firstName" class="form-control"
                                                            placeholder="${therapist.firstName}"
                                                            id="firstName-form1-1n" maxlength="50"></form:input>
                                                <form:errors path="firstName"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>

                                    <div class="col-md-6 multi-horizontal" data-for="lastName">
                                        <spring:bind path="lastName">
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <label class="form-control-label mbr-fonts-style display-7"
                                                       for="lastName-form1-1n">Nazwisko</label>
                                                <form:input type="text" path="lastName" class="form-control"
                                                            placeholder="${therapist.lastName}"
                                                            id="lastName-form1-1n" maxlength="50"></form:input>
                                                <form:errors path="lastName"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>

                                    <div class="col-md-6 multi-horizontal" data-for="email">
                                        <spring:bind path="email">
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <label class="form-control-label mbr-fonts-style display-7"
                                                       for="email-form1-1n">Adres email</label>
                                                <form:input type="email" path="email" class="form-control" placeholder="${therapist.email}" id="email-form1-1n" maxlength="50"></form:input>
                                                <form:errors path="email"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>

                                    <div class="col-md-6 multi-horizontal" data-for="telephone">
                                        <spring:bind path="telephone">
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <label class="form-control-label mbr-fonts-style display-7"
                                                       for="telephone-form1-1n">Telefon</label>
                                                <form:input type="tel" path="telephone" class="form-control"
                                                            placeholder="${therapist.telephone}"
                                                            id="telephone-form1-1n" maxlength="20"></form:input>
                                                <form:errors path="telephone"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>
                                    <div class="col-md-12 multi-horizontal" data-for="specialization">
                                        <spring:bind path="specialization">
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <label class="form-control-label mbr-fonts-style display-7"
                                                       for="specialization-form1-1n">Specjalizacja</label>
                                                <form:input type="text" path="specialization" class="form-control"
                                                            placeholder="${therapist.specialization}"
                                                            id="specialization-form1-1n" maxlength="50"></form:input>
                                                <form:errors path="specialization"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>

                                </div>

                                <div class="form-group" data-for="description">
                                    <spring:bind path="description">
                                        <div class="form-group ${status.error ? 'has-error' : ''}">
                                            <label class="form-control-label mbr-fonts-style display-7"
                                                   for="description-form1-1n">Opis</label>
                                            <form:textarea type="text" path="description" class="form-control"
                                                           placeholder="${therapist.description}"
                                                           id="description-form1-1n" rows="5"
                                                           maxlength="255"></form:textarea>
                                            <form:errors path="description"></form:errors>
                                        </div>
                                    </spring:bind>
                                </div>

                                <span class="input-group-btn">
                                    <button type="submit" class="btn btn-primary btn-form display-4">WYŚLIJ</button>
                                </span>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
            <%--formend--%>
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
                <p class="mbr-text">Poradnia Terapeutyczna<br>PWSZ Tarnów<br>ul. Mickiewicza 8,<br> 33-100 Tarnów<br>
                </p>
            </div>
            <div class="col-12 col-md-3 mbr-fonts-style display-7">
                <h5 class="pb-3">Linki</h5>
                <p class="mbr-text">
                    <a class="text-primary" href="https://mobirise.com/">Website builder</a>
                    <br><a class="text-primary" href="https://mobirise.com/mobirise-free-win.zip">Download for
                    Windows</a>
                    <br><a class="text-primary" href="https://mobirise.com/mobirise-free-mac.zip">Download for Mac</a>
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
<script src="resources/assets/data-tables/jquery.data-tables.min.js"></script>
<script src="resources/assets/data-tables/data-tables.bootstrap4.min.js"></script>
<script src="resources/assets/jarallax/jarallax.min.js"></script>
<script src="resources/assets/dropdown/js/script.min.js"></script>
<script src="resources/assets/theme/js/script.js"></script>

</body>
</html>