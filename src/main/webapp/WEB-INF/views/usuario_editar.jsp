<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editor usuario</title>
</head>
<body>

	<c:if test="${actualizado}">
		<h2>Datos Actualizados</h2>
	</c:if>

	<!--  SOLUCION EJERCICIO -->
	<c:url var="urlGuardar" value="/usuario/guardar" />

	<form:form action="${urlGuardar}" method="post" commandName="usuario">
		<form:input type="hidden" path="id" />
		<div>
			<label>Nombre</label>
			<form:input type="text" path="nombre" />
		</div>
		<div>
			<form:errors path="nombre" />
		</div>
		<div>
			<label>Email</label>
			<form:input type="text" path="email" />
		</div>
		<div>
			<form:errors path="email" />
		</div>
		<div>
			<label>Password</label>
			<form:input type="password" path="password" />
		</div>
		<div>
			<form:errors path="password" />
		</div>
		<input type="submit" value="enviar" />
	</form:form>

</body>
</html>