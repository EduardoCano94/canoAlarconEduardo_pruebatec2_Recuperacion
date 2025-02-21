
package com.hackaboss.servlets;

import com.hackaboss.logica.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crear una unidad de persistencia
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("turneroPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Crear un usuario de prueba
            em.getTransaction().begin();
            Usuario usuario = new Usuario();
            usuario.setUsername("admin");
            usuario.setPassword("admin123");
            usuario.setRol("ADMIN");
            em.persist(usuario);
            em.getTransaction().commit();

            // Responder al cliente
            response.getWriter().println("¡Usuario creado con éxito!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
}