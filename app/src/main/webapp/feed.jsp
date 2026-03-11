<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.models.Post" %>


<!DOCTYPE html>
<html>
<head>  
    <title>Home - MINIATURE</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>  
    <h1>Bienvenue sur MINIATURE</h1>
    <p>Voici votre flux d'actualités personnalisé.</p>
  <%
List<Post> posts = (List<Post>) request.getAttribute("posts");

if (posts != null) {
    for (Post post : posts) {
%>

<div class="post">
    <p><%= post.getContent() %></p>
    <p><small>Posté le <%= post.getCreatedAt() %></small></p>
</div>

<%
    } 
 } else { %>
<p>Aucun post à afficher pour le moment.</p>
<% } %>


</body>
</html>