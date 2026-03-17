<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.servlet.jsp.jstl.core" %>

<%
    Object postObj = request.getAttribute("post");
    Object commentsObj = request.getAttribute("comments");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Détail du Post</title>
    <link rel="stylesheet" type="text/css" href="./style/feed.css">
</head>
<body>
    <h1>Détail du Post</h1>

    <c:if test="${not empty post}">
        <!-- Affichage du détail du post -->
        <div id="post-detail">
            <h2>${post.content}</h2>
            <p><em>Posté par ${post.authorName} le ${post.createdAt}</em></p>
        </div>

        <!-- Affichage des commentaires -->
        <div id="comments">
            <h3>Commentaires</h3>
            <c:if test="${not empty comments}">
                <c:forEach var="comment" items="${comments}">
                    <div class="comment">
                        <strong>${comment.userId}</strong> : ${comment.content}<br>
                        <span><em>${comment.createdAt}</em></span>
                    </div>
                    <hr>
                </c:forEach>
            </c:if>
            <c:if test="${empty comments}">
                <p>Aucun commentaire pour le moment.</p>
            </c:if>
        </div>

        <!-- Formulaire pour créer un nouveau commentaire -->
        <div id="new-comment">
            <h3>Ajouter un commentaire</h3>
            <form action="comments" method="post">
                <input type="hidden" name="postId" value="${post.id}" />
                <label for="author">Nom :</label>
                <input type="text" id="author" name="author" required /><br>
                <label for="content">Commentaire :</label><br>
                <textarea id="content" name="content" rows="4" cols="50" required></textarea><br>
                <button type="submit">Envoyer</button>
            </form>
        </div>
    </c:if>

    <c:if test="${empty post}">
        <p>Post introuvable.</p>
    </c:if>
</body>
</html>