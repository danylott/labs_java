package com.example.Servlet_OOP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req);
        System.out.println(resp);
        getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\n\n\ni m here doPost\n\n\n");
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User(firstName, secondName, username, password);
        boolean correct = DBConnection.addUser(user);
        if (correct) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        //mb some check

        //resp.addCookie(new Cookie("success", "no"));
        //resp.sendRedirect("index.jsp");
    }

}