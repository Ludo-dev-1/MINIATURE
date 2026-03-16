package org.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.example.models.Comment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class CommentController extends HttpServlet {

    private List<Comment> comments = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Exemple de commentaires existants
        comments.add(new Comment(1, 1, 2, "Super post !"));
        comments.add(new Comment(2, 1, 3, "J'aime beaucoup !"));

        // On stocke dans le contexte pour un accès global si besoin
        getServletContext().setAttribute("comments", comments);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Récupérer l'ID du post depuis l'URL
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

        // Filtrer les commentaires pour ce post
        List<Comment> postComments = comments.stream()
                .filter(c -> c.getPostId() == postId)
                .collect(Collectors.toList());

        req.setAttribute("comments", postComments);
        req.setAttribute("postId", postId);

        req.getRequestDispatcher("/comment.jsp").forward(req, resp);
    }

    // POST pour ajouter un nouveau commentaire
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String content = req.getParameter("content");
        String author = req.getParameter("author");
        long postId = Long.parseLong(req.getParameter("postId"));

        // Création d'un nouvel ID simple (increment)
        long newId = comments.size() + 1;
        Comment newComment = new Comment(newId, postId, 0, content); // 0 = userId temporaire

        comments.add(newComment);

        // Mettre à jour le contexte
        getServletContext().setAttribute("comments", comments);

        // Redirection vers la page de détail du post
        resp.sendRedirect(req.getContextPath() + "/postDetail?id=" + postId);
    }
}