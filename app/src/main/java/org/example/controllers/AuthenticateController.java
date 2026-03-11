package org.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.models.User;
import org.jspecify.annotations.NonNull;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthenticateController", urlPatterns = { "/register", "/login" })
public class AuthenticateController extends HttpServlet {
    private List<@NonNull User> users = new ArrayList<>();

    public void init() {
        users.add(new User("test", "test123", "test@example.com", 1));
        users.add(new User("admin", "admin123", "admin@example.com", 2));

        getServletContext().setAttribute("users", users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userList", users);

        String path = req.getServletPath();

        if (path.equals("/register")) {
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();

        if (path.equals("/register")) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");

            if (username == null || password == null || email == null) {
                resp.sendRedirect(req.getContextPath() + "/register");
                return;
            }

            User newUser = new User(username, password, email, users.size() + 1);
            users.add(newUser);

            getServletContext().setAttribute("users", users);

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (path.equals("/login")) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {

                    req.getSession().setAttribute("currentUser", user);
                    resp.sendRedirect(req.getContextPath() + "/feed");
                    return;
                }
            }

            resp.sendRedirect(req.getContextPath() + "/login?error=1");
        }
    }

}
