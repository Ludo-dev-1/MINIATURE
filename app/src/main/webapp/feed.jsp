<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
        <%@ page import="org.example.domain.entity.Post" %>
            <%@ page import="org.example.domain.entity.User" %>


                <!DOCTYPE html>
                <html>

                <head>
                    <title>Home - MINIATURE</title>
                    <link rel="stylesheet" type="text/css" href="./style/feed.css?v=2">
                </head>

                <body>
                    <h1>Bienvenue sur MINIATURE</h1>

                    <h2><a href="/feedSubscriptions" class="subscription-link">Page d'abonnements</a></h2>
                    <a href="<%= request.getContextPath() %>/logout" class="logout-link">Se déconnecter</a>

                    <form id="newPostForm" method="post" action="/post">
                        <input type="text" name="content" placeholder="Quoi de neuf ?">
                        <button name="newPost" type="submit">&#9998; Nouveau post</button>
                    </form>
                    <% List<Post> posts = (List<Post>) request.getAttribute("posts");

                            if (posts != null) {
                            for (Post post : posts) {
                            %>

                            <article class="post">
                                <form class="follow-form" method="post" action="follow">
                                    <input type="hidden" name="userId" value="<%= post.getUserId() %>">
                                    <button type="submit" class="btn-follow <%= post.isFollowing() ? " following" : ""
                                        %>">
                                        <%= post.isFollowing() ? "✓ Suivi" : "Suivre" %>
                                    </button>
                                </form>
                                <p><small>Posté par <%= post.getAuthorName() %> le <%= post.getCreatedAt().toLocalDate()
                                                %></small></p>
                                <p>
                                    <%= post.getContent() %>
                                </p>

                                <form class="like-form" method="post" action="like">
                                    <input type="hidden" name="postId" value="<%= post.getId() %>">
                                    <button type="submit" class="like <%= post.isLiked() ? " liked" : "" %>">
                                        <%= post.isLiked() ? "❤️ Liké" : "🤍 J'aime" %>
                                    </button>
                                </form>
                                <a href="<%= request.getContextPath() %>/comments?postId=<%= post.getId() %>">&#128172;
                                    Commentaire</a>
                            </article>

                            <% } } else { %>
                                <p>Aucun post à afficher pour le moment.</p>
                                <% } %>


                </body>

                </html>

// Ce fichier JSP (feed.jsp) est utilisé pour afficher le fil d'actualité principal de l'application MINIATURE.
// Il récupère la liste des posts à partir des attributs de la requête et les affiche de manière structurée, en incluant des fonctionnalités pour suivre les auteurs des posts, liker les posts, et accéder aux commentaires associés à chaque post.
// Le fichier inclut également un formulaire pour permettre aux utilisateurs de créer de nouveaux posts.    
// En résumé, ce fichier est essentiel pour fournir une interface utilisateur permettant de visualiser le fil d'actualité, d'interagir avec les posts, et de créer de nouveaux contenus dans l'application MINIATURE.   
