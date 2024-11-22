package com.servlet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.entity.User;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=invalid");
            return;
        }

        EntityManagerFactory factory = null;
        EntityManager manager = null;

        try {
            factory = Persistence.createEntityManagerFactory("E1");
            manager = factory.createEntityManager();

            User user = manager.find(User.class, username);

            if (user == null || !user.getPassword().equals(password)) {
                response.sendRedirect("login.jsp?error=invalid");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("search.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=exception");
        } finally {
            if (manager != null) manager.close();
            if (factory != null) factory.close();
        }
    }
}
