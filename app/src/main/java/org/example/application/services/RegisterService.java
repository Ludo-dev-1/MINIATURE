package org.example.application.services;

import org.example.domain.entity.User;
import org.example.domain.repositories.UserRepository;

public class RegisterService {

    private UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String username, String password, String email) {

        if (username == null || password == null || email == null) {
            throw new IllegalArgumentException("Invalid data");
        }

        User existing = userRepository.findByUsername(username);
        if (existing != null) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User(username, password, email, 0);
        userRepository.save(user);
    }
}
// Ce service d'enregistrement gère le processus d'inscription des nouveaux utilisateurs. Il vérifie que les données fournies sont valides et que le nom d'utilisateur n'est pas déjà pris avant de créer un nouvel utilisateur et de le sauvegarder dans le UserRepository.    
// Note : Comme pour le service d'authentification, il est recommandé de ne pas stocker les mots de passe en clair et d'utiliser des techniques de hachage pour sécuriser les données d'inscription.    
// Ce service peut être utilisé dans les servlets pour gérer les processus d'inscription des utilisateurs.
// Exemple d'utilisation dans un servlet :
/*
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String email = request.getParameter("email");

    RegisterService registerService = new RegisterService(RepositoryAdapter.getUserRepository(getServletContext()));
    try {
        registerService.register(username, password, email);
        response.sendRedirect("login.jsp?registered=1");
    } catch (IllegalArgumentException | IllegalStateException e) {
        response.sendRedirect("register.jsp?error=1");
    }
}
*/
// Ce code dans le servlet montre comment utiliser le service d'enregistrement pour créer un nouvel utilisateur à partir des données soumises dans un formulaire d'inscription. En cas de succès, l'utilisateur est redirigé vers la page de connexion avec un message indiquant que l'inscription a réussi. En cas d'erreur, l'utilisateur est redirigé vers la page d'inscription avec un message d'erreur.    
// En résumé, ce service d'enregistrement est un composant clé pour gérer les processus d'inscription des utilisateurs dans l'application, en utilisant le UserRepository pour vérifier les données et créer de nouveaux utilisateurs.  
