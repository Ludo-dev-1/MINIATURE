package org.example.presentation.controllers;

import java.io.IOException;

import org.example.domain.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.example.presentation.controllers.FeedController.verifyUserLoggedIn;;

@WebServlet("/follow")
public class FollowController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // on récupère l'utilisateur courant pour lui permettre de suivre ou unfollow un
        // autre utilisateur
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        // on vérifie que l'utilisateur est connecté avant de lui permettre de suivre ou
        // unfollow un autre utilisateur
        verifyUserLoggedIn(req, resp);

        // on récupère l'id de l'utilisateur à suivre ou unfollow
        long userIdToFollow = Long.parseLong(req.getParameter("userId"));

        // Toggle : unfollow si déjà suivi, follow sinon
        if (currentUser.getFollowing().contains(userIdToFollow)) {
            currentUser.unfollow(userIdToFollow);
        } else {
            currentUser.follow(userIdToFollow);
        }

        // Mettre à jour la session
        req.getSession().setAttribute("currentUser", currentUser);

        // Retourner au feed
        resp.sendRedirect(req.getHeader("referer"));
    }
}
// Ce servlet FollowController gère les opérations de suivi et de non-suivi entre les utilisateurs dans l'application. Il vérifie d'abord que l'utilisateur est connecté avant de lui permettre de suivre ou de ne plus suivre un autre utilisateur. Ensuite, il récupère l'identifiant de l'utilisateur à suivre ou à ne plus suivre à partir des paramètres de la requête, et effectue l'action correspondante (suivre ou ne plus suivre) en fonction de l'état actuel du suivi. Enfin, il met à jour la session avec les nouvelles informations de suivi et redirige l'utilisateur vers la page précédente (généralement le feed) pour voir les changements.    
// En résumé, ce servlet est un composant clé pour gérer les interactions de suivi entre les utilisateurs dans l'application, en fournissant des fonctionnalités pour suivre ou ne plus suivre d'autres utilisateurs de manière structurée et sécurisée.    
