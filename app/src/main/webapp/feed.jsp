<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="org.example.models.User" %>
        <%@ page import="java.util.List" %>
            <%@ page import="org.example.models.Post" %>


                <!DOCTYPE html>
                <html>

                <head>
                    <title>Home - MINIATURE</title>
                    <link rel="stylesheet" type="text/css" href="./style/feed.css">
                </head>

                <body>
                    <h1>Bienvenue sur MINIATURE</h1>
                    <p>Voici votre flux d'actualités personnalisé.</p>
                    <form method="post">
                        <input type="text" name="content" placeholder="Quoi de neuf ?">
                        <button name="newPost" type="submit">&#9998; Nouveau post</button>
                    </form>
                    <% List<Post> posts = (List<Post>) request.getAttribute("posts");

                            if (posts != null) {
                            for (Post post : posts) {
                            %>

                            <article class="post">
                                <form method="post" action="follow">
                                    <input type="hidden" name="userId" value="<%= post.getUserId() %>">
                                    <button type="submit">
                                        <%= post.isFollowing() ? "✓ Suivi" : "Suivre" %>
                                    </button>
                                </form>
                                <p><small>Posté par <%= post.getAuthorName() %> le <%= post.getCreatedAt().toLocalDate()
                                                %></small></p>
                                <p>
                                    <%= post.getContent() %>
                                </p>

                                <button name="like">&#129293; Like </button>
                                <button name="comment">&#128172; Comment</button>
                            </article>

                            <% } } else { %>
                                <p>Aucun post à afficher pour le moment.</p>
                                <% } %>


                </body>

                </html>