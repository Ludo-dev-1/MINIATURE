package org.example.infrastructure.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.example.domain.database.Database;

public class InMemoryDatabase implements Database {
       
    // singleton pour garantir une seule instance de la base de données en mémoire dans toute l'application : une seule BDD partagée entre tous les repositories et services qui en ont besoin.
    private static final InMemoryDatabase INSTANCE = new InMemoryDatabase();

    // Analogie Postgres : la Map représente la base de données, où chaque clé est une classe d'entité (User, Post, Comment, etc.) et la valeur associée est une liste d'instances de cette classe (les "lignes" de la table correspondante).
    private final Map<Class<?>, List<Object>> storage = new HashMap<>();

    // Le constructeur privé empêche la création d'instances supplémentaires de la classe InMemoryDatabase, garantissant ainsi que tous les accès à la base de données se font via l'instance unique (singleton) fournie par la méthode getInstance().
    private InMemoryDatabase() {
    }

    // Méthode statique pour accéder à l'instance unique de la base de données en mémoire.
    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

     
    // Analogies Postgres : add = INSERT INTO
    @Override
    public synchronized <T> void add(Class<T> type, T item) {
        storage.computeIfAbsent(type, ignored -> new ArrayList<>()).add(item);
    }

    // Analogies Postgres : remove = DELETE FROM
    @Override
    public synchronized <T> boolean remove(Class<T> type, T item) {
        List<Object> bucket = storage.get(type);
        if (bucket == null) {
            return false;
        }
        return bucket.remove(item);
    }

    // Analogies Postgres : remove with predicate = DELETE FROM ... WHERE ...
    @Override
    public synchronized <T> boolean removeIf(Class<T> type, Predicate<T> predicate) {
        List<Object> bucket = storage.get(type);
        if (bucket == null || bucket.isEmpty()) {
            return false;
        }
        return bucket.removeIf(entry -> predicate.test(type.cast(entry)));
    }

    // Analogies Postgres : findFirst = SELECT * FROM ... WHERE ... LIMIT 1
    @Override
    public synchronized <T> Optional<T> findFirst(Class<T> type, Predicate<T> predicate) {
        List<Object> bucket = storage.get(type);
        if (bucket == null) {
            return Optional.empty();
        }
        return bucket.stream()
                .map(type::cast)
                .filter(predicate)
                .findFirst();
    }

    // Analogies Postgres : findAll = SELECT * FROM ... WHERE ...
    @Override
    public synchronized <T> List<T> findAll(Class<T> type, Predicate<T> predicate) {
        List<Object> bucket = storage.get(type);
        if (bucket == null) {
            return List.of();
        }
        return bucket.stream()
                .map(type::cast)
                .filter(predicate)
                .toList();
    }

    // Analogies Postgres : getAll = SELECT * FROM ...
    @Override
    public synchronized <T> List<T> getAll(Class<T> type) {
        List<Object> bucket = storage.get(type);
        if (bucket == null) {
            return List.of();
        }
        return bucket.stream()
                .map(type::cast)
                .toList();
    }

    // Analogies Postgres : count = SELECT COUNT(*) FROM ...
    @Override
    public synchronized <T> int size(Class<T> type) {
        List<Object> bucket = storage.get(type);
        return bucket == null ? 0 : bucket.size();
    }

    // Analogies Postgres : clear = TRUNCATE / DROP ALL
    @Override
    public synchronized void clear() {
        storage.clear();
    }
}

// Note : les données sont initialisées dans les repositories pour éviter les dépendances circulaires entre les repositories et la base de données.
// Par exemple, le InMemoryPostRepository initialise des posts avec des userId qui font référence à des utilisateurs dans le InMemoryUserRepository.
// Cela permet de garder une séparation claire entre la logique de stockage (InMemoryDatabase) et la logique métier (repositories) tout en assurant que les données de test sont cohérentes.
// Si on initialisait les données directement dans la base de données, cela créerait une dépendance directe entre la base de données et les entités spécifiques (User, Post, etc.), ce qui rendrait le code plus rigide et moins modulaire.
// En résumé, la base de données est un simple conteneur de données, tandis que les repositories sont responsables de la logique d'initialisation et de gestion des données spécifiques à chaque type d'entité.


