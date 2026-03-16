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