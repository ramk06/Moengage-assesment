<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            padding: 20px;
            margin: 50px auto;
            width: 80%;
            max-width: 600px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        form {
            display: flex;
            flex-direction: column;
        }
        form input {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        form button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit List</h2>
        <form action="editList" method="post">
            <% 
                com.entity.List list = (com.entity.List) request.getAttribute("list");
                if (list != null) { 
            %>
                <input type="hidden" name="listId" value="<%= list.getId() %>">
                <label for="name">List Name</label>
                <input type="text" id="name" name="name" value="<%= list.getName() %>" required>
                <button type="submit">Save Changes</button>
            <% 
                } else { 
            %>
                <p>List not found.</p>
            <% } %>
        </form>
    </div>
</body>
</html>
