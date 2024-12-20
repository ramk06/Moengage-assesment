package com.servlet;

import java.io.IOException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/editList")
public class EditListServlet extends HttpServlet {
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

        try {
            com.entity.List list = manager.find(com.entity.List.class, Integer.parseInt(listId));
            if (list != null && list.getUsername().equals(username)) {
                request.setAttribute("list", list);
                request.getRequestDispatcher("editlist.jsp").forward(request, response);
            } else {
                response.sendRedirect("lists.jsp?error=notfound");
            }
        } finally {
            manager.close();
            factory.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String listId = request.getParameter("listId");
        String name = request.getParameter("name");

        if (username == null || listId == null || name == null) {
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
                list.setName(name);
                manager.merge(list);
                transaction.commit();
                response.sendRedirect("lists.jsp?success=updated");
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
