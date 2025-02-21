package com.hackaboss.persistencia;

import com.hackaboss.logica.Ciudadano;
import com.hackaboss.logica.Tramite;
import com.hackaboss.logica.Turno;
import com.hackaboss.logica.Usuario;
import com.hackaboss.persistencia.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {

    CiudadanoJpaController ciudadanoJpa = new CiudadanoJpaController();
    UsuarioJpaController usuarioJpa = new UsuarioJpaController();
    TramiteJpaController tramiteJpa = new TramiteJpaController();
    TurnoJpaController turnoJpa = new TurnoJpaController();

    // Métodos para Ciudadano
    public void crearCiudadano(Ciudadano ciudadano) {
        ciudadanoJpa.create(ciudadano);
    }

    public Ciudadano buscarCiudadano(Integer id) {
        return ciudadanoJpa.findCiudadano(id);
    }

    public List<Ciudadano> traerCiudadanos() {
        return ciudadanoJpa.findCiudadanoEntities();
    }

    public void modificarCiudadano(Ciudadano ciudadano) {
        try {
            ciudadanoJpa.edit(ciudadano);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarCiudadano(Integer id) throws NonexistentEntityException {
        ciudadanoJpa.destroy(id);
    }

    public Ciudadano buscarPorDni(String dni) {
        try {
            return ciudadanoJpa.buscarPorDni(dni);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Métodos para Usuario
    public void crearUsuario(Usuario usuario) {
        usuarioJpa.create(usuario);
    }

    public Usuario buscarUsuario(Long nombre) {
        return usuarioJpa.findUsuario(nombre);
    }

    public List<Usuario> traerUsuarios() {
        return usuarioJpa.findUsuarioEntities();
    }

    public void modificarUsuario(Usuario usuario) {
        try {
            usuarioJpa.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarUsuario(long id) throws NonexistentEntityException {
        usuarioJpa.destroy(id);
    }

    // Métodos para Trámite
    public void crearTramite(Tramite tramite) {
        tramiteJpa.create(tramite);
    }

    public Tramite buscarTramite(Long id) {
        return tramiteJpa.findTramite(id);
    }

    public List<Tramite> traerTramites() {
        return tramiteJpa.findTramiteEntities();
    }

    public void modificarTramite(Tramite tramite) {
        try {
            tramiteJpa.edit(tramite);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarTramite(Long id) throws NonexistentEntityException {
        tramiteJpa.destroy(id);
    }

    // Métodos para Turno
    public void crearTurno(Turno turno) {
        turnoJpa.create(turno);
    }

    public Turno buscarTurno(Long id) {
        return turnoJpa.findTurno(id);
    }

    public List<Turno> traerTurnos() {
        List<Turno> turnos = turnoJpa.findTurnoEntities();
    
    System.out.println("Turnos encontrados: " + turnos.size());
    for (Turno t : turnos) {
        System.out.println("ID: " + t.getIdTurno() + ", Estado: " + t.getEstado());
    }

    return turnos;
    }

    public List<Turno> traerTurnosPorFecha(Date fecha) {
        return this.traerTurnos().stream()
                .filter(turno -> turno.getFecha().equals(fecha))
                .toList();
    }

    public List<Turno> traerTurnosPorEstado(String estado) {
        return this.traerTurnos().stream()
                .filter(turno -> turno.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    public List<Turno> traerTurnosPorFechaYEstado(Date fecha, String estado) {
        return this.traerTurnos().stream()
                .filter(turno -> turno.getFecha().equals(fecha)
                && turno.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    public void modificarTurno(Turno turno) {
        try {
            turnoJpa.edit(turno);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarTurno(Long id) throws NonexistentEntityException {
        turnoJpa.destroy(id);
    }

}
