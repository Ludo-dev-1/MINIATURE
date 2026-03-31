package org.example.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.example.domain.database.Database;

public class InMemoryDatabase implements Database {

    private static final InMemoryDatabase INSTANCE = new InMemoryDatabase();

    private final Map<Class<?>, List<Object>> storage = new HashMap<>();

    private InMemoryDatabase() {
    }

    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized <T> void add(Class<T> type, T item) {
        storage.computeIfAbsent(type, ignored -> new ArrayList<>()).add(item);
    }

    @Override
    public synchronized <T> boolean remove(Class<T> type, T item) {
        List<Object> bucket = storage.get(type);
        if (bucket == null) {
            return false;
        }
        return bucket.remove(item);
    }

    @Override
    public synchronized <T> boolean removeIf(Class<T> type, Predicate<T> predicate) {
        List<Object> bucket = storage.get(type);
        if (bucket == null || bucket.isEmpty()) {
            return false;
        }
        return bucket.removeIf(entry -> predicate.test(type.cast(entry)));
    }

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

    @Override
    public synchronized <T> int size(Class<T> type) {
        List<Object> bucket = storage.get(type);
        return bucket == null ? 0 : bucket.size();
    }

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


