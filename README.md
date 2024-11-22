# Response Code Management System

This is a Java-based web application that allows users to search HTTP response codes, view images related to them, and manage saved lists of codes. Users can also edit and delete their saved lists.

## Features

- **Search Response Codes**: Search for HTTP response codes and view images related to the code.
- **Save Lists**: Save searched response codes to personalized lists.
- **View Lists**: View all saved lists.
- **Edit Lists**: Update the name of a saved list.
- **Delete Lists**: Remove a saved list from the database.
- **User Authentication**: Session-based user management with login/logout functionality.

## Technology Stack

- **Backend**: Java Servlets, Hibernate (JPA)
- **Frontend**: JSP, HTML, CSS
- **Database**: MySQL (or other supported relational databases)
- **Web Server**: Apache Tomcat

## Project Structure

```plaintext
src/
│
├── com.entity/
│   └── List.java                  # Entity class for saved lists
│
├── com.servlet/
│   ├── SearchServlet.java         # Handles search functionality
│   ├── SaveListServlet.java       # Handles saving lists
│   ├── ListPageServlet.java       # Handles displaying all lists
│   ├── EditListServlet.java       # Handles editing lists
│   ├── DeleteListServlet.java     # Handles deleting lists
│   └── LoginServlet.java          # Handles user authentication
│
├── webapp/
│   ├── index.jsp                  # Homepage
│   ├── search.jsp                 # Search response codes
│   ├── lists.jsp                  # View saved lists
│   ├── editlist.jsp               # Edit a list
│   ├── login.jsp                  # User login page
│   └── signup.jsp                 # User signup page
│
└── resources/
    └── META-INF/persistence.xml   # JPA configuration
