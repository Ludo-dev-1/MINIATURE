package org.example.presentation.controllers;

import java.io.IOException;

import org.example.domain.model.User;

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
