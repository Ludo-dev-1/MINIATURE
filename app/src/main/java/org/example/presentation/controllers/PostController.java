package org.example.presentation.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.example.domain.entity.Comment;
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

import static org.example.presentation.controllers.FeedController.verifyUserLoggedIn;

@WebServlet("/post")
public class PostController extends HttpServlet {

    private List<User> users;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Override
    public void init() {
        postRepository = RepositoryAdapter.getPostRepository(getServletContext());
        userRepository = RepositoryAdapter.getUserRepository(getServletContext());
        users = userRepository.findAll();
    }

    // POST pour créer un nouveau post
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // on verifie que l'utilisateur est connecté avant de lui permettre de poster
        verifyUserLoggedIn(req, resp);
        // Récupère le contenu du post et l'utilisateur courant
        String content = req.getParameter("content");
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        // on récupère l'id de l'utilisateur pour associer le post à son auteur
        long userId = currentUser.getUserId();

        // on verifie que le contenu n'est pas vide avant de créer le post
        if (content != null && !content.trim().isEmpty()) {
            long nextPostId = postRepository.findAll().stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1L;

            Post newPost = new Comment(content, userId, nextPostId, LocalDateTime.now(), currentUser.getUsername(),
                    false);
            postRepository.save(newPost);
        }
        // redirige vers le feed pour éviter les resoumissions de formulaire
        resp.sendRedirect(req.getContextPath() + "/feed");
        return;
    }

}

// Ce servlet PostController gère les opérations liées à la création de nouveaux posts dans l'application. La méthode doPost vérifie d'abord que l'utilisateur est connecté avant de lui permettre de créer un post. Ensuite, elle récupère le contenu du post et l'utilisateur courant, vérifie que le contenu n'est pas vide, et crée un nouveau post avec un identifiant unique, l'identifiant de l'auteur, la date de création, et le nom de l'auteur. Enfin, elle enregistre le nouveau post dans le repository et redirige l'utilisateur vers le feed pour éviter les resoumissions de formulaire.    
// En résumé, ce servlet est un composant clé pour gérer la création de nouveaux posts dans l'application, en fournissant des fonctionnalités pour vérifier la connexion de l'utilisateur, valider le contenu du post, et enregistrer le post de manière structurée dans le repository.     

