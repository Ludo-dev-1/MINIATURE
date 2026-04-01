package org.example.presentation.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.domain.entity.Post;
import org.example.domain.entity.User;
import org.example.infrastructure.adapter.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.example.presentation.controllers.FeedController.verifyUserLoggedIn;
import static org.example.presentation.controllers.FeedController.recupUserInSession;

@WebServlet("/feedSubscriptions")
public class SubscriptionFeedController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // on verifie que l'utilisateur est connecté avant de lui permettre d'accéder au feed des abonnements
        if (!verifyUserLoggedIn(req, resp)) {
            return;
        }

        // On Récupére l'utilisateur connecté
        User currentUser = recupUserInSession(req);

        // On récupère tous les posts et utilisateurs
        List<Post> posts = RepositoryAdapter.getPostRepository(getServletContext()).findAll();
        List<User> users = RepositoryAdapter.getUserRepository(getServletContext()).findAll();

        // On crée une liste pour le feed des abonnements
        List<Post> feedPosts = new ArrayList<>();

        // On Filtre les posts
        for (Post post : posts) {
            // on ne garde que les posts des abonnements
            if (currentUser.getFollowing().contains(post.getUserId())) {
                // Ajouter le nom de l'auteur
                for (User user : users) {
                    if (user.getUserId() == post.getUserId()) {
                        post.setAuthorName(user.getUsername());
                        break;
                    }
                }

                // Indiquer si on suit l'auteur (utile pour le bouton follow)
                post.setFollowing(currentUser.getFollowing().contains(post.getUserId()));
                post.setLiked(currentUser.getLiked().contains(post.getId()));

                feedPosts.add(post);
            }
        }

        // Trier par date
        feedPosts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        // On met en attribut et forwarder
        req.setAttribute("posts", feedPosts);
        req.getRequestDispatcher("/feedSubscription.jsp").forward(req, resp);
    }
}

// Ce servlet SubscriptionFeedController gère l'affichage du feed des abonnements dans l'application. Il vérifie d'abord que l'utilisateur est
// connecté avant de lui permettre d'accéder au feed des abonnements. Ensuite, il récupère tous les posts et utilisateurs depuis les repositories, filtre
// les posts pour ne garder que ceux des utilisateurs auxquels l'utilisateur connecté est abonné, enrichit les posts avec le nom de l'auteur et les
// informations de suivi et de like, trie les posts par date de création, et les passe en attribut à la page JSP pour affichage.
// En résumé, ce servlet est un composant clé pour gérer l'affichage du feed des abonnements dans l'application, en fournissant des fonctionnalités pour
// récupérer et filtrer les posts en fonction des abonnements de l'utilisateur connecté, et en les affichant de manière structurée sur la page du feed des
// abonnements.
