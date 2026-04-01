package org.example.application.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.example.domain.entity.Comment;
import org.example.domain.entity.Post;
import org.example.domain.entity.User;

public class PostService {
    private List<Post> posts;

    public PostService(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPostsForUser(User user, List<User> allUsers) {
        for (Post post : posts) {
            User author = allUsers.stream()
                                  .filter(u -> u.getUserId() == post.getUserId())
                                  .findFirst()
                                  .orElse(null);
            if (author != null) post.setAuthorName(author.getUsername());

            post.setFollowing(user.getFollowing().contains(post.getUserId()));
            post.setLiked(user.getLiked().contains(post.getId()));
        }
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return posts;
    }

    public void addPost(String content, User author) {
        if (content != null && !content.trim().isEmpty()) {
            posts.add(new Comment(content, author.getUserId(), posts.size() + 1,
                                  LocalDateTime.now(), author.getUsername(), false));
        }
    }
}
// Ce service de gestion des posts fournit des méthodes pour récupérer les posts d'un utilisateur et ajouter de nouveaux posts.
// La méthode getPostsForUser enrichit les posts avec des informations sur l'auteur, si l'utilisateur suit l'auteur, et si l'utilisateur a aimé le post. Les posts sont ensuite triés par date de création.
// La méthode addPost permet d'ajouter un nouveau post à la liste des posts, en vérifiant que le contenu n'est pas vide. Le nouveau post est créé avec les informations de l'auteur et la date de création actuelle.    
// Ce service peut être utilisé dans les servlets pour gérer les opérations liées aux posts, telles que l'affichage des posts sur la page d'accueil ou la création de nouveaux posts par les utilisateurs.  
// Exemple d'utilisation dans un servlet :
/*              
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String content = request.getParameter("content");
    User author = (User) request.getSession().getAttribute("currentUser");

    PostService postService = new PostService(RepositoryAdapter.getPostRepository(getServletContext()).findAll());
    postService.addPost(content, author);

    response.sendRedirect("home.jsp");
}
*/
// Ce code dans le servlet montre comment utiliser le service de gestion des posts pour ajouter un nouveau post créé par l'utilisateur actuellement connecté. Après l'ajout du post, l'utilisateur est redirigé vers la page d'accueil pour voir le nouveau post.   
// En résumé, ce service de gestion des posts est un composant clé pour gérer les opérations liées aux posts dans l'application, en fournissant des fonctionnalités pour récupérer et ajouter des posts.    
