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
