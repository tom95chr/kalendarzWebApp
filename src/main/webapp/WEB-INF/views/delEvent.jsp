<%--
  Created by IntelliJ IDEA.
  User: Agnieszka
  Date: 2017-06-21
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Title</title>
</head>
<body>
Zajecia ${event.name} zostaly usuniete.

<a href="<c:url value="/event/eventList-${user}" />">Ok</a><br/> <br/>
</body>
</html>
