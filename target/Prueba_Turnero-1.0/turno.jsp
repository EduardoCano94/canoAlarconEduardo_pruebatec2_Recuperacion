<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Gestión de Turnos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 1.2em;
        }

        .form-container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            max-width: 600px;
            margin: 0 auto 30px auto;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: auto;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f8f9fa;
            font-weight: 600;
            color: #333;
        }

        .actions {
            display: flex;
            gap: 10px;
        }

        .actions a {
            text-decoration: none;
            color: #007bff;
        }

        .actions a:hover {
            text-decoration: underline;
        }

        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            text-align: center;
        }

        .message.success {
            background-color: #d4edda;
            color: #155724;
        }

        .message.error {
            background-color: #f8d7da;
            color: #721c24;
        }

        input[type="date"] {
            appearance: none;
            -webkit-appearance: none;
            background-color: white;
            cursor: pointer;
        }

        input[type="date"]::-webkit-calendar-picker-indicator {
            background-color: transparent;
            cursor: pointer;
            padding: 5px;
        }

        .nav-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-bottom: 30px;
        }

        .nav-buttons a {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .nav-buttons a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="nav-buttons">
        <a href="index.jsp">Volver al Inicio</a>
        <a href="LoginSv?action=logout">Cerrar sesión</a>
    </div>

    <h1>Bienvenido, admin al Sistema de Gestión de Turnos</h1>
    
    <!-- Mensajes de éxito o error -->
    <c:if test="${not empty message}">
        <div class="message ${messageType}">${message}</div>
    </c:if>

    <!-- Formulario para crear o editar un turno -->
    <div class="form-container">
        <h2>Gestión de Trámites</h2>
        <form action="turnos" method="POST">
            <input type="hidden" name="action" value="${not empty turno ? 'modificar' : 'crear'}">
            <c:if test="${not empty turno}">
                <input type="hidden" name="id" value="${turno.idTurno}">
            </c:if>
            
            <label for="fecha">Fecha:</label>
            <input type="date" id="fecha" name="fecha" 
                   value="${not empty turno ? turno.fecha : ''}" required>
            
            <label for="estado">Estado:</label>
            <input type="text" id="estado" name="estado" 
                   value="${not empty turno ? turno.estado : ''}" required>
            
            <label for="descripcion">Descripción:</label>
            <input type="text" id="descripcion" name="descripcion" 
                   value="${not empty turno ? turno.descripcion : ''}" required>
            
            <label for="numeroTurno">Número de Turno:</label>
            <input type="number" id="numeroTurno" name="numeroTurno" 
                   value="${not empty turno ? turno.numeroTurno : ''}" required>
            
            <label for="ciudadanoId">ID del Ciudadano:</label>
            <input type="number" id="ciudadanoId" name="ciudadanoId" 
                   value="${not empty turno ? turno.ciudadano.idCiudadano : ''}" required>
            
            <label for="tramiteId">ID del Trámite:</label>
            <input type="number" id="tramiteId" name="tramiteId" 
                   value="${not empty turno ? turno.tramite.idTramite : ''}" required>
            
            <button type="submit">
                ${not empty turno ? 'Guardar Cambios' : 'Agregar Trámite'}
            </button>
        </form>
    </div>

    <!-- Lista de turnos -->
    <h2>Lista de Trámites</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${empty turnos}">
                <tr>
                    <td colspan="4" style="text-align: center;">No hay trámites disponibles</td>
                </tr>
            </c:if>
            <c:forEach var="t" items="${turnos}">
                <tr>
                    <td>${t.idTurno}</td>
                    <td>${t.tramite.nombre}</td>
                    <td>${t.descripcion}</td>
                    <td class="actions">
                        <a href="turnos?action=editar&id=${t.idTurno}">Editar</a>
                        <a href="turnos?action=eliminar&id=${t.idTurno}" 
                           onclick="return confirm('¿Estás seguro de eliminar este trámite?')">
                            Eliminar
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>