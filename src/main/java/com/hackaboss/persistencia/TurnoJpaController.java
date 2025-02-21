package com.hackaboss.persistencia;

import com.hackaboss.logica.Turno;
import com.hackaboss.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class TurnoJpaController implements Serializable {

    private static final Logger logger = Logger.getLogger(TurnoJpaController.class.getName());

    private EntityManagerFactory emf;

    // Constructor que permite inyectar un EntityManagerFactory
    public TurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Constructor por defecto que crea el EntityManagerFactory
    public TurnoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("turneroPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) {
        if (turno == null) {
            throw new IllegalArgumentException("El turno no puede ser nulo.");
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(turno);
            em.getTransaction().commit();
            logger.info("Turno creado con éxito: " + turno.getIdTurno());
        } catch (Exception e) {
            logger.severe("Error al crear el turno: " + e.getMessage());
            throw new RuntimeException("Error al crear el turno.", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnoEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Turno t", Turno.class)
                     .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Turno findTurno(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del turno no puede ser nulo.");
        }
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws Exception {
        if (turno == null) {
            throw new IllegalArgumentException("El turno no puede ser nulo.");
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            turno = em.merge(turno);
            em.getTransaction().commit();
            logger.info("Turno modificado con éxito: " + turno.getIdTurno());
        } catch (Exception e) {
            logger.severe("Error al modificar el turno: " + e.getMessage());
            throw new RuntimeException("Error al modificar el turno.", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        if (id == null) {
            throw new IllegalArgumentException("El ID del turno no puede ser nulo.");
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Turno turno = em.find(Turno.class, id);
            if (turno == null) {
                throw new NonexistentEntityException("El turno con ID " + id + " no existe.");
            }
            em.remove(turno);
            em.getTransaction().commit();
            logger.info("Turno eliminado con éxito: " + id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // Métodos adicionales para consultas específicas

    public List<Turno> findTurnosByFecha(Date fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Turno> query = em.createQuery(
                "SELECT t FROM Turno t WHERE t.fecha = :fecha", Turno.class);
            query.setParameter("fecha", fecha);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnosByEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Turno> query = em.createQuery(
                "SELECT t FROM Turno t WHERE t.estado = :estado", Turno.class);
            query.setParameter("estado", estado);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnosByFechaAndEstado(Date fecha, String estado) {
        if (fecha == null || estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha y el estado no pueden ser nulos.");
        }
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Turno> query = em.createQuery(
                "SELECT t FROM Turno t WHERE t.fecha = :fecha AND t.estado = :estado", Turno.class);
            query.setParameter("fecha", fecha);
            query.setParameter("estado", estado);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}