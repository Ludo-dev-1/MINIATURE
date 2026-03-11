package org.example.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.models.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feed")
public class FeedController extends HttpServlet {

    private List<Post> posts = new ArrayList<>(List.of(
            new Post("Test post", 1, 1, LocalDate.now()),
            new Post("Admin post", 2, 2, LocalDate.now())));

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("posts", posts);

        req.getRequestDispatcher("/feed.jsp").forward(req, resp);
    }
}
