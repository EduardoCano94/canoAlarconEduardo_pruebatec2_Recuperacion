<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Error</h1>

    <!-- Mensaje de error -->
    <div class="mensaje error">
        <p>${mensajeError}</p>
    </div>

    <!-- Enlace para volver a la página principal -->
    <p><a href="index.jsp">Volver a la página principal</a></p>
</body>
</html>