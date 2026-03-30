package org.example.infrastructure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.domain.model.Comment;
import org.example.domain.model.Post;
import org.example.domain.repositories.PostRepository;

public class InMemoryPostRepository implements PostRepository {
    private List<Post> posts = new ArrayList<>();

      public InMemoryPostRepository() {
            posts.add(new Comment("Test post ", 1, 1, LocalDateTime.of(2023, 6, 26, 10, 0), "User1", false));
            posts.add(new Comment("Admin post", 2, 2, LocalDateTime.of(2024, 6, 26, 12, 0), "User2", false));
            posts.add(new Comment("Aujourd’hui j’ai commencé à travailler sur mon mini réseau social en Java avec Servlets et JSP. "
                    + "C’est intéressant de voir comment fonctionne l’architecture MVC côté serveur. "
                    + "J’ai réussi à gérer l’authentification des utilisateurs et maintenant je travaille sur le feed de posts. "
                    + "Prochaine étape : ajouter les likes et les commentaires !",
                    2, 3, LocalDateTime.of(2025, 6, 26, 14, 0), "Admin", false));
            posts.add(new Comment("Je viens de terminer la fonctionnalité de feed pour mon mini réseau social en Java. ", 4, 8,
                    LocalDateTime.of(2024, 6, 26, 16, 0), "Fabien", false));
            posts.add(new Comment(
                    "C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ",
                    3, 4, LocalDateTime.of(2024, 6, 26, 18, 0), "Kevin", false));
            posts.add(new Comment(
                    "Je viens de mettre en place la fonctionnalité de follow pour mon mini réseau social. Maintenant, les utilisateurs peuvent suivre d’autres utilisateurs et voir leurs posts dans un feed personnalisé. C’est génial de voir comment tout cela prend forme ! Prochaine étape : ajouter la possibilité de liker les posts et de commenter. ",
                    5, 5, LocalDateTime.of(2024, 6, 26, 20, 0), "Eric", false));
            posts.add(new Comment(
                    "Je viens de terminer la fonctionnalité de feed pour mon mini réseau social en Java. C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ",
                    3, 6, LocalDateTime.of(2024, 6, 26, 22, 0), "Kevin", false));
            posts.add(new Comment(
                    "C’est super de voir les posts s’afficher dans le feed ! Prochaine étape : permettre aux utilisateurs de suivre d’autres utilisateurs pour personnaliser leur feed. ",
                    4, 7, LocalDateTime.of(2024, 6, 27, 0, 0), "Fabien", false));

    }

    @Override
    public List<Post> findAll() {
        return posts;
    }

    @Override
    public void save(Post post) {
        posts.add(post);
    }
}
