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

import com.entity.User;

@WebServlet("/connect")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Retrieve form data
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		resp.getWriter().print(username);
        resp.getWriter().print(email);
       resp.getWriter().print(password);



		// Input validation
		if (isInvalidInput(username, email, password)) {
			resp.sendRedirect("signup.jsp?error=invalid");
			return;
		}

		// Create a User object
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);

		// Perform database operations
		EntityManagerFactory factory = null;
		EntityManager manager = null;
		EntityTransaction transaction = null;

		try {
			factory = Persistence.createEntityManagerFactory("E1");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			transaction.begin();

			// Check if the user already exists
			if (userExists(manager, username)) {
				resp.sendRedirect("signup.jsp?error=exists");
			} else {
				// Save the user to the database
				manager.persist(user);
				transaction.commit();
				resp.sendRedirect("login.jsp?message=registered");
			}
		} catch (Exception e) {
			handleTransactionError(transaction);
			e.printStackTrace();
			resp.sendRedirect("signup.jsp?error=exception");
		} finally {
			closeResources(manager, factory);
		}
	}

	// Validates user input
	private boolean isInvalidInput(String username, String email, String password) {
		return username == null || username.isEmpty() || email == null || email.isEmpty() || password == null
				|| password.isEmpty();
	}

	// Checks if the user already exists in the database
	private boolean userExists(EntityManager manager, String username) {
		return manager.find(User.class, username) != null;
	}

	// Handles transaction rollback in case of an error
	private void handleTransactionError(EntityTransaction transaction) {
		if (transaction != null && transaction.isActive()) {
			transaction.rollback();
		}
	}

	// Closes JPA resources
	private void closeResources(EntityManager manager, EntityManagerFactory factory) {
		if (manager != null) {
			manager.close();
		}
		if (factory != null) {
			factory.close();
		}
	}
}
