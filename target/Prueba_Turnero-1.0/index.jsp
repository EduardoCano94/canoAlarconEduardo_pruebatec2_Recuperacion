<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String usuario = (String) sesion.getAttribute("usuario");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Turnos - Ciudadanos</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Bienvenido, <%= usuario %> al Sistema de Gestión de Turnos</h1>
    <nav>
        <ul>
            <li><a href="ciudadano.jsp">Ciudadano</a></li>
            <li><a href="tramite.jsp">Trámite</a></li>
            <li><a href="turno.jsp">Turno</a></li>
            <li><a href="LoginSv?action=logout">Cerrar Sesión</a></li>
        </ul>
    </nav>

    <h1>Gestión de Ciudadanos</h1>
    <h2>${ciudadano != null ? 'Editar Ciudadano' : 'Agregar Nuevo Ciudadano'}</h2>
    <form action="CiudadanoSv" method="post">
        <input type="hidden" name="action" value="${ciudadano != null ? 'update' : 'create'}">
        <c:if test="${ciudadano != null}">
            <input type="hidden" name="idCiudadano" value="${ciudadano.idCiudadano}">
        </c:if>
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${ciudadano.nombre}" required><br><br>
        <label for="apellido">Apellido:</label>
        <input type="text" id="apellido" name="apellido" value="${ciudadano.apellido}" required><br><br>
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" value="${ciudadano.dni}" required><br><br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${ciudadano.email}" required><br><br>
        <input type="submit" value="${ciudadano != null ? 'Actualizar' : 'Agregar'} Ciudadano">
    </form>

    <h1>Buscar Ciudadano por DNI</h1>
    <form action="CiudadanoSv" method="get">
        <input type="hidden" name="action" value="search">
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" required>
        <input type="submit" value="Buscar">
    </form>

    <h2>Lista de Ciudadanos</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>DNI</th>
            <th>Email</th>
            <th>Acciones</th>
        </tr>
        <c:forEach var="ciudadano" items="${listaCiudadanos}">
            <tr>
                <td>${ciudadano.idCiudadano}</td>
                <td>${ciudadano.nombre}</td>
                <td>${ciudadano.apellido}</td>
                <td>${ciudadano.dni}</td>
                <td>${ciudadano.email}</td>
                <td>
                    <a href="CiudadanoSv?action=edit&id=${ciudadano.idCiudadano}">Editar</a> |
                    <a href="CiudadanoSv?action=delete&id=${ciudadano.idCiudadano}" onclick="return confirm('¿Estás seguro de eliminar este ciudadano?')">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${not empty mensaje}">
        <p style="color: green;">${mensaje}</p>
    </c:if>
    <c:if test="${not empty mensajeError}">
        <p style="color: red;">${mensajeError}</p>
    </c:if>
</body>
</html>
