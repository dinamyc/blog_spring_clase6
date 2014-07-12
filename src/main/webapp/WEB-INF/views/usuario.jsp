<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1><spring:message code="usuario.informacion"/>:</h1>
	<div>
		<spring:message code="usuario.nombre"/>:
		<c:out value="${usuario.nombre}" />
	</div>
	<div>
		<spring:message code="usuario.email"/>:
		<c:out value="${usuario.email}" />
	</div>

</body>
</html>