package org.example.presentation.controllers;

import java.io.IOException;

import org.example.application.services.RegisterService;
import org.example.domain.repositories.UserRepository;
import org.example.infrastructure.RepositoryAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

    private RegisterService registerService;

    @Override
    public void init() {
        UserRepository repo = RepositoryAdapter.getUserRepository(getServletContext());

        this.registerService = new RegisterService(repo);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try {
            registerService.register(username, password, email);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/register?error=1");
        }
    }
}