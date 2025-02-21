<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Gestión de Ciudadanos</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <h1>Gestión de Ciudadanos</h1>

        <!-- Formulario para agregar o editar un ciudadano -->
<h2>${not empty ciudadano ? 'Editar Ciudadano' : 'Agregar Nuevo Ciudadano'}</h2>
<form action="CiudadanoSv" method="post">
    <input type="hidden" name="action" value="${not empty ciudadano ? 'update' : 'create'}">
    <c:if test="${not empty ciudadano}">
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
    <input type="submit" value="${not empty ciudadano ? 'Actualizar' : 'Agregar'} Ciudadano">
</form>
        <!-- Formulario para buscar ciudadano por DNI -->
        <h2>Buscar Ciudadano por DNI</h2>
        <form action="CiudadanoSv" method="get">
            <form action="CiudadanoSv" method="get">
                <input type="hidden" name="action" value="search">
                <div class="form-group">
                    <label for="dni">DNI:</label>
                    <input type="text" id="dni" name="dni" required>
                </div>
                <input type="submit" value="Buscar">

            </form>

            <!-- Resultado de la búsqueda -->
            <c:if test="${not empty ciudadanoBuscado}">
                <h2>Resultado de la búsqueda</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>DNI</th>
                        <th>Email</th>
                        <th>Acciones</th>
                    </tr>
                    <tr>
                        <td>${ciudadanoBuscado.idCiudadano}</td>
                        <td>${ciudadanoBuscado.nombre}</td>
                        <td>${ciudadanoBuscado.apellido}</td>
                        <td>${ciudadanoBuscado.dni}</td>
                        <td>${ciudadanoBuscado.email}</td>
                        <td>
                            <a href="CiudadanoSv?action=edit&id=${ciudadanoBuscado.idCiudadano}">Editar</a>
                            <a href="CiudadanoSv?action=delete&id=${ciudadanoBuscado.idCiudadano}">Eliminar</a>
                        </td>
                    </tr>
                </table>
            </c:if>

            <!-- Lista de ciudadanos -->
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
                            <a href="CiudadanoSv?action=edit&id=${ciudadano.idCiudadano}">Editar</a>
                            <a href="CiudadanoSv?action=delete&id=${ciudadano.idCiudadano}">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <!-- Mensajes de éxito o error -->
            <c:if test="${not empty mensaje}">
                <p style="color: green;">${mensaje}</p>
            </c:if>
            <c:if test="${not empty mensajeError}">
                <p style="color: red;">${mensajeError}</p>
            </c:if>
    </body>
</html>