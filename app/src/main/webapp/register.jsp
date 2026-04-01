<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>MINIATURE</title>
    <link rel="stylesheet" type="text/css" href="./style/register.css">
</head>
<body>
    <h1>Créer un compte</h1>
    <form action="register" method="post">
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>
        
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Register">
    </form>
</body>
</html>

// Ce fichier JSP (register.jsp) est utilisé pour afficher la page d'inscription de l'application MINIATURE.        
// Il contient un formulaire d'inscription qui permet aux utilisateurs de saisir leur nom d'utilisateur, leur adresse e-mail et leur mot de passe pour créer un compte dans l'application.
// Le formulaire envoie une requête POST à l'URL "register" pour traiter l'inscription.
// En résumé, ce fichier est essentiel pour fournir une interface utilisateur permettant aux nouveaux utilisateurs de s'inscrire à l'application MINIATURE en fournissant les informations nécessaires pour créer un compte.    
