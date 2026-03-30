package org.example.presentation.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.domain.model.Comment;
import org.example.domain.model.Post;
import org.example.domain.model.User;
import org.example.domain.repositories.PostRepository;
import org.example.domain.repositories.UserRepository;
import org.example.infrastructure.RepositoryAdapter;

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

    @Override
    public void init() {
        userRepository = RepositoryAdapter.getUserRepository(getServletContext());
        postRepository = RepositoryAdapter.getPostRepository(getServletContext());
        users = userRepository.findAll();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Post> posts = postRepository.findAll(); // récupère depuis le repository
        /* postRepository.save(newPost); // pour ajouter un post */

        // on récupère l'utilisateur courant et la liste des utilisateurs pour mettre à
        // jour les posts avec les infos de follow et like
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        // on vérifie que l'utilisateur est connecté avant de lui permettre de voir le
        // feed
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // on récupère la liste des utilisateurs pour mettre à jour les posts avec les
        // infos de follow et like
        List<User> users = userRepository.findAll();

        // on met à jour les posts avec les infos de follow et like pour l'utilisateur
        // courant
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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupère le contenu du post et l'utilisateur courant
        String content = req.getParameter("content");
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        // on verifie que l'utilisateur est connecté avant de lui permettre de poster
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // on récupère l'id de l'utilisateur pour associer le post à son auteur
        long userId = currentUser.getUserId();

        // on verifie que le contenu n'est pas vide avant de créer le post
        if (content != null && !content.trim().isEmpty()) {
            Post newPost = new Comment(content, userId, Post.size() + 1, LocalDateTime.now(), currentUser.getUsername(),
                false);
           postRepository.save(newPost);    
        }

        // redirige vers le feed pour éviter les resoumissions de formulaire
        resp.sendRedirect(req.getContextPath() + "/feed");
        return;
    }

}
