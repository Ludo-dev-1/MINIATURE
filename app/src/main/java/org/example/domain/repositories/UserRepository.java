package org.example.domain.repositories;

import java.util.List;

import org.example.domain.entity.User;

public interface UserRepository {
    User findByUsername(String username);

    List<User> findAll();

    void save(User user);
}
// Cette interface UserRepository définit les opérations de base pour gérer les utilisateurs dans l'application. Elle fournit des méthodes pour trouver un utilisateur par son nom d'utilisateur, pour trouver tous les utilisateurs et pour enregistrer un utilisateur.
// En résumé, cette interface UserRepository est un composant clé pour gérer les utilisateurs dans l'application, en fournissant une structure pour effectuer des opérations courantes sur les utilisateurs, telles que la récupération d'un utilisateur spécifique ou de tous les utilisateurs, et l'enregistrement de nouveaux utilisateurs.          
