package com.example.Servlet_OOP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminPage")
public class AdminPageServlet extends HttpServlet {


    public static boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie auth = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("SESSION"))
                auth = cookie;
        }
        if (auth == null || auth.getValue() == null) {
            resp.sendRedirect("login");
            return false;
        }
        String decrypted = auth.getValue();
        /*byte[] encrypted = auth.getValue().getBytes();
        String key = "Bar12345Bar12345"; // 128 bit key
        // Create key and cipher
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decrypted = new String(cipher.doFinal(encrypted));
            System.out.println(decrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }*/

        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\ni m here doGet \nadminPage\n");
//        if (!checkAdmin(req, resp)) return;

        List<User> users = DBConnection.getUsers();
        req.setAttribute("userList", users);
        getServletContext().getRequestDispatcher("/adminPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\ni m here doPost \nadminPage\n");
        if (!checkAdmin(req, resp)) return;

        int userId = Integer.parseInt(req.getParameter("users"));
        int cardNo = Integer.parseInt(req.getParameter("cards"));
        List<User> users = DBConnection.getUsers();

        DBConnection.setBlocked(userId, cardNo, false);
        resp.sendRedirect("adminPage");
    }


}
