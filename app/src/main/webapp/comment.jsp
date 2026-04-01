<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="org.example.domain.entity.Post" %>
<%@ page import="org.example.domain.entity.Comment" %>

<%
    Post post = (Post) request.getAttribute("post");
    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détail du Post</title>
    <link rel="stylesheet" type="text/css" href="./style/comment.css">
</head>
<body>
    <h1>Détail du Post</h1>

<% if (post != null) { %>
    <!-- Affichage du détail du post -->
    <div id="post-detail">
        <h2><%= post.getContent() %></h2>
        <p><em>Posté par <%= post.getAuthorName() %> le <%= post.getCreatedAt() %></em></p>
    </div>

    <div class="back-nav">
        <h2><a href="<%= request.getContextPath() %>/feed" class="back-link"> Retour au fil d'actualité</a></h2>
    </div>

    <!-- Affichage des commentaires -->
    <div id="comments">
        <h3>Commentaires</h3>
    <% if (comments != null && !comments.isEmpty()) { 
            for (Comment comment : comments) { %>
                <div class="comment">
                    <strong><%= comment.getAuthorName() %></strong> <%= comment.getContent() %><br>
                    <span><em><%= comment.getCreatedAt() != null ? comment.getCreatedAt().format(dateFormatter) : "Date indisponible" %></em></span>
                </div>
                <hr>
    <%      }
       } else { %>
        <p>Aucun commentaire pour le moment.</p>
    <% } %>
    </div>

    <!-- Formulaire pour créer un nouveau commentaire -->
    <div id="new-comment">
        <h3>Ajouter un commentaire</h3>
        <form action="<%= request.getContextPath() %>/comments" method="post">
            <input type="hidden" name="postId" value="<%= post.getId() %>">
            <label for="content">Commentaire :</label>
            <textarea id="content" name="content" rows="4" cols="50" required></textarea><br>
            <button type="submit">Envoyer</button>
        </form>
    </div>
<% } else { %>
    <p>Post introuvable.</p>
<% } %>

</body>
</html>

//  Ce fichier JSP (comment.jsp) est utilisé pour afficher les détails d'un post spécifique, ainsi que les commentaires associés à ce post. 
// Il récupère le post et les commentaires à partir des attributs de la requête, puis les affiche de manière structurée. 
// Le fichier inclut également un formulaire pour permettre aux utilisateurs d'ajouter de nouveaux commentaires au post. 
// En résumé, ce fichier est essentiel pour fournir une interface utilisateur permettant de visualiser les détails d'un post et d'interagir avec les commentaires associés.           
