package org.example.presentation.controllers;

import java.io.IOException;

import org.example.domain.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/like")
public class LikeController extends HttpServlet {
      @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // on récupère l'utilisateur courant pour lui permettre de liker ou unliker un post
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        // on verifie que l'utilisateur est connecté avant de lui permettre de liker ou unliker un post
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // on récupère l'id du post à liker ou unliker
        long postIdToLike = Long.parseLong(req.getParameter("postId"));

        // Toggle : unlike si déjà liké, like sinon
        if (currentUser.getLiked().contains(postIdToLike)) {
            currentUser.unlike(postIdToLike);
        } else {
            currentUser.like(postIdToLike);
        }

        // Mettre à jour la session
        req.getSession().setAttribute("currentUser", currentUser);

        // Retourner au feed
        resp.sendRedirect(req.getHeader("referer"));
    }


}
