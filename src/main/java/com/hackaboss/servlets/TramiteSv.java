package com.hackaboss.servlets;

import com.hackaboss.logica.ControladoraLogica;
import com.hackaboss.logica.Tramite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TramiteSv", urlPatterns = {"/TramiteSv"})
public class TramiteSv extends HttpServlet {

    private final ControladoraLogica controladora = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                // Listar todos los trámites
                List<Tramite> tramites = controladora.traerTramites();
                request.setAttribute("listaTramites", tramites);
                request.getRequestDispatcher("tramite.jsp").forward(request, response);
                return;
            }

            switch (action) {
                case "delete":
                    eliminarTramite(request, response);
                    break;
                case "edit":
                    editarTramite(request, response);
                    break;
                case "search":
                    buscarTramite(request, response);
                    break;
                default:
                    throw new IllegalArgumentException("Acción no reconocida.");
            }

        } catch (Exception e) {
            manejarError(request, response, e, "tramite.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    crearTramite(request, response);
                    break;
                case "update":
                    actualizarTramite(request, response);
                    break;
                case "delete":
                    eliminarTramite(request, response);
                    break;
                default:
                    throw new IllegalArgumentException("Acción no válida");
            }

            // Redirigir solo si no hay errores
            if (request.getAttribute("mensajeError") == null) {
                response.sendRedirect("TramiteSv");
            } else {
                request.getRequestDispatcher("tramite.jsp").forward(request, response);
            }

        } catch (Exception e) {
            manejarError(request, response, e, "tramite.jsp");
        }
    }

    private void buscarTramite(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String idStr = request.getParameter("idTramite");

        if (idStr == null || idStr.isEmpty()) {
            request.setAttribute("mensajeError", "ID no válido");
            request.setAttribute("listaTramites", new ArrayList<>());
            request.getRequestDispatcher("tramite.jsp").forward(request, response);
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            Tramite tramite = controladora.buscarTramite(id);

            if (tramite != null) {
                List<Tramite> tramites = new ArrayList<>();
                tramites.add(tramite);
                request.setAttribute("listaTramites", tramites);
            } else {
                request.setAttribute("mensajeError", "Trámite no encontrado");
                request.setAttribute("listaTramites", new ArrayList<>());
            }

            request.getRequestDispatcher("tramite.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("mensajeError", "ID debe ser un número válido");
            request.setAttribute("listaTramites", new ArrayList<>());
            request.getRequestDispatcher("tramite.jsp").forward(request, response);
        }
    }

    private void eliminarTramite(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            request.setAttribute("mensajeError", "ID no válido");
            request.getRequestDispatcher("tramite.jsp").forward(request, response);
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            controladora.eliminarTramite(id);
            request.setAttribute("mensaje", "Trámite eliminado correctamente");
            
            // Actualizar la lista de trámites después de eliminar
            List<Tramite> tramites = controladora.traerTramites();
            request.setAttribute("listaTramites", tramites);
            
        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error al eliminar el trámite: " + e.getMessage());
        }

        request.getRequestDispatcher("tramite.jsp").forward(request, response);
    }

    private void editarTramite(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            request.setAttribute("mensajeError", "ID no válido");
            request.getRequestDispatcher("tramite.jsp").forward(request, response);
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            Tramite tramite = controladora.buscarTramite(id);

            if (tramite != null) {
                request.setAttribute("tramite", tramite);
                // Mantener la lista de trámites visible
                List<Tramite> tramites = controladora.traerTramites();
                request.setAttribute("listaTramites", tramites);
            } else {
                request.setAttribute("mensajeError", "Trámite no encontrado");
            }

            request.getRequestDispatcher("tramite.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("mensajeError", "ID debe ser un número válido");
            request.getRequestDispatcher("tramite.jsp").forward(request, response);
        }
    }

    private void crearTramite(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        if (nombre == null || nombre.isEmpty() || descripcion == null || descripcion.isEmpty()) {
            request.setAttribute("mensajeError", "Todos los campos son obligatorios");
            request.getRequestDispatcher("tramite.jsp").forward(request, response);
            return;
        }

        try {
            controladora.crearTramite(nombre, descripcion);
            request.setAttribute("mensaje", "Trámite creado exitosamente");
            
            // Actualizar la lista de trámites después de crear
            List<Tramite> tramites = controladora.traerTramites();
            request.setAttribute("listaTramites", tramites);
            
        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error al crear el trámite: " + e.getMessage());
        }
    }

    private void actualizarTramite(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("idTramite");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        if (idStr == null || idStr.isEmpty() || nombre == null || nombre.isEmpty() || 
            descripcion == null || descripcion.isEmpty()) {
            request.setAttribute("mensajeError", "Todos los campos son obligatorios");
            request.getRequestDispatcher("tramite.jsp").forward(request, response);
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            Tramite tramite = controladora.buscarTramite(id);

            if (tramite == null) {
                request.setAttribute("mensajeError", "Trámite no encontrado");
                request.getRequestDispatcher("tramite.jsp").forward(request, response);
                return;
            }

            tramite.setNombre(nombre);
            tramite.setDescripcion(descripcion);
            controladora.modificarTramite(tramite);

            request.setAttribute("mensaje", "Trámite actualizado correctamente");
            
            // Actualizar la lista de trámites después de modificar
            List<Tramite> tramites = controladora.traerTramites();
            request.setAttribute("listaTramites", tramites);
            
        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error al actualizar el trámite: " + e.getMessage());
        }
    }

    private void manejarError(HttpServletRequest request, HttpServletResponse response, Exception e, String pagina)
            throws ServletException, IOException {
        Logger.getLogger(TramiteSv.class.getName()).log(Level.SEVERE, null, e);
        request.setAttribute("mensajeError", "Error: " + e.getMessage());
        request.setAttribute("listaTramites", new ArrayList<>()); // Lista vacía en caso de error
        request.getRequestDispatcher(pagina).forward(request, response);
    }
}