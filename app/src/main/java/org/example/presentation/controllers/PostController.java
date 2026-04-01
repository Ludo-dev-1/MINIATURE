package org.example.presentation.controllers;

import java.io.IOException;

import org.example.application.usecase.CreatePostUseCase;
import org.example.domain.entity.User;
import org.example.domain.repositories.PostRepository;
import org.example.infrastructure.adapter.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.example.presentation.controllers.FeedController.verifyUserLoggedIn;
import static org.example.presentation.controllers.FeedController.recupUserInSession;


@WebServlet("/post")
public class PostController extends HttpServlet {

    private CreatePostUseCase createPostUseCase;

    @Override
    public void init() {
        PostRepository postRepository = RepositoryAdapter.getPostRepository(getServletContext());
        createPostUseCase = new CreatePostUseCase(postRepository);
    }

    // POST pour créer un nouveau post
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // on verifie que l'utilisateur est connecté avant de lui permettre de poster
         if (!verifyUserLoggedIn(req, resp)) {
            return;
        }

        // on récupère l'utilisateur courant pour lui permettre de créer un post
        User currentUser = recupUserInSession(req);

        // Récupère le contenu du post et l'utilisateur courant
        String content = req.getParameter("content");

        createPostUseCase.execute(content, currentUser);
        // redirige vers le feed pour éviter les resoumissions de formulaire
        resp.sendRedirect(req.getContextPath() + "/feed");
        return;
    }

}

// Ce servlet PostController gère les opérations liées à la création de nouveaux posts dans l'application. La méthode doPost vérifie d'abord que l'utilisateur est connecté avant de lui permettre de créer un post. Ensuite, elle récupère le contenu du post et l'utilisateur courant, vérifie que le contenu n'est pas vide, et crée un nouveau post avec un identifiant unique, l'identifiant de l'auteur, la date de création, et le nom de l'auteur. Enfin, elle enregistre le nouveau post dans le repository et redirige l'utilisateur vers le feed pour éviter les resoumissions de formulaire.    
// En résumé, ce servlet est un composant clé pour gérer la création de nouveaux posts dans l'application, en fournissant des fonctionnalités pour vérifier la connexion de l'utilisateur, valider le contenu du post, et enregistrer le post de manière structurée dans le repository.     

