package com.hackaboss.logica;

import com.hackaboss.persistencia.ControladoraPersistencia;
import com.hackaboss.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ControladoraLogica {

    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    //CRUD ciudadano
    public void crearPersona(String nombre, String apellido, String dni, String email) {
        try {
            Ciudadano ciud = new Ciudadano(nombre, apellido, dni, email);
            controlPersis.crearCiudadano(ciud);
        } catch (Exception e) {
            System.out.println("Error al crear ciudadano: " + e.getMessage());
        }
    }

    public Ciudadano buscarCiudadano(Integer idCiudadano) {
        return controlPersis.buscarCiudadano(idCiudadano);
    }

    public List<Ciudadano> traerPersonas() {
        return controlPersis.traerCiudadanos();
    }

    public void eliminarCiudadano(Integer id) throws NonexistentEntityException {
        controlPersis.eliminarCiudadano(id);
    }

    public void modificarCiudadano(Ciudadano ciudadano) {
        controlPersis.modificarCiudadano(ciudadano);
    }

    public Ciudadano buscarPorDni(String dni) {
        return controlPersis.buscarPorDni(dni);
    }

    public List<Ciudadano> traerCiudadanosPorApellido(String apellido) {
        return controlPersis.traerCiudadanos().stream()
                .filter(ciudadano -> ciudadano.getApellido().equalsIgnoreCase(apellido))
                .collect(Collectors.toList());
    }
    public List<Ciudadano> traerCiudadanos() {
        return controlPersis.traerCiudadanos();
    }
    

    // CRUD de Trámite
    public void crearTramite(String nombre, String descripcion) {
        Tramite tramite = new Tramite(nombre, descripcion);
        controlPersis.crearTramite(tramite);
    }

    public Tramite buscarTramite(Long id) {
        return controlPersis.buscarTramite(id);
    }

    public List<Tramite> traerTramites() {
        return controlPersis.traerTramites();
    }

    public void eliminarTramite(Long id) throws NonexistentEntityException {
        controlPersis.eliminarTramite(id);
        
    }

    public void modificarTramite(Tramite tramite) {
        controlPersis.modificarTramite(tramite);
    }
    // CRUD de Usuario

    public void crearUsuario(String nombre, String contraseña, String rol) {
        Usuario usuario = new Usuario(nombre, contraseña, rol);
        controlPersis.crearUsuario(usuario);
    }

    public Usuario buscarUsuario(Long nombre) {
        return controlPersis.buscarUsuario(nombre);
    }

    public List<Usuario> traerUsuarios() {
        return controlPersis.traerUsuarios();
    }

    public void eliminarUsuario(long id) throws NonexistentEntityException {
        controlPersis.eliminarUsuario(id);
    }

    public void modificarUsuario(Usuario usuario) {
        controlPersis.modificarUsuario(usuario);
    }
    //CRUD de TURNO


    // CRUD de TURNO

    public void crearTurno(Date fecha, String estado, String descripcion, Integer numeroTurno, Ciudadano ciudadano, Tramite tramite) {
        if (fecha == null || estado == null || descripcion == null || numeroTurno == null || ciudadano == null || tramite == null) {
            throw new IllegalArgumentException("Todos los parámetros son obligatorios.");
        }

        try {
            Turno turno = new Turno(fecha, estado, ciudadano, tramite, descripcion, numeroTurno);
            controlPersis.crearTurno(turno);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el turno: " + e.getMessage(), e);
        }
    }

    public Turno buscarTurno(long id) {
        Turno turno = controlPersis.buscarTurno(id);
        if (turno == null) {
            throw new TurnoNoEncontradoException("Turno con ID " + id + " no encontrado.");
        }
        return turno;
    }

    public List<Turno> traerTurnos() {
        return controlPersis.traerTurnos();
    }

    public List<Turno> traerTurnosPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new EstadoInvalidoException("El estado no puede estar vacío.");
        }

        return controlPersis.traerTurnos().stream()
                .filter(turno -> turno.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    public void eliminarTurno(long id) throws NonexistentEntityException {
        controlPersis.eliminarTurno(id);
    }

    public void modificarTurno(Turno turno) {
        if (turno == null) {
            throw new IllegalArgumentException("El turno no puede ser nulo.");
        }
        controlPersis.modificarTurno(turno);
    }
    public class TurnoNoEncontradoException extends RuntimeException {
    public TurnoNoEncontradoException(String message) {
        super(message);
    }
}

public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException(String message) {
        super(message);
    }
}
}

