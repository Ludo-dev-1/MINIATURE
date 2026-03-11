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
    <form method="post">
        <input type="text" name="content" placeholder="Quoi de neuf ?">
        <button name="newPost" type="submit">&#9998; Nouveau post</button>
    </form>
  <%
List<Post> posts = (List<Post>) request.getAttribute("posts");

if (posts != null) {
    for (Post post : posts) {
%>

<div class="post">
    <p><%= post.getContent() %></p>
    <p><small>Posté le <%= post.getCreatedAt().toString() %></small></p>
    <button name="like">&#129293; Like </button>
    <button name="comment">&#128172; Comment</button>
</div>

<%
    } 
 } else { %>
<p>Aucun post à afficher pour le moment.</p>
<% } %>


</body>
</html>