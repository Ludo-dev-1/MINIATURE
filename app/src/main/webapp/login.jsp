<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>Login - MINIATURE</title>
        <link rel="stylesheet" type="text/css" href="./style/login.css?v=2">
    </head>

    <body>
        <h1>Se connecter</h1>

        <form action="login" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <input type="submit" value="Login" class="auth-action">
        </form>

        <a href="register" class="auth-action registerbtn">S'inscrire</a>

    </body>

    </html>

    // Ce fichier JSP (login.jsp) est utilisé pour afficher la page de connexion de l'application MINIATURE.
    // Il contient un formulaire de connexion qui permet aux utilisateurs de saisir leur nom d'utilisateur et leur mot de passe pour se connecter à l'application.
    // Le formulaire envoie une requête POST à l'URL "login" pour traiter la connexion.
    // Le fichier inclut également un lien vers la page d'inscription pour les utilisateurs qui n'ont pas encore de compte.
    // En résumé, ce fichier est essentiel pour fournir une interface utilisateur permettant aux utilisateurs de se connecter à l'application MINIATURE et d'accéder à leurs fonctionnalités.   
    