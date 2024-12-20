package com.servlet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entity.List;
import java.io.IOException;
import java.util.Date;

@WebServlet("/saveList")
public class SaveListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String filter = request.getParameter("filter");

        // Validate session and input
        if (username == null) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }
        if (filter == null || filter.trim().isEmpty() || !isValidFilter(filter)) {
            response.sendRedirect("search.jsp?error=invalid");
            return;
        }

        EntityManagerFactory factory = null;
        EntityManager manager = null;
        EntityTransaction transaction = null;

        try {
            factory = Persistence.createEntityManagerFactory("E1");
            manager = factory.createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();

            List list = new List();
            list.setName(filter.trim());
            list.setCreationDate(new Date());
            list.setUsername(username);
            manager.persist(list);

            transaction.commit();
            response.sendRedirect("search.jsp?success=added");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            response.sendRedirect("search.jsp?error=exception");
        } finally {
            if (manager != null) manager.close();
            if (factory != null) factory.close();
        }
    }

    private boolean isValidFilter(String filter) {
        // Validate if the filter matches patterns like "2xx", "200", etc.
        return filter.matches("\\d{1,3}(x{0,2})");
    }
}
