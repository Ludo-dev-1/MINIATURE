package org.example.presentation.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.example.domain.entity.Comment;
import org.example.domain.entity.Post;
import org.example.domain.entity.User;
import org.example.domain.repositories.CommentRepository;
import org.example.domain.repositories.PostRepository;
import org.example.infrastructure.adapter.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.example.presentation.controllers.FeedController.verifyUserLoggedIn;
import static org.example.presentation.controllers.FeedController.recupUserInSession;

@WebServlet("/comments")
public class CommentController extends HttpServlet {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    @Override
    public void init() throws ServletException {
        postRepository = RepositoryAdapter.getPostRepository(getServletContext());
        commentRepository = RepositoryAdapter.getCommentRepository(getServletContext());
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

        // Récupérer les commentaires pour ce post depuis le repository
        List<Comment> postComments = commentRepository.findByPostId(postId);

        req.setAttribute("post", post);
        req.setAttribute("comments", postComments);

        req.getRequestDispatcher("/comment.jsp").forward(req, resp);
    }

    // POST pour ajouter un nouveau commentaire
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // on verifie que l'utilisateur est connecté avant de lui permettre de commenter
        if (!verifyUserLoggedIn(req, resp)) {
            return;
        }

        // on récupère l'utilisateur courant pour lui permettre de créer un commentaire
        User currentUser = recupUserInSession(req);

        // Récupère le contenu du commentaire et l'utilisateur courant
        String content = req.getParameter("content");
        long postId = Long.parseLong(req.getParameter("postId"));

        // Crée le commentaire avec le nom de l'auteur
        Comment newComment = new Comment(
                content,
                currentUser.getUserId(),
            0,
                LocalDateTime.now(),
                currentUser.getUsername(),
                false);
        newComment.setPostId(postId);

        // Persiste le commentaire via le repository
        commentRepository.save(newComment);

        // Redirection vers la page de détail du post (CommentController gère postId)
        resp.sendRedirect(req.getContextPath() + "/comments?postId=" + postId);
    }

}

// Ce servlet CommentController gère les opérations liées aux commentaires dans l'application. Il permet d'afficher les commentaires associés à un post
// spécifique et de permettre aux utilisateurs connectés d'ajouter de nouveaux commentaires. Les commentaires sont stockés dans une liste en mémoire, et le
// servlet utilise le contexte de la servlet pour partager cette liste à travers différentes requêtes.
// La méthode doGet récupère les commentaires pour un post donné et les affiche sur une page de détail du post, tandis que la méthode doPost permet aux
// utilisateurs de soumettre de nouveaux commentaires, qui sont ensuite ajoutés à la liste et affichés sur la même page.
// En résumé, ce servlet est un composant clé pour gérer les interactions des utilisateurs avec les commentaires dans l'application, en fournissant des
// fonctionnalités pour afficher et ajouter des commentaires de manière structurée.
