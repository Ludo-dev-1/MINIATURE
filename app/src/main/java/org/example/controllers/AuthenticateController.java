package org.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.models.User;
import org.jspecify.annotations.NonNull;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthenticateController", urlPatterns = { "/register", "/login" })
public class AuthenticateController extends HttpServlet {
    private List<@NonNull User> users = new ArrayList<>();

    // on initialise une liste d'utilisateurs en dur 
    public void init() {
        users.add(new User("test", "test123", "test@example.com", 1));
        users.add(new User("admin", "admin123", "admin@example.com", 2));
        users.add(new User("Kevin", "user123", "user@user.com", 3));
        users.add(new User("Fabien", "user456", "user2@user.com", 4));
        users.add(new User("Eric", "user789", "user3@user.com", 5));

        // on partage la liste d'utilisateurs dans le contexte pour pouvoir y accéder depuis les autres servlets
        getServletContext().setAttribute("users", users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // on ajoute la liste d'utilisateurs en attribut pour les afficher dans le JSP (utile pour le feed et les infos de follow et like)
        req.setAttribute("userList", users);

        // on affiche le formulaire de login ou d'inscription en fonction de l'URL
        String path = req.getServletPath();

        if (path.equals("/register")) {
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // on récupère l'URL pour savoir si on est en train de s'inscrire ou de se connecter
        String path = req.getServletPath();
        // on traite l'inscription
        if (path.equals("/register")) {
            // on récupère les infos du formulaire d'inscription
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");

            if (username == null || password == null || email == null) {
                resp.sendRedirect(req.getContextPath() + "/register");
                return;
            }

            User newUser = new User(username, password, email, users.size() + 1);
            users.add(newUser);

            // on met à jour la liste d'utilisateurs dans le contexte pour pouvoir y accéder depuis les autres servlets
            getServletContext().setAttribute("users", users);

            // on redirige vers le formulaire de login après l'inscription
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // on traite la connexion
        if (path.equals("/login")) {
            // on récupère les infos du formulaire de login
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    // on met l'utilisateur en session pour le garder connecté
                    req.getSession().setAttribute("currentUser", user);

                    // on redirige vers le feed après la connexion
                    resp.sendRedirect(req.getContextPath() + "/feed");
                    return;
                }
            }
            // sinon, on redirige vers le formulaire de login avec un message d'erreur
            resp.sendRedirect(req.getContextPath() + "/login?error=1");
        }
    }

}
