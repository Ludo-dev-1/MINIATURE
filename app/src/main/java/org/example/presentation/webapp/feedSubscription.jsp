<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.domain.entity.Post" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>  
    <title>Home - MINIATURE</title>
    <link rel="stylesheet" type="text/css" href="./style/feed.css">
</head>
<body>  

<h1>Bienvenue sur MINIATURE</h1>

<h2><a href="<%= request.getContextPath() %>/feed" class="subscription-link">Retourner au feed</a></h2>

<p>Voici les posts des utilisateurs que vous suivez.</p>

<%
List<Post> posts = (List<Post>) request.getAttribute("posts");

if (posts != null && !posts.isEmpty()) {
    for (Post post : posts) {
%>
<article class="post">
    <form class="follow-form" method="post" action="follow">
        <input type="hidden" name="userId" value="<%= post.getUserId() %>">
        <button type="submit" class="btn-follow <%= post.isFollowing() ? "following" : "" %>">
            <%= post.isFollowing() ? "✓ Suivi" : "Suivre" %>
        </button>
    </form>

    <p><small>Posté par <%= post.getAuthorName() %> le <%= post.getCreatedAt().toLocalDate() %></small></p>
    <p><%= post.getContent() %></p>

    <form class="like-form" method="post" action="like">
        <input type="hidden" name="postId" value="<%= post.getId() %>">
        <button type="submit" class="like <%= post.isLiked() ? "liked" : "" %>">
            <%= post.isLiked() ? "❤️ Liké" : "🤍 J'aime" %>
        </button>
    </form>
    <a href="<%= request.getContextPath() %>/comments?postId=<%= post.getId() %>">&#128172; Commentaire</a>
</article>
<%
    }
} else {
%>
<p>Aucun post des abonnements pour le moment.</p>
<%
}
%>

</body>
</html>
