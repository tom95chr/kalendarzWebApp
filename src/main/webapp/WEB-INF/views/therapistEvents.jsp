<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Strona terapeuty</title>
</head>
<body>
	Dear <strong>${users}</strong>, Strona terapeuty do obslugi dodawania/usuwania/blokowania terminow
	<a href="<c:url value="/logout" />">Logout</a>
</body>
</html>