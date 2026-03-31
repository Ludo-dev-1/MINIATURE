<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
        <%@ page import="org.example.domain.model.Post" %>
            <%@ page import="org.example.domain.model.User" %>


                <!DOCTYPE html>
                <html>

                <head>
                    <title>Home - MINIATURE</title>
                    <link rel="stylesheet" type="text/css" href="./style/feed.css">
                </head>

                <body>
                    <h1>Bienvenue sur MINIATURE</h1>

                    <h2><a href="/feedSubscriptions" class="subscription-link">Page d'abonnements</a></h2>


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
                                <button name="comment">&#128172; <a href="comment.jsp?postId=<%= post.getId() %>">Comment</a></button>
                            </article>

                            <% } } else { %>
                                <p>Aucun post à afficher pour le moment.</p>
                                <% } %>


                </body>

                </html>