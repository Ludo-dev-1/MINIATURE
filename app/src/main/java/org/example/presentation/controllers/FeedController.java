package org.example.presentation.controllers;

import java.io.IOException;

import java.util.Comparator;
import java.util.List;

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
    private List<User> users;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public static void verifyUserLoggedIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        return;
    }

    @Override
    public void init() {
        userRepository = RepositoryAdapter.getUserRepository(getServletContext());
        postRepository = RepositoryAdapter.getPostRepository(getServletContext());
        users = userRepository.findAll();
    }

    @Override
    // GET pour afficher le feed
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // on verifie que l'utilisateur est connecté avant de lui permettre d'accéder au
        // feed
        verifyUserLoggedIn(req, resp);
        List<Post> posts = postRepository.findAll(); // récupère depuis le repository
        // Récupère l'utilisateur courant depuis la session
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        // on récupère la liste des utilisateurs pour mettre à jour les posts avec les infos de follow et like
        List<User> users = userRepository.findAll();

        // on met à jour les posts avec les infos de follow et like pour l'utilisateur courant
        for (Post post : posts) {
            // Mets à jour l'auteur
            for (User user : users) {
                if (user.getUserId() == post.getUserId()) {
                    post.setAuthorName(user.getUsername());
                }
            }
            // Mets à jour follow et liked
            if (currentUser != null) {
                // Un utilisateur suit un autre utilisateur si son id est dans la liste des
                // following de l'utilisateur courant
                post.setFollowing(currentUser.getFollowing().contains(post.getUserId()));
                // Un utilisateur aime un post si son id est dans la liste des liked de
                // l'utilisateur courant
                post.setLiked(currentUser.getLiked().contains(post.getId()));
            } else {
                // Si l'utilisateur n'est pas connecté, on considère qu'il ne suit personne et
                // qu'il n'aime aucun post
                post.setLiked(false);
                post.setFollowing(false);
            }
        }

        // on trie les posts du plus récent au plus ancien
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        // on ajoute les posts en attribut pour les afficher dans le JSP
        req.setAttribute("posts", posts);

        // on affiche le feed
        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }

}
//  Ce servlet FeedController gère l'affichage du feed de l'application. Il vérifie d'abord que l'utilisateur est connecté avant de lui permettre d'accéder au feed. Ensuite, il récupère tous les posts depuis le repository et met à jour chaque post avec les informations sur l'auteur, si l'utilisateur suit l'auteur, et si l'utilisateur a aimé le post. Les posts sont ensuite triés par date de création, du plus récent au plus ancien, et passés en attribut à la page JSP pour affichage.
// En résumé, ce servlet est un composant clé pour gérer l'affichage du feed dans  l'application, en fournissant des fonctionnalités pour récupérer et enrichir les posts avec des informations pertinentes pour l'utilisateur connecté, et en les affichant de manière structurée sur la page du feed.     
