package org.example.presentation.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); 

        if (session != null) {
            session.invalidate(); 
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}

// Ce servlet LogoutController gère les opérations de déconnexion des utilisateurs dans l'application. La méthode doGet invalide la session de l'utilisateur actuel, ce qui le déconnecte de l'application, puis redirige l'utilisateur vers la page de connexion.
// En résumé, ce servlet est un composant clé pour gérer les processus de déconnexion des utilisateurs dans l'application, en assurant que la session de l'utilisateur est correctement invalidée et en redirigeant l'utilisateur vers la page de connexion après la déconnexion.       
