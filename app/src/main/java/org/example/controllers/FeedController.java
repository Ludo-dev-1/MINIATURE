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

    // on initialise une liste de posts en dur pour les afficher dans le feed, on associe chaque post à un utilisateur pour pouvoir afficher les infos de follow et like dans le feed
    private List<Post> posts = new ArrayList<>(List.of(
            new Post("Test post ", 1, 1, LocalDateTime.of(2023, 6, 26, 10, 0), "User1", false),
            new Post("Admin post", 2, 2, LocalDateTime.of(2024, 6, 26, 12, 0), "User2", false),
            new Post("Aujourd’hui j’ai commencé à travailler sur mon mini réseau social en Java avec Servlets et JSP. "
                    + "C’est intéressant de voir comment fonctionne l’architecture MVC côté serveur. "
                    + "J’ai réussi à gérer l’authentification des utilisateurs et maintenant je travaille sur le feed de posts. "
                    + "Prochaine étape : ajouter les likes et les commentaires !",
                    2, 3, LocalDateTime.of(2025, 6, 26, 14, 0), "Admin", false),
            new Post("Je viens de terminer la fonctionnalité de feed pour mon mini réseau social en Java. ", 4, 8,
                    LocalDateTime.of(2024, 6, 26, 16, 0), "Fabien", false),
            new Post(
                    "C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ",
                    3, 4, LocalDateTime.of(2024, 6, 26, 18, 0), "Kevin", false),
            new Post(
                    "Je viens de mettre en place la fonctionnalité de follow pour mon mini réseau social. Maintenant, les utilisateurs peuvent suivre d’autres utilisateurs et voir leurs posts dans un feed personnalisé. C’est génial de voir comment tout cela prend forme ! Prochaine étape : ajouter la possibilité de liker les posts et de commenter. ",
                    5, 5, LocalDateTime.of(2024, 6, 26, 20, 0), "Eric", false),
            new Post(
                    "Je viens de terminer la fonctionnalité de feed pour mon mini réseau social en Java. C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ",
                    3, 6, LocalDateTime.of(2024, 6, 26, 22, 0), "Kevin", false),
            new Post(
                    "C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ",
                    4, 7, LocalDateTime.of(2024, 6, 27, 0, 0), "Fabien", false))

    );

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // on récupère l'utilisateur courant et la liste des utilisateurs pour mettre à jour les posts avec les infos de follow et like
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        // on vérifie que l'utilisateur est connecté avant de lui permettre de voir le feed
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        List<User> users = (List<User>) getServletContext().getAttribute("users");

        // on met à jour les posts avec les infos de follow et like pour l'utilisateur courant
        for (Post post : posts) {
            // Mets à jour l'auteur
            for (User user : users) {
                if (user.getUserId() == post.getUserId()) {
                    post.setAuthorName(user.getUsername());
                }
            }

            // Mets à jour follow et liked
            if (currentUser != null) {
                // Un utilisateur suit un autre utilisateur si son id est dans la liste des following de l'utilisateur courant
                post.setFollowing(currentUser.getFollowing().contains(post.getUserId()));

                // Un utilisateur aime un post si son id est dans la liste des liked de l'utilisateur courant
                post.setLiked(currentUser.getLiked().contains(post.getId()));
            } else {
                // Si l'utilisateur n'est pas connecté, on considère qu'il ne suit personne et qu'il n'aime aucun post
                post.setLiked(false);
                post.setFollowing(false);
            }
        }

        // on trie les posts du plus récent au plus ancien
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        // on ajoute les posts en attribut pour les afficher dans le JSP
        req.setAttribute("posts", posts);

        // on affiche le feed
        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupère le contenu du post et l'utilisateur courant
        String content = req.getParameter("content");
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        // on verifie que l'utilisateur est connecté avant de lui permettre de poster
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // on récupère l'id de l'utilisateur pour associer le post à son auteur
        long userId = currentUser.getUserId();

        // on verifie que le contenu n'est pas vide avant de créer le post
        if (content != null && !content.trim().isEmpty()) {
            Post newPost = new Post(content, userId, posts.size() + 1, LocalDateTime.now(), currentUser.getUsername(),
                    false);
            posts.add(newPost);
        }

        // redirige vers le feed pour éviter les resoumissions de formulaire
        resp.sendRedirect(req.getContextPath() + "/feed");
        return;
    }

}
