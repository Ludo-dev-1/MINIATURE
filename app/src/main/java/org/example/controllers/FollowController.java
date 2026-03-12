package org.example.controllers;

import java.io.IOException;

import org.example.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/follow")
public class FollowController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long userIdToFollow = Long.parseLong(req.getParameter("userId"));

        // Ajouter dans la liste des suivis
        currentUser.follow(userIdToFollow);

        // Mettre à jour la session
        req.getSession().setAttribute("currentUser", currentUser);

        // Retourner au feed
        resp.sendRedirect(req.getHeader("referer"));
    }
}
