<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Lists</title>
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
            text-align: center;
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
        ul {
            list-style-type: none;
            padding: 0;
        }
        ul li {
            margin: 10px 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .action-links a {
            margin-left: 10px;
            color: white;
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 5px;
        }
        .edit-link {
            background-color: #28a745;
        }
        .delete-link {
            background-color: #dc3545;
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
    </style>
</head>
<body>
    <div class="header">
        <h1>My Lists</h1>
        <a href="logout" style="color: white;">Logout</a>
    </div>
    <div class="container">
        <!-- Success Message -->
        <% if (request.getParameter("success") != null) { %>
            <div class="message">
                <p>Edit successful!</p>
                <a href="search.jsp">Go to Search Page</a>
            </div>
        <% } %>

        <h2>Your Saved Lists</h2>
        <ul>
            <% 
                java.util.List<com.entity.List> lists = (java.util.List<com.entity.List>) request.getAttribute("lists");
                if (lists != null && !lists.isEmpty()) {
                    for (com.entity.List list : lists) { 
            %>
                        <li>
                            <%= list.getName() %>
                            <div class="action-links">
                                <a href="editList?listId=<%= list.getId() %>" class="edit-link">Edit</a>
                                <a href="deleteList?listId=<%= list.getId() %>" class="delete-link">Delete</a>
                            </div>
                        </li>
            <% 
                    } 
                } else { 
            %>
                <p>No saved lists found.</p>
            <% } %>
        </ul>
    </div>
</body>
</html>
