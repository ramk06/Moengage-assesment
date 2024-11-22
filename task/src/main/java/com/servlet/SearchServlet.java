package com.servlet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        String filter = request.getParameter("filter");
        List<String> images = new ArrayList<>();

        if (filter != null && !filter.trim().isEmpty()) {
            if (!filter.matches("\\d{1,3}(x{0,2})")) {
                response.sendRedirect("search.jsp?error=invalid");
                return;
            }

            List<String> responseCodes = generateResponseCodes(filter);
            images = fetchImageUrls(responseCodes);
        }

        request.setAttribute("images", images);
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    private List<String> generateResponseCodes(String filter) {
        List<String> codes = new ArrayList<>();
        if (filter.endsWith("xx")) {
            char prefix = filter.charAt(0);
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    codes.add(prefix + "" + i + j);
                }
            }
        } else if (filter.endsWith("x")) {
            char prefix1 = filter.charAt(0);
            char prefix2 = filter.charAt(1);
            for (int i = 0; i <= 9; i++) {
                codes.add(prefix1 + "" + prefix2 + i);
            }
        } else {
            codes.add(filter);
        }
        return codes;
    }

    private List<String> fetchImageUrls(List<String> responseCodes) {
        List<String> images = new ArrayList<>();
        for (String code : responseCodes) {
            images.add("https://http.dog/" + code + ".jpg");
        }
        return images;
    }
}
