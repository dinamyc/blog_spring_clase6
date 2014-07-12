<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 
<!doctype html/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Post</title>
</head>
<body>

   <h1><spring:message code="post.titulo.actual"/></h1>
   
   <c:out value="${nombre}" />
   
   <h2><c:out value="${post.titulo}"/></h2>  
   <p><c:out value="${post.contenido}"/></p>
   
   <h2><spring:message code="post.comentarios"/>:</h2>
   <c:forEach var="comentario" items="${post.comentarios}">
       <h4><c:out value="${comentario.comentarista}"/> Dice:</h4>
       <p><c:out value="${comentario.comentario}"/></p>
   </c:forEach>

</body>
</html>