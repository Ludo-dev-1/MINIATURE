package org.example.controllers;

import java.io.IOException;
import java.time.LocalDate;
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

    private List<Post> posts = new ArrayList<>(List.of(
            new Post("Test post ", 1, 1, LocalDate.of(2023, 6, 26)),
            new Post("Admin post", 2, 2, LocalDate.of(2024, 6, 26)),
            new Post("Aujourd’hui j’ai commencé à travailler sur mon mini réseau social en Java avec Servlets et JSP. "
                    + "C’est intéressant de voir comment fonctionne l’architecture MVC côté serveur. "
                    + "J’ai réussi à gérer l’authentification des utilisateurs et maintenant je travaille sur le feed de posts. "
                    + "Prochaine étape : ajouter les likes et les commentaires !",
                    1, 3, LocalDate.of(2025, 6, 26)),
            new Post("Je viens de terminer la fonctionnalité de feed pour mon mini réseau social en Java. ", 1, 1,
                    LocalDate.of(2024, 6, 26))

    ));

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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
            Post newPost = new Post(content, userId, posts.size() + 1, LocalDate.now());
            posts.add(newPost);
        }

        resp.sendRedirect(req.getContextPath() + "/feed");
        return;
    }
}
