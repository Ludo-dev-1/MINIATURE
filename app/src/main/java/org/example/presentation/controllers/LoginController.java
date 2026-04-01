package org.example.presentation.controllers;

import java.io.IOException;

import org.example.application.services.Authservice;
import org.example.domain.entity.User;
import org.example.domain.repositories.UserRepository;
import org.example.infrastructure.RepositoryAdapter;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

    private Authservice authService;

    @Override
    public void init() {
        UserRepository repo = RepositoryAdapter.getUserRepository(getServletContext());
        this.authService = new Authservice(repo);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = authService.login(username, password);

        if (user != null) {
            req.getSession().setAttribute("currentUser", user);
            resp.sendRedirect(req.getContextPath() + "/feed");
        } else {
            resp.sendRedirect(req.getContextPath() + "/register");
        }
    }
}   
// Ce servlet LoginController gère les opérations de connexion des utilisateurs dans l'application. Il utilise un service d'authentification (Authservice) pour vérifier les informations d'identification de l'utilisateur. La méthode doGet affiche la page de connexion, tandis que la méthode doPost traite les informations de connexion soumises par l'utilisateur. Si les informations sont valides, l'utilisateur est stocké dans la session et redirigé vers le feed. Sinon, il est redirigé vers la page d'inscription.    
// En résumé, ce servlet est un composant clé pour gérer les processus de connexion des utilisateurs dans l'application, en utilisant le service d'authentification pour vérifier les informations d'identification et en gérant les redirections appropriées en fonction du résultat de la connexion.      
