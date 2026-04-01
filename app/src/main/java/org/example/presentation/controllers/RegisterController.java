package org.example.presentation.controllers;

import java.io.IOException;

import org.example.application.services.RegisterService;
import org.example.domain.repositories.UserRepository;
import org.example.infrastructure.adapter.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
    private RegisterService registerService;
    
    @Override
    // on initialise le service de register avec le repository d'utilisateur
    public void init() {
        UserRepository repo = RepositoryAdapter.getUserRepository(getServletContext());
        this.registerService = new RegisterService(repo);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try {
            registerService.register(username, password, email);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/register?error=1");
        }
    }
}

// Ce servlet RegisterController gère les opérations d'inscription des utilisateurs dans l'application. Il utilise un service d'inscription (RegisterService) pour créer de nouveaux utilisateurs. La méthode doGet affiche la page d'inscription, tandis que la méthode doPost traite les informations d'inscription soumises par l'utilisateur. Si l'inscription est réussie, l'utilisateur est redirigé vers la page de connexion. Sinon, il est redirigé vers la page d'inscription avec un paramètre d'erreur.    
// En résumé, ce servlet est un composant clé pour gérer les processus d'inscription des utilisateurs dans l'application, en utilisant le service d'inscription pour créer de nouveaux utilisateurs et en gérant les redirections appropriées en fonction du résultat de l'inscription.     
