package org.example.domain.database;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Database {
    <T> void add(Class<T> type, T item);

    <T> boolean remove(Class<T> type, T item);

    <T> boolean removeIf(Class<T> type, Predicate<T> predicate);

    <T> Optional<T> findFirst(Class<T> type, Predicate<T> predicate);

    <T> List<T> findAll(Class<T> type, Predicate<T> predicate);

    <T> List<T> getAll(Class<T> type);

    <T> int size(Class<T> type);

    void clear();
}
// Cette interface Database définit les opérations de base pour gérer les données dans l'application. Elle permet d'ajouter, de supprimer, de trouver et de récupérer des éléments de différents types (comme User et Post) en utilisant des prédicats pour filtrer les résultats.
// Les méthodes sont génériques, ce qui permet de les utiliser pour différents types d'entités sans avoir à dupliquer le code pour chaque type. Par exemple, vous pouvez utiliser cette interface pour gérer à la fois les utilisateurs et les posts dans votre application.
// En résumé, cette interface Database est un composant clé pour gérer les données de l'application de manière flexible et générique, en fournissant des méthodes pour effectuer des opérations courantes sur les données.  

