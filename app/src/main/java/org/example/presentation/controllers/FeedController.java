package org.example.presentation.controllers;

import java.io.IOException;
import java.util.List;

import org.example.application.services.GetFeedUseCase;
import org.example.domain.entity.Post;
import org.example.domain.entity.User;
import org.example.domain.repositories.PostRepository;
import org.example.domain.repositories.UserRepository;
import org.example.infrastructure.adapter.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feed")
public class FeedController extends HttpServlet {
    private GetFeedUseCase getFeedUseCase;

    public static boolean verifyUserLoggedIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    public static User recupUserInSession(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("currentUser");
    }

    @Override
    public void init() {
        UserRepository userRepository = RepositoryAdapter.getUserRepository(getServletContext());
        PostRepository postRepository = RepositoryAdapter.getPostRepository(getServletContext());
        getFeedUseCase = new GetFeedUseCase(postRepository, userRepository);
    }

    @Override
    // GET pour afficher le feed
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // on verifie que l'utilisateur est connecté avant de lui permettre d'accéder aufeed
        if (!verifyUserLoggedIn(req, resp)) {
            return;
        }
        // Récupère l'utilisateur courant depuis la session
        User currentUser = recupUserInSession(req);

        List<Post> posts = getFeedUseCase.execute(currentUser);

        // on ajoute les posts en attribut pour les afficher dans le JSP
        req.setAttribute("posts", posts);

        // on affiche le feed
        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }

}
// Ce servlet FeedController gère l'affichage du feed de l'application. Il vérifie d'abord que l'utilisateur est connecté avant de lui permettre
// d'accéder au feed. Ensuite, il récupère tous les posts depuis le repository et met à jour chaque post avec les informations sur l'auteur, si
// l'utilisateur suit l'auteur, et si l'utilisateur a aimé le post. Les posts sont ensuite triés par date de création, du plus récent au plus ancien, et
// passés en attribut à la page JSP pour affichage.
// En résumé, ce servlet est un composant clé pour gérer l'affichage du feed dans l'application, en fournissant des fonctionnalités pour récupérer et
// enrichir les posts avec des informations pertinentes pour l'utilisateur connecté, et en les affichant de manière structurée sur la page du feed.
