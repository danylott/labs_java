package com.example.Servlet_OOP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/userPage")
public class UserPageServlet extends HttpServlet {


    public static int getId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie auth = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("SESSION"))
                auth = cookie;
        }
        if (auth == null || auth.getValue() == null) {
            resp.sendRedirect("login");
            return -1;
        }
        String decrypted = auth.getValue();

        if (!decrypted.startsWith("HelloWorld")) {
            auth.setMaxAge(0);
            auth.setValue("");

            resp.addCookie(auth);
            resp.sendRedirect("login");
            return -1;
        }

        return Integer.parseInt(decrypted.substring(10));
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\ni m here doGet \nuserPage\n");
        String typeParameter = req.getParameter("type");
        System.out.println(typeParameter);
        if (typeParameter == null || !(typeParameter.equals("payment") || typeParameter.equals("cards") || typeParameter.equals("blockCard") || typeParameter.equals("replenishment"))) {
            req.setAttribute("type", 0);
            resp.sendRedirect("userPage?type=cards");
            return;
        } else if (req.getParameter("type").equals("payment")) {
            req.setAttribute("type", -1);
        } else if (req.getParameter("type").equals("cards")) {
            req.setAttribute("type", 0);
        } else if (req.getParameter("type").equals("blockCard")) {
            req.setAttribute("type", 1);
        } else if (req.getParameter("type").equals("replenishment")) {
            req.setAttribute("type", 2);
        }


        int id = getId(req, resp);
        if (id == -1) return;
        List<Card> cards = DBConnection.getCards(id);
        req.setAttribute("cardList", cards);
        getServletContext().getRequestDispatcher("/userPage.jsp").forward(req, resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("\ni m here doPost\nuserPage\n");

        String type = req.getParameter("type");
        if (type == null)
            return;

        int userId = UserPageServlet.getId(req, resp);
        if (userId == -1) return;


        if (!type.equals("cards") && !type.equals("blockCard")) {
            System.out.println(req.getParameter("cards"));
            int cardNo = Integer.parseInt(req.getParameter("cards"));
            System.out.println(cardNo);
            int sum = Integer.parseInt(req.getParameter("sum"));
            String comment = req.getParameter("comment");
            System.out.println(sum);
            System.out.println(comment);

            if (type.equals("replenishment")) {
                DBConnection.addPayment(userId, cardNo, sum, comment);
                resp.sendRedirect("userPage?type=replenishment");
            } else if (type.equals("payment")) {
                DBConnection.addPayment(userId, cardNo, -sum, comment);
                resp.sendRedirect("userPage?type=payment");
            }

        } else if (type.equals("cards")) {
            String cardName = req.getParameter("cardName");
            System.out.println(cardName);
            DBConnection.addCard(userId, cardName);
            resp.sendRedirect("userPage?type=cardName");
        } else {
            System.out.println("IN BLOCK CARD!");
            System.out.println(req.getParameter("cards"));
            int cardNo = Integer.parseInt(req.getParameter("cards"));
            System.out.println(cardNo);
            DBConnection.setBlocked(userId, cardNo, true);
            resp.sendRedirect("userPage?type=blockCard");
        }

    }

}

