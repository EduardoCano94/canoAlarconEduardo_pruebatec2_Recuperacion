package com.hackaboss.servlets;

import com.hackaboss.logica.Ciudadano;
import com.hackaboss.logica.ControladoraLogica;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CiudadanoSv", urlPatterns = {"/CiudadanoSv"})
public class CiudadanoSv extends HttpServlet {

    ControladoraLogica controladora = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                // Listar todos los ciudadanos
                List<Ciudadano> ciudadanos = controladora.traerPersonas();
                request.setAttribute("listaCiudadanos", ciudadanos);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                switch (action) {
                    case "search":
                        String dni = request.getParameter("dni");
                        if (dni == null || dni.trim().isEmpty()) {
                            throw new IllegalArgumentException("El DNI es requerido");
                        }

                        Ciudadano encontrado = controladora.buscarPorDni(dni);
                        if (encontrado != null) {
                            request.setAttribute("listaCiudadanos", List.of(encontrado)); // Muestra solo el buscado
                        } else {
                            request.setAttribute("mensajeError", "Ciudadano no encontrado");
                            request.setAttribute("listaCiudadanos", controladora.traerPersonas()); // Muestra lista completa
                        }
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;

                    case "delete":
                        String idStrDelete = request.getParameter("id");
                        if (idStrDelete == null || idStrDelete.isEmpty()) {
                            throw new IllegalArgumentException("ID no válido");
                        }
                        controladora.eliminarCiudadano(Integer.parseInt(idStrDelete));
                        response.sendRedirect("CiudadanoSv");
                        break;

                    case "edit":
                        String idStrEdit = request.getParameter("id");
                        if (idStrEdit == null || idStrEdit.isEmpty()) {
                            throw new IllegalArgumentException("ID no válido");
                        }

                        // Buscar el ciudadano por ID
                        Ciudadano ciudadanoEdit = controladora.buscarCiudadano(Integer.parseInt(idStrEdit));
                        if (ciudadanoEdit != null) {
                            // Enviar el ciudadano al JSP para que el formulario se rellene
                            request.setAttribute("ciudadano", ciudadanoEdit);
                        } else {
                            request.setAttribute("mensajeError", "Ciudadano no encontrado");
                        }
                        // Redirigir al formulario de edición
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;

                    default:
                        throw new IllegalArgumentException("Acción no reconocida.");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CiudadanoSv.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("mensajeError", e.getMessage());
            request.setAttribute("listaCiudadanos", controladora.traerPersonas());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                // Crear nuevo ciudadano
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String dni = request.getParameter("dni");
                String email = request.getParameter("email");

                Ciudadano ciudadano = new Ciudadano();
                ciudadano.setNombre(nombre);
                ciudadano.setApellido(apellido);
                ciudadano.setDni(dni);
                ciudadano.setEmail(email);

                controladora.crearPersona(nombre, apellido, dni, email);
                request.setAttribute("mensaje", "Ciudadano agregado exitosamente");

            } else if ("update".equals(action)) {
                // Actualizar ciudadano existente
                int idCiudadano = Integer.parseInt(request.getParameter("idCiudadano"));
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String dni = request.getParameter("dni");
                String email = request.getParameter("email");

                Ciudadano ciudadano = controladora.buscarCiudadano(idCiudadano);
                if (ciudadano != null) {
                    ciudadano.setNombre(nombre);
                    ciudadano.setApellido(apellido);
                    ciudadano.setDni(dni);
                    ciudadano.setEmail(email);
                    controladora.modificarCiudadano(ciudadano);
                    request.setAttribute("mensaje", "Ciudadano actualizado correctamente");
                } else {
                    request.setAttribute("mensajeError", "Ciudadano no encontrado");
                }
            }

            // Redirigir a la lista de ciudadanos después de crear o actualizar
            response.sendRedirect("CiudadanoSv");

        } catch (Exception e) {
            Logger.getLogger(CiudadanoSv.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("mensajeError", "Error: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}