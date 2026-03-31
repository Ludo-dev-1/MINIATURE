package org.example.presentation.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.domain.model.Post;
import org.example.domain.model.User;
import org.example.infrastructure.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.example.presentation.controllers.FeedController.verifyUserLoggedIn;

@WebServlet("/feedSubscriptions")
public class SubscriptionFeedController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // On Récupére l'utilisateur connecté
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        verifyUserLoggedIn(req, resp);

        // On récupére tous les posts et utilisateurs
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