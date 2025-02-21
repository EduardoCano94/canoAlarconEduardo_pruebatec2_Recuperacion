package com.hackaboss.servlets;

import com.hackaboss.logica.Ciudadano;
import com.hackaboss.logica.ControladoraLogica;
import com.hackaboss.logica.Tramite;
import com.hackaboss.logica.Turno;
import com.hackaboss.persistencia.ControladoraPersistencia;
import com.hackaboss.persistencia.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TurnoServlet", urlPatterns = {"/turnos"})
public class TurnoServlet extends HttpServlet {

    private ControladoraLogica controlLogica = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "listar":
                    listarTurnos(request, response);
                    break;
                case "buscar":
                    buscarTurno(request, response);
                    break;
                case "editar":
                    editarTurno(request, response);
                    break;
                case "eliminar":
                    eliminarTurno(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } else {
            listarTurnos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "crear":
                    crearTurno(request, response);
                    break;
                case "modificar":
                    modificarTurno(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
        }
    }

    private void listarTurnos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Turno> turnos = controlLogica.traerTurnos();
        List<Ciudadano> ciudadanos = controlLogica.traerCiudadanos();
        List<Tramite> tramites = controlLogica.traerTramites();
        request.setAttribute("turnos", turnos);
        request.setAttribute("ciudadanos", ciudadanos);
        request.setAttribute("tramites", tramites);
        request.getRequestDispatcher("turno.jsp").forward(request, response);
    }

    private void buscarTurno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Turno turno = controlLogica.buscarTurno(id);

            if (turno != null) {
                request.setAttribute("turno", turno);
                request.setAttribute("message", "Turno encontrado con éxito");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Turno no encontrado");
                request.setAttribute("messageType", "error");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID de turno inválido");
            request.setAttribute("messageType", "error");
        } finally {
            request.getRequestDispatcher("turno.jsp").forward(request, response);
        }
    }

    private void editarTurno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Turno turno = controlLogica.buscarTurno(id);

            if (turno != null) {
                request.setAttribute("turno", turno);
                List<Ciudadano> ciudadanos = controlLogica.traerCiudadanos();
                List<Tramite> tramites = controlLogica.traerTramites();
                request.setAttribute("ciudadanos", ciudadanos);
                request.setAttribute("tramites", tramites);
                request.setAttribute("message", "Turno cargado para edición");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Turno no encontrado");
                request.setAttribute("messageType", "error");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID de turno inválido");
            request.setAttribute("messageType", "error");
        } finally {
            request.getRequestDispatcher("turno.jsp").forward(request, response);
        }
    }

    private void crearTurno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha"));
            String estado = request.getParameter("estado");
            String descripcion = request.getParameter("descripcion");
            Integer numeroTurno = Integer.parseInt(request.getParameter("numeroTurno"));
            Long ciudadanoId = Long.parseLong(request.getParameter("ciudadanoId"));
            Long tramiteId = Long.parseLong(request.getParameter("tramiteId"));

            Ciudadano ciudadano = controlLogica.buscarCiudadano(ciudadanoId.intValue());
            Tramite tramite = controlLogica.buscarTramite(tramiteId);

            if (ciudadano != null && tramite != null) {
                controlLogica.crearTurno(fecha, estado, descripcion, numeroTurno, ciudadano, tramite);
                request.setAttribute("message", "Turno creado con éxito");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Ciudadano o Trámite no encontrado");
                request.setAttribute("messageType", "error");
            }
        } catch (ParseException e) {
            request.setAttribute("message", "Formato de fecha inválido");
            request.setAttribute("messageType", "error");
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Número de turno, ciudadano o trámite inválido");
            request.setAttribute("messageType", "error");
        } finally {
            listarTurnos(request, response);
        }
    }

    private void modificarTurno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha"));
            String estado = request.getParameter("estado");
            String descripcion = request.getParameter("descripcion");
            Integer numeroTurno = Integer.parseInt(request.getParameter("numeroTurno"));
            Long ciudadanoId = Long.parseLong(request.getParameter("ciudadanoId"));
            Long tramiteId = Long.parseLong(request.getParameter("tramiteId"));

            Turno turno = controlLogica.buscarTurno(id);
            if (turno != null) {
                Ciudadano ciudadano = controlLogica.buscarCiudadano(ciudadanoId.intValue());
                Tramite tramite = controlLogica.buscarTramite(tramiteId);
                
                if (ciudadano != null && tramite != null) {
                    turno.setFecha(fecha);
                    turno.setEstado(estado);
                    turno.setDescripcion(descripcion);
                    turno.setNumeroTurno(numeroTurno);
                    turno.setCiudadano(ciudadano);
                    turno.setTramite(tramite);

                    controlLogica.modificarTurno(turno);
                    request.setAttribute("message", "Turno modificado con éxito");
                    request.setAttribute("messageType", "success");
                } else {
                    request.setAttribute("message", "Ciudadano o Trámite no encontrado");
                    request.setAttribute("messageType", "error");
                }
            } else {
                request.setAttribute("message", "Turno no encontrado");
                request.setAttribute("messageType", "error");
            }
        } catch (ParseException e) {
            request.setAttribute("message", "Formato de fecha inválido");
            request.setAttribute("messageType", "error");
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Datos numéricos inválidos");
            request.setAttribute("messageType", "error");
        } catch (Exception e) {
            request.setAttribute("message", "Error al modificar el turno: " + e.getMessage());
            request.setAttribute("messageType", "error");
        } finally {
            listarTurnos(request, response);
        }
    }

    private void eliminarTurno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            controlLogica.eliminarTurno(id);
            request.setAttribute("message", "Turno eliminado con éxito");
            request.setAttribute("messageType", "success");
        } catch (NonexistentEntityException e) {
            request.setAttribute("message", "Turno no encontrado");
            request.setAttribute("messageType", "error");
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID de turno inválido");
            request.setAttribute("messageType", "error");
        } finally {
            listarTurnos(request, response);
        }
    }
}