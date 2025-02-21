<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Gestión de Trámites</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <script>
            function buscarTramite() {
                const idTramite = document.getElementById('idTramite').value;
                if (!idTramite) {
                    alert('Por favor ingrese un ID de trámite');
                    return false;
                }
                return true;
            }

            function confirmarEliminacion() {
                return confirm('¿Estás seguro de eliminar este trámite?');
            }
        </script>
        <style>
            .message {
                padding: 10px;
                margin: 10px 0;
                border-radius: 4px;
                text-align: center;
            }
            .success {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }
            .error {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
            }
            .form-container {
                margin: 20px 0;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .table-container {
                margin: 20px 0;
                overflow-x: auto;
            }
            .action-buttons {
                display: flex;
                gap: 10px;
                justify-content: center;
            }
            .action-button {
                padding: 5px 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                background-color: #007bff;
                color: white;
            }
            .action-button:hover {
                background-color: #0056b3;
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            .header-cell {
                background-color: #f8f9fa;
                font-weight: bold;
                padding: 10px;
                text-align: left;
                border: 1px solid #dee2e6;
            }
            td {
                padding: 10px;
                border: 1px solid #dee2e6;
            }
            nav ul {
                list-style-type: none;
                padding: 0;
                margin: 0;
                display: flex;
                background-color: #f8f9fa;
                border-radius: 4px;
            }
            nav ul li {
                padding: 10px;
            }
            nav ul li a {
                text-decoration: none;
                color: #007bff;
            }
            nav ul li a:hover {
                color: #0056b3;
            }
            
            /* Nuevos estilos para la navegación */
            .nav-container {
                display: flex;
                justify-content: flex-end;
                gap: 10px;
                padding: 10px;
                margin-bottom: 20px;
            }
            
            .nav-button {
                padding: 8px 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                font-weight: 500;
            }
            
            .home-button {
                background-color: #007bff;
                color: white;
            }
            
            .home-button:hover {
                background-color: #0056b3;
            }
            
            .logout-button {
                background-color: #dc3545;
                color: white;
            }
            
            .logout-button:hover {
                background-color: #c82333;
            }

            /* Estilos para los campos de formulario */
            input[type="text"],
            input[type="number"],
            textarea {
                width: 100%;
                padding: 8px;
                margin: 8px 0;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }

            textarea {
                min-height: 100px;
                resize: vertical;
            }

            label {
                display: block;
                margin-top: 10px;
                font-weight: 500;
            }
        </style>
    </head>
    <body>
        <div class="nav-container">
            <a href="index.jsp" class="nav-button home-button">Inicio</a>
            <a href="LoginSv?action=logout" class="nav-button logout-button">Cerrar Sesión</a>
        </div>

        <h1>Bienvenido, admin al Sistema de Gestión de Turnos</h1>

        <h2>Gestión de Trámites</h2>

        <div class="form-container">
            <h3>${tramite != null ? 'Actualizar' : 'Agregar Nuevo'} Trámite</h3>
            <form action="TramiteSv" method="post">
                <input type="hidden" name="action" value="${tramite != null ? 'update' : 'create'}">
                <c:if test="${tramite != null}">
                    <input type="hidden" name="idTramite" value="${tramite.idTramite}">
                </c:if>

                <div>
                    <label for="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" value="${tramite.nombre}" required>
                </div>

                <div>
                    <label for="descripcion">Descripción:</label>
                    <textarea id="descripcion" name="descripcion" required>${tramite.descripcion}</textarea>
                </div>

                <div>
                    <label for="nombre2">Nombre:</label>
                    <input type="text" id="nombre2" name="nombre2" value="${tramite.nombre}" required>
                </div>

                <button type="submit" class="action-button">
                    ${tramite != null ? 'Actualizar' : 'Agregar'} Trámite
                </button>
            </form>
        </div>

        <div class="form-container">
            <h3>Buscar Trámite por ID</h3>
            <form action="TramiteSv" method="get" onsubmit="return buscarTramite()">
                <input type="hidden" name="action" value="search">
                <div>
                    <label for="idTramite">ID Trámite:</label>
                    <input type="number" id="idTramite" name="idTramite" min="1" required>
                </div>
                <button type="submit" class="action-button">Buscar</button>
            </form>
        </div>

        <div class="table-container">
            <h3>Lista de Trámites</h3>
            <table>
                <thead>
                    <tr>
                        <th class="header-cell">ID</th>
                        <th class="header-cell">Nombre</th>
                        <th class="header-cell">Descripción</th>
                        <th class="header-cell">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty listaTramites}">
                            <c:forEach var="tramiteItem" items="${listaTramites}">
                                <tr>
                                    <td>${tramiteItem.idTramite}</td>
                                    <td>${tramiteItem.nombre}</td>
                                    <td>${tramiteItem.descripcion}</td>
                                    <td>
                                        <div class="action-buttons">
                                            <form action="TramiteSv" method="get" style="display: inline;">
                                                <input type="hidden" name="action" value="edit">
                                                <input type="hidden" name="id" value="${tramiteItem.idTramite}">
                                                <button type="submit" class="action-button">Editar</button>
                                            </form>
                                            <form action="TramiteSv" method="post" style="display: inline;" 
                                                  onsubmit="return confirmarEliminacion()">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="id" value="${tramiteItem.idTramite}">
                                                <button type="submit" class="action-button" 
                                                        style="background-color: #dc3545;">Eliminar</button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4" style="text-align: center;">No hay trámites disponibles</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <c:if test="${not empty mensaje}">
            <div class="message success">${mensaje}</div>
        </c:if>
        <c:if test="${not empty mensajeError}">
            <div class="message error">${mensajeError}</div>
        </c:if>
    </body>
</html>