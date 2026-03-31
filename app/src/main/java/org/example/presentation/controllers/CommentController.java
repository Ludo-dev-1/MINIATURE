package org.example.presentation.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.example.domain.entity.Comment;
import org.example.domain.entity.Post;
import org.example.domain.entity.User;
import org.example.domain.repositories.PostRepository;
import org.example.infrastructure.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class CommentController extends HttpServlet {

    private List<Comment> comments = new ArrayList<>();
    private PostRepository postRepository;

    @Override
    public void init() throws ServletException {
        postRepository = RepositoryAdapter.getPostRepository(getServletContext());

        @SuppressWarnings("unchecked")
        List<Comment> existingComments = (List<Comment>) getServletContext().getAttribute("comments");
        if (existingComments != null) {
            comments = existingComments;
            return;
        }

        // Initialisation avec quelques commentaires de test
        comments.add(new Comment("Super post !", 1, 1, LocalDateTime.now().minusDays(2), "Alice", false));
        comments.add(new Comment("Je suis d'accord !", 2, 1, LocalDateTime.now().minusDays(1), "Bob", false));
        comments.add(new Comment("Merci pour le partage.", 3, 2, LocalDateTime.now().minusHours(12), "Charlie", false));
        // On stocke dans le contexte pour un accès global si besoin
        getServletContext().setAttribute("comments", comments);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String postIdParam = req.getParameter("postId");
        if (postIdParam == null) {
            resp.sendRedirect(req.getContextPath() + "/feed");
            return;
        }

        long postId;
        try {
            postId = Long.parseLong(postIdParam);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/feed");
            return;
        }

        // Récupérer le post depuis le même repository que le feed
        Post post = postRepository.findAll().stream()
            .filter(p -> p.getId() == postId)
            .findFirst()
            .orElse(null);
        if (post == null) {
            resp.sendRedirect(req.getContextPath() + "/feed");
            return;
        }

        // Filtrer les commentaires pour ce post
        List<Comment> postComments = comments.stream()
                .filter(c -> c.getPostId() == postId)
                .collect(Collectors.toList());

        req.setAttribute("post", post);
        req.setAttribute("comments", postComments);

        req.getRequestDispatcher("/comment.jsp").forward(req, resp);
    }

    // POST pour ajouter un nouveau commentaire
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Récupère le contenu du commentaire et l'utilisateur courant
        String content = req.getParameter("content");
        long postId = Long.parseLong(req.getParameter("postId"));
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        // Vérifie que l'utilisateur est connecté
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Création d'un nouvel ID pour le commentaire
        long newId = comments.size() + 1;

        // Crée le commentaire avec le nom de l'auteur
        Comment newComment = new Comment(
                content,
                currentUser.getUserId(),
                newId,
            LocalDateTime.now(),
                currentUser.getUsername(),
                false);
        newComment.setPostId(postId);

        // Ajoute le commentaire à la liste
        comments.add(newComment);

        // Mettre à jour le contexte si besoin
        getServletContext().setAttribute("comments", comments);

        // Redirection vers la page de détail du post (CommentController gère postId)
        resp.sendRedirect(req.getContextPath() + "/comments?postId=" + postId);
    }

}