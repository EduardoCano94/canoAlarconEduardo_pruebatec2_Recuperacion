package com.hackaboss.persistencia;

import com.hackaboss.logica.Ciudadano;
import com.hackaboss.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CiudadanoJpaController implements Serializable {

    public CiudadanoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public CiudadanoJpaController() {
        emf = Persistence.createEntityManagerFactory("turneroPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudadano ciudadano) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ciudadano);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Ciudadano buscarPorDni(String dni) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Ciudadano> query = em.createQuery("SELECT c FROM Ciudadano c WHERE c.dni = :dni", Ciudadano.class);
            query.setParameter("dni", dni);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Ciudadano> findCiudadanoEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Ciudadano c", Ciudadano.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Ciudadano findCiudadano(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudadano.class, id);
        } finally {
            em.close();
        }
    }

    public void edit(Ciudadano ciudadano) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ciudadano = em.merge(ciudadano);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Ciudadano ciudadano = em.find(Ciudadano.class, id);
            if (ciudadano == null) {
                throw new NonexistentEntityException("El ciudadano con ID " + id + " no existe.");
            }
            em.remove(ciudadano);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
