package com.example.Servlet_OOP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/statistics")
public class AdminShowStatisticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startDay = req.getParameter("startDay");
        String endDay = req.getParameter("endDay");
        List<Card> cards = DBConnection.getCards();
        for (Card card : cards) {
            card.getAccount().setPayments(DBConnection.getPayments(card.getId(), startDay, endDay));
        }

        req.setAttribute("cards", cards.toArray(new Card[0]));
        getServletContext().getRequestDispatcher("/adminShowStatistic.jsp").forward(req, resp);
    }
}