<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .header {
            background-color: #007bff;
            color: white;
            padding: 15px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            margin: 0;
        }
        .header .welcome {
            margin-left: 20px;
        }
        .logout button {
            padding: 8px 12px;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .logout button:hover {
            background-color: #c82333;
        }
        .container {
            padding: 20px;
            margin: 0 auto;
            width: 80%;
            max-width: 900px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .filter-form {
            margin-bottom: 20px;
            text-align: center;
        }
        .filter-form input {
            padding: 10px;
            width: 70%;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .filter-form button {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .filter-form button:hover {
            background-color: #218838;
        }
        .images-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
            margin-bottom: 20px;
        }
        .images-container img {
            width: 150px;
            height: 150px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .message {
            padding: 10px;
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
        .error-message {
            padding: 10px;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
        .view-lists-button {
            padding: 10px 20px;
            background-color: #ffc107;
            color: black;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            display: block;
            margin: 20px auto;
            text-align: center;
        }
        .view-lists-button:hover {
            background-color: #e0a800;
        }
    </style>
    <script>
        // Validate search form
        function validateSearchForm() {
            const filterInput = document.querySelector('input[name="filter"]');
            if (!filterInput.value.trim()) {
                alert('Please enter a valid response code.');
                return false;
            }
            return true;
        }

        // Logout function
        function logout() {
            window.location.href = "logout";
        }
    </script>
</head>
<body>
    <div class="header">
        <h1>MoEngage Assignment </h1>
        <div class="welcome">
            Welcome, <%= session.getAttribute("username") != null ? session.getAttribute("username") : "Guest" %>
        </div>
        <div class="logout">
            <button onclick="logout()">Logout</button>
        </div>
    </div>
    <div class="container">
        <!-- Success/Error Messages -->
        <% if (request.getParameter("error") != null) { %>
            <div class="error-message">
                <% if ("invalid".equals(request.getParameter("error"))) { %>
                    Invalid input! Please try again.
                <% } else { %>
                    An error occurred. Please try again.
                <% } %>
            </div>
        <% } else if (request.getParameter("success") != null) { %>
            <div class="message">
                Item added successfully to the list!
            </div>
        <% } %>

        <h2>Search by Response Code</h2>
        <form action="search" method="get" class="filter-form" onsubmit="return validateSearchForm();">
            <input type="text" name="filter" placeholder="Enter code (e.g., 2xx, 200)" required>
            <button type="submit">Search</button>
        </form>

        <!-- View All Lists Button -->
        <form action="lists" method="get">
            <button type="submit" class="view-lists-button">View All Lists</button>
        </form>

        <!-- Display Images -->
        <div class="images-container">
            <% 
            java.util.List<String> images = (java.util.List<String>) request.getAttribute("images");
            if (images != null && !images.isEmpty()) {
                for (String imgUrl : images) {
            %>
                <img src="<%= imgUrl %>" alt="Response Code Image">
            <% 
                }
            } else if (request.getParameter("filter") != null) { 
            %>
                <p>No images found for the given filter.</p>
            <% } %>
        </div>

        <!-- Save List Button -->
        <form action="saveList" method="post">
            <input type="hidden" name="filter" value="<%= request.getParameter("filter") %>">
            <button type="submit" class="save-button">Save List</button>
        </form>
    </div>
</body>
</html>
