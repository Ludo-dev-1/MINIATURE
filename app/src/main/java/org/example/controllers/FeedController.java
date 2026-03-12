package org.example.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.models.Post;
import org.example.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feed")
public class FeedController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        getServletContext().setAttribute("posts", posts);
       
    }

    private List<Post> posts = new ArrayList<>(List.of(
            new Post("Test post ", 1, 1, LocalDateTime.of(2023, 6, 26, 10, 0), "User1", false),
            new Post("Admin post", 2, 2, LocalDateTime.of(2024, 6, 26, 12, 0), "User2", false),
            new Post("Aujourd’hui j’ai commencé à travailler sur mon mini réseau social en Java avec Servlets et JSP. "
                    + "C’est intéressant de voir comment fonctionne l’architecture MVC côté serveur. "
                    + "J’ai réussi à gérer l’authentification des utilisateurs et maintenant je travaille sur le feed de posts. "
                    + "Prochaine étape : ajouter les likes et les commentaires !",
                    1, 3, LocalDateTime.of(2025, 6, 26, 14, 0), "User1", false),
            new Post("Je viens de terminer la fonctionnalité de feed pour mon mini réseau social en Java. ", 1, 1,
                    LocalDateTime.of(2024, 6, 26, 16, 0), "Fabien", false),
            new Post("C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ",
                    2, 2, LocalDateTime.of(2024, 6, 26, 18, 0), "Kevin", false),
            new Post("Je viens de mettre en place la fonctionnalité de follow pour mon mini réseau social. Maintenant, les utilisateurs peuvent suivre d’autres utilisateurs et voir leurs posts dans un feed personnalisé. C’est génial de voir comment tout cela prend forme ! Prochaine étape : ajouter la possibilité de liker les posts et de commenter. ",
                    3, 3, LocalDateTime.of(2024, 6, 26, 20, 0), "Eric", false),
            new Post("Je viens de terminer la fonctionnalité de feed pour mon mini réseau social en Java. C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ", 2, 2, LocalDateTime.of(2024, 6, 26, 22, 0), "Kevin", false),
            new Post("C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ", 1, 1, LocalDateTime.of(2024, 6, 27, 0, 0), "Fabien", false))

    );

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = (User) req.getSession().getAttribute("currentUser");
        List<User> users = (List<User>) getServletContext().getAttribute("users");
        for (Post post : posts) {

            for (User user : users) {
                if (user.getUserId() == post.getUserId()) {
                    post.setAuthorName(user.getUsername());
                }
            }

            if (currentUser != null) {
                boolean isFollowing = currentUser.getFollowing().contains(post.getUserId());
                post.setFollowing(isFollowing);
            }
        }

        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        req.setAttribute("posts", posts);

        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String content = req.getParameter("content");
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long userId = currentUser.getUserId();

        if (content != null && !content.trim().isEmpty()) {
            Post newPost = new Post(content, userId, posts.size() + 1, LocalDateTime.now(), currentUser.getUsername(),
                    false);
            posts.add(newPost);
        }

        resp.sendRedirect(req.getContextPath() + "/feed");
        return;
    }

}
