package org.example.presentation.controllers;

import java.io.IOException;

import org.example.application.services.Authservice;
import org.example.domain.entity.User;
import org.example.domain.repositories.UserRepository;
import org.example.infrastructure.RepositoryAdapter;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

    private Authservice authService;

    @Override
    public void init() {
        UserRepository repo = RepositoryAdapter.getUserRepository(getServletContext());
        this.authService = new Authservice(repo);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = authService.login(username, password);

        if (user != null) {
            req.getSession().setAttribute("currentUser", user);
            resp.sendRedirect(req.getContextPath() + "/feed");
        } else {
            resp.sendRedirect(req.getContextPath() + "/register");
        }
    }
}