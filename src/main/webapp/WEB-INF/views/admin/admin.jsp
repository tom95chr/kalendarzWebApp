<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Admin page</title>
</head>
<body>
	Dear <strong>${users}</strong>, Welcome to Admin Page.<br />
	<a href="<c:url value="/admin/therapists" />">Therapists</a><br />
	<a href="<c:url value="/" />">Home</a><br />
	<a href="<c:url value="/admin/registration" />">Create account</a><br />
	<a href="<c:url value="/admin/event-types" />">Event types settings</a><br/>
</body>
</html>