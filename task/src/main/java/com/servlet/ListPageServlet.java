package com.servlet;

import com.entity.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/lists")
public class ListPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String PERSISTENCE_UNIT_NAME = "E1";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");

        // Validate session
        if (username == null) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        String action = request.getParameter("action");
        String listId = request.getParameter("listId");

        if ("delete".equalsIgnoreCase(action) && listId != null) {
            handleDelete(request, response, listId, username);
        } else if ("edit".equalsIgnoreCase(action) && listId != null) {
            handleEdit(request, response, listId, username);
        } else {
            // Default behavior: display the list
            displayLists(request, response, username);
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, String listId, String username) throws IOException {
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        EntityTransaction transaction = null;

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            manager = factory.createEntityManager();
            transaction = manager.getTransaction();

            transaction.begin();
            List list = manager.find(List.class, Integer.parseInt(listId));

            if (list != null && username.equals(list.getUsername())) {
                manager.remove(list);
                transaction.commit();
                response.sendRedirect("lists.jsp?success=deleted");
            } else {
                response.sendRedirect("lists.jsp?error=unauthorized");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            response.sendRedirect("lists.jsp?error=exception");
        } finally {
            if (manager != null) manager.close();
            if (factory != null) factory.close();
        }
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response, String listId, String username) throws IOException {
        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            manager = factory.createEntityManager();

            List list = manager.find(List.class, Integer.parseInt(listId));
            if (list != null && username.equals(list.getUsername())) {
                request.setAttribute("list", list);
                request.getRequestDispatcher("editlist.jsp").forward(request, response);
            } else {
                response.sendRedirect("lists.jsp?error=unauthorized");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("lists.jsp?error=exception");
        } finally {
            if (manager != null) manager.close();
            if (factory != null) factory.close();
        }
    }

    private void displayLists(HttpServletRequest request, HttpServletResponse response, String username) throws ServletException, IOException {
        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            manager = factory.createEntityManager();

            java.util.List<List> lists = manager.createQuery(
                    "SELECT l FROM List l WHERE l.username = :username", List.class)
                    .setParameter("username", username)
                    .getResultList();

            request.setAttribute("lists", lists);
            request.getRequestDispatcher("lists.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("lists", new ArrayList<>());
            request.getRequestDispatcher("lists.jsp").forward(request, response);
        } finally {
            if (manager != null) manager.close();
            if (factory != null) factory.close();
        }
    }
}
