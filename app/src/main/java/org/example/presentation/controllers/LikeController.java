package org.example.presentation.controllers;

import java.io.IOException;

import org.example.domain.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.example.presentation.controllers.FeedController.verifyUserLoggedIn;
import static org.example.presentation.controllers.FeedController.recupUserInSession;

@WebServlet("/like")
public class LikeController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // on verifie que l'utilisateur est connecté avant de lui permettre de liker ouunliker un post
        if (!verifyUserLoggedIn(req, resp)) {
            return;
        }
        // on récupère l'utilisateur courant pour lui permettre de liker ou unliker un post
        User currentUser = recupUserInSession(req);

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
// Ce servlet LikeController gère les opérations de like et de unlike sur les posts dans l'application. Il vérifie d'abord que l'utilisateur est connecté
// avant de lui permettre de liker ou de ne plus liker un post. Ensuite, il récupère l'identifiant du post à liker ou à ne plus liker à partir des
// paramètres de la requête, et effectue l'action correspondante (liker ou ne plus liker) en fonction de l'état actuel du like. Enfin, il met à jour la
// session avec les nouvelles informations de like et redirige l'utilisateur vers la page précédente (généralement le feed) pour voir les changements.
// En résumé, ce servlet est un composant clé pour gérer les interactions de like entre les utilisateurs et les posts dans l'application, en fournissant
// des fonctionnalités pour liker ou ne plus liker des posts de manière structurée et sécurisée.
