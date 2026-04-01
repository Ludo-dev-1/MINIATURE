package org.example.application.services;

import org.example.domain.entity.User;
import org.example.domain.repositories.UserRepository;


public class Authservice {

    private UserRepository userRepository;

    public Authservice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}
// Ce service d'authentification vérifie les informations d'identification de l'utilisateur en utilisant le UserRepository.     Il retourne l'utilisateur si les informations sont correctes, sinon il retourne null.
// Note : Dans une application réelle, il est recommandé de ne pas stocker les mots de passe en clair et d'utiliser des techniques de hachage pour sécuriser les données d'authentification.    
// De plus, ce service pourrait être étendu pour inclure des fonctionnalités telles que l'inscription, la gestion des sessions, et d'autres aspects liés à l'authentification et à la sécurité.     
// Ce service peut être utilisé dans les servlets pour gérer les processus de connexion des utilisateurs.       
// Exemple d'utilisation dans un servlet :
/*
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    Authservice authService = new Authservice(RepositoryAdapter.getUserRepository(getServletContext()));
    User user = authService.login(username, password);

    if (user != null) {
        // Authentification réussie, rediriger vers la page d'accueil ou tableau de bord
        response.sendRedirect("home.jsp");
    } else {
        // Authentification échouée, rediriger vers la page de connexion avec un message d'erreur
        response.sendRedirect("login.jsp?error=1");
    }
}
*/          
// Ce code dans le servlet montre comment utiliser le service d'authentification pour vérifier les informations d'identification de l'utilisateur et rediriger en conséquence.      
// Note : Assurez-vous de gérer les sessions utilisateur de manière appropriée après une authentification réussie pour maintenir l'état de connexion.   
// En résumé, ce service d'authentification est un composant clé pour gérer les processus de connexion des utilisateurs dans l'application, en utilisant le UserRepository pour vérifier les informations d'identification. 

