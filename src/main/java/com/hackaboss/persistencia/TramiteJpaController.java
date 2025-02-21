package com.hackaboss.persistencia;

import com.hackaboss.logica.Tramite;
import com.hackaboss.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class TramiteJpaController implements Serializable {

    public TramiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TramiteJpaController() {
        emf = Persistence.createEntityManagerFactory("turneroPU");
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("turneroPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tramite tramite) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tramite);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Tramite> findTramiteEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tramite t", Tramite.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Tramite findTramite(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tramite.class, id);
        } finally {
            em.close();
        }
    }

    public void edit(Tramite tramite) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            tramite = em.merge(tramite);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tramite tramite = em.find(Tramite.class, id);
            if (tramite == null) {
                throw new NonexistentEntityException("El trámite con ID " + id + " no existe.");
            }
            em.remove(tramite);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
