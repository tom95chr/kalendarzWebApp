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
                <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                    <li class="nav-item"><a class="nav-link link text-white display-4" href="/admin"><span
                            class="mbri-star mbr-iconfont mbr-iconfont-btn"></span>Admin</a></li>
                </sec:authorize>
            </ul>
            <!-- login button -->
            <%
                if (session.getAttribute("loggedUser")=="anonymousUser"){
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

</section><section class="countdown2 cid-qzc7TOs7aU" id="countdown2-1b" data-rv-view="103">


    <div class="container">
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
            <h2 class="mbr-section-subtitle align-center mbr-fonts-style display-5">
                Tutaj możesz utworzyć nowe wydarzenie / wydarzenie cykliczne
            </h2>
    </div>

</section>
<section class="mbr-section form1 cid-qzgWxdEqVc" id="form1-1f" data-rv-view="138">


    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-12 col-lg-8">
                <h2 class="mbr-section-title align-center pb-3 mbr-fonts-style display-2">
                    <%--DANE KONTAKTOWE--%>
                </h2>
                <h3 class="mbr-section-subtitle align-center mbr-light pb-3 mbr-fonts-style display-5" style="color: red;">
                    ${info}
                </h3>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row justify-content-center">
            <div class="media-container-column col-lg-4">

                <form:form method="POST" modelAttribute="eventDTO" class="form-signin">
                    <div class="form-group">
                        <spring:bind path="eventType">
                            <label for="typ">Wybierz typ spotkania</label>
                            <form:select class="form-control" path="eventType" id="typ">
                                <c:forEach items="${eventTypes}" var="event">
                                        <form:option value="${event.eventTypeId}" label="${event.eventTypeId}"></form:option>
                                </c:forEach>
                            </form:select>
                        </spring:bind>
                    </div>
                    <spring:bind path="startDate">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="date" path="startDate" class="form-control"
                                        placeholder="Data"></form:input>
                            <form:errors path="startDate"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="startTime">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="time" path="startTime" class="form-control"
                                        placeholder="Godzina"></form:input>
                            <form:errors path="startTime"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="duration">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="number" path="duration" min="5" max="180" class="form-control "
                                        placeholder="Czas trwania (minuty)"></form:input>
                            <form:errors path="duration"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="room">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="room" class="form-control" placeholder="Sala"></form:input>
                            <form:errors path="room"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="numberOfRepetitions">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="number" path="numberOfRepetitions" min="0" max="53" class="form-control"
                                        placeholder="Powtarzaj przez ? tyg."></form:input>
                            <form:errors path="numberOfRepetitions"></form:errors>
                        </div>
                    </spring:bind>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Utwórz</button>
                </form:form>
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

<%--
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create event</title>

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

<a href="<c:url value="/therapist-events" />">Home</a><br/>

<div class="container">

    <form:form method="POST" modelAttribute="eventDTO" id="formularz">
        <h2 class="form-signin-heading">Event data </h2>

        &lt;%&ndash;       <spring:bind path="name">
                   <div class="form-group ${status.error ? 'has-error' : ''}">
                       <form:input type="text" path="name" class="form-control" placeholder="Event name"></form:input>
                       <form:errors path="name"></form:errors>
                   </div>
               </spring:bind>&ndash;%&gt;

        <br>Event type
        <spring:bind path="eventType">
            <form:select path="eventType">
                <c:forEach items="${eventTypes}" var="event"> &lt;%&ndash;varStatus="status"&ndash;%&gt;>
                    <tr>
                        <form:option value="${event.eventTypeId}" label="${event.eventTypeId}"/>
                    </tr>
                </c:forEach>
            </form:select>
        </spring:bind>
        <br>
        <spring:bind path="startDateTime">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="startDateTime" class="form-control"
                            placeholder="Start date/time    Example 06-09-2017 18:00"></form:input>
                    &lt;%&ndash;autofocus="true"></form:input>&ndash;%&gt;
                <form:errors path="startDateTime"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="endDateTime">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="endDateTime" class="form-control"
                            placeholder="End date/time    Example 06-09-2017 18:00"></form:input>
                <form:errors path="endDateTime"></form:errors>
            </div>
        </spring:bind>


        <spring:bind path="room">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="room" class="form-control" placeholder="Room"></form:input>
                <form:errors path="room"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="numberOfRepetitions">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="number" path="numberOfRepetitions" min="0" max="53" class="form-control"
                            placeholder="If you want to create weekly repeated event, just add weeks"></form:input>
                <form:errors path="numberOfRepetitions"></form:errors>
            </div>
        </spring:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>
<div class="container">
    <h2>${error}</h2>
    <c:if test="${collidedEvent != null}">
        <h2 style="color: red">Upppps... this term is not available.</h2>
        <h2 style="color: red">This room is occupied by ${collidedEvent.therapist.firstName} ${collidedEvent.therapist.lastName}
            from: ${collidedEvent.startDateTime.dayOfMonth}-${collidedEvent.startDateTime.monthValue}-${collidedEvent.startDateTime.year}
            godz. ${collidedEvent.startDateTime.toLocalTime()} to: ${collidedEvent.endDateTime.dayOfMonth}-${collidedEvent.endDateTime.monthValue}-${collidedEvent.endDateTime.year}
            godz. ${collidedEvent.endDateTime.toLocalTime()} </h2>
    </c:if>

    <h2 style="color: red">${info}</h2>
    &lt;%&ndash;    <c:if test="${collidedEvent == null}">
            <h2>${eventCreated}</h2>
        </c:if>&ndash;%&gt;
</div>
<div class="container">
    <h2>All therapist events in one calendar</h2>
    <iframe src="https://calendar.google.com/calendar/embed?showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;
showTz=0&amp;mode=WEEK&amp;height=600&amp;wkst=2&amp;hl=pl&amp;bgcolor=%23c0c0c0&amp;
<c:forEach items="${therapists}" var="therapist">
src=${therapist.googleCalendarId}&amp;color=${therapist.colour}&amp;
</c:forEach>
ctz=Europe%2FWarsaw" style="border-width:0" width="800" height="600" frameborder="0" scrolling="no"></iframe>
</div>

<!-- /container -->
&lt;%&ndash;<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>&ndash;%&gt;
</body>
</html>

--%>
