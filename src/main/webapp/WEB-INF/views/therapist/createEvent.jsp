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
    <title>Nowe spotkanie</title>
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
                if (session.getAttribute("loggedUser")=="anonymousUser" || session.getAttribute("loggedUser")==null){
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


<section class="mbr-section form1 cid-qz9pErsY1P" id="form1-1h" data-rv-view="183">

    <br>
    <div class="container">
        <h2 class="align-center pb-2 mbr-fonts-style display-2">
            Wprowadź dane i utwórz spotkanie
        </h2>
    </div>
    <div class="container">
        <div class="row">
            <div class="card col-sm-6 col-lg-6 col-md-6" style="min-width: 200px">
                <div class="row justify-content-center">
                    <div>
                        <h4 class="mbr-section-title align-center pb-3 mbr-fonts-style display-5">
                            <br>
                            Dane spotkania
                        </h4>
                        <h3 class="mbr-section-subtitle align-center mbr-light pb-3 mbr-fonts-style display-5"
                            style="color: red;font-weight: bold">
                            <c:if test="${collidedEvent != null}">
                                NIE UTWORZONO SPOTKANIA !<br> Wybrana sala jest zajęta przez: <br>
                                ${collidedEvent.therapist.specialization} ${collidedEvent.therapist.firstName} ${collidedEvent.therapist.lastName}
                                <br>dnia ${collidedEvent.startDateTime.dayOfMonth}-${collidedEvent.startDateTime.monthValue}-${collidedEvent.startDateTime.year}
                                od godz: ${collidedEvent.startDateTime.toLocalTime()}
                                do godz. ${collidedEvent.endDateTime.toLocalTime()}
                                <c:if test="${!collidedEvent.startDateTime.toLocalDate().isEqual(collidedEvent.endDateTime.toLocalDate())}">
                                    dnia: ${collidedEvent.endDateTime.dayOfMonth}-${collidedEvent.endDateTime.monthValue}-${collidedEvent.endDateTime.year}
                                </c:if>
                                <br>${info}
                            </c:if>
                        </h3>
                        <c:if test="${collidedEvent == null}">
                            <h3 class="mbr-section-subtitle align-center mbr-light pb-3 mbr-fonts-style display-5"
                                style="color: #1f00ff; font-weight: 600">
                                    ${successInfo}
                            </h3>
                        </c:if>
                    </div>
                </div>

                <div class="container">
                    <div class="row justify-content-center">
                        <div class="media-container-column">
                            <form:form method="POST" modelAttribute="eventDTO" class="form-signin">
                                <div class="row row-sm-offset">
                                    <div class="col-md-6 multi-horizontal" data-for="eventType">
                                        <spring:bind path="eventType">
                                            <label style="font-weight: bold" for="typ">Wybierz typ spotkania</label>
                                            <form:select class="form-control" path="eventType" id="typ">
                                                <c:forEach items="${eventTypes}" var="event">
                                                    <form:option value="${event.eventTypeId}"
                                                                 label="${event.eventTypeId}"></form:option>
                                                </c:forEach>
                                            </form:select>
                                        </spring:bind>
                                    </div>

                                    <div class="col-md-6 multi-horizontal" data-for="startDate">
                                        <spring:bind path="startDate">
                                            <label style="font-weight: bold" for="typ">
                                                Data:
                                            </label>
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <form:input type="date" path="startDate" class="form-control"
                                                            placeholder="Data"></form:input>
                                                <form:errors path="startDate"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>

                                    <div class="col-md-6 multi-horizontal" data-for="startTime">
                                        <spring:bind path="startTime">
                                            <label style="font-weight: bold" for="typ">
                                                Godzina:
                                            </label>
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <form:input type="time" path="startTime" class="form-control"
                                                            placeholder="Godzina"></form:input>
                                                <form:errors path="startTime"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>

                                    <div class="col-md-6 multi-horizontal" data-for="duration">
                                        <spring:bind path="duration">
                                            <label style="font-weight: bold" for="typ">
                                                Czas trwania:
                                            </label>
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <form:input type="number" path="duration" min="5" max="180"
                                                            class="form-control "
                                                            placeholder="Czas trwania (minuty)"></form:input>
                                                <form:errors path="duration"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>
                                    <div class="col-md-6 multi-horizontal" data-for="room">
                                        <spring:bind path="room">
                                            <label style="font-weight: bold" for="typ">
                                                Sala:
                                            </label>
                                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                                <form:input type="text" path="room" class="form-control"
                                                            placeholder="Sala"></form:input>
                                                <form:errors path="room"></form:errors>
                                            </div>
                                        </spring:bind>
                                    </div>
                                    <div class="col-md-6 multi-horizontal" data-for="numberOfRepetitions">
                                        <spring:bind path="numberOfRepetitions">
                                            <label style="font-weight: bold" for="typ">
                                                Czy powtarzać spotkanie ?
                                            </label>
                                            <div class="form-group" ${status.error ? 'has-error' : ''}">
                                            <form:input type="number" path="numberOfRepetitions" min="0" max="53"
                                                        class="form-control"
                                                        placeholder="Liczba tygodni"></form:input>
                                            <form:errors path="numberOfRepetitions"></form:errors>
                                        </spring:bind>
                                    </div>


                                </div>
                                <span class="col-md-12 multi-horizontal input-group-btn justify-content-center">
                                    <button type="submit" class="btn btn-primary btn-form display-4">Potwierdź</button>
                                </span>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

            <div class="col-sm-5 col-5 col-lg-5" style="min-width: 200px">
                <div class="container">
                    <div>
                        <h4 class="mbr-section-title align-center pb-3 mbr-fonts-style display-5">
                            <br>
                            Twoje spotkania
                        </h4>
                    </div>
                    <div class="container scroll">
                        <table class="table isSearch" cellspacing="0">
                            <thead>
                            <tr class="table-heads ">
                                <th class="head-item mbr-fonts-style display-7 align-center" style="font-size: small">
                                    Typ
                                </th>
                                <th class="head-item mbr-fonts-style display-7 align-center" style="font-size: small">
                                    Data
                                </th>
                                <th class="head-item mbr-fonts-style display-7 align-center" style="font-size: small">
                                    Godz.
                                </th>
                                <th class="head-item mbr-fonts-style display-7 align-center" style="font-size: small">
                                    Trwa
                                </th>
                                <th class="head-item mbr-fonts-style display-7 align-center" style="font-size: small">
                                    Sala
                                </th>
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${events}" var="event" varStatus="status">
                                <tr>
                                    <td class="body-item mbr-fonts-style display-7" style="font-size: small">${event.eventType.eventTypeId}</td>
                                    <td class="body-item mbr-fonts-style display-7 align-right" style="font-size: small">${event.startDateTime.dayOfMonth}-${event.startDateTime.monthValue}-${event.startDateTime.year}</td>
                                    <td class="body-item mbr-fonts-style display-7" style="font-size: small">${event.startDateTime.toLocalTime()}</td>
                                    <td class="body-item mbr-fonts-style display-7" style="font-size: small">${event.calculateDuration()}min.</td>
                                    <td class="body-item mbr-fonts-style display-7" style="font-size: small">${event.room}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- footer -->

<section class="cid-qz9pV4HiBN" id="footer1-1i" data-rv-view="186">


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
<script src="resources/assets/dropdown/js/script.min.js"></script>
<script src="resources/assets/touch-swipe/jquery.touch-swipe.min.js"></script>
<script src="resources/assets/smooth-scroll/smooth-scroll.js"></script>
<script src="resources/assets/theme/js/script.js"></script>
<script src="resources/assets/formoid/formoid.min.js"></script>


</body>
</html>
