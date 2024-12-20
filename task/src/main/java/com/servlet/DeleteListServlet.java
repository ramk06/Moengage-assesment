package com.servlet;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteList")
public class DeleteListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String listId = request.getParameter("listId");

        if (username == null || listId == null) {
            response.sendRedirect("lists.jsp?error=invalid");
            return;
        }

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("E1");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            com.entity.List list = manager.find(com.entity.List.class, Integer.parseInt(listId));
            if (list != null && list.getUsername().equals(username)) {
                manager.remove(list);
                transaction.commit();
                response.sendRedirect("lists.jsp?success=deleted");
            } else {
                response.sendRedirect("lists.jsp?error=notfound");
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            response.sendRedirect("lists.jsp?error=exception");
        } finally {
            manager.close();
            factory.close();
        }
    }
}


