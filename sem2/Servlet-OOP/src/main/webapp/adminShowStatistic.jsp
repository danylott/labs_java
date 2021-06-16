
<%@ page import="com.example.Servlet_OOP.Card" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    if (request.getAttribute("cards") == null) {
        response.sendRedirect("period");
        return;
    }
    Card[] cards = (Card[]) request.getAttribute("cards");

%>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<a class="w3-button w3-teal" href="http://localhost:8000/Servlet_OOP_war_exploded/adminPage"> go to admin page </a> <br>

<div class="w3-content">
    <h2>Statistics:</h2>
    <table class="w3-content w3-table-all">
        <tr>
            <td>CARD
            </td>

            <td>PAYMENT
            </td>
            <td>REPLENISHMENT
            </td>
        </tr>


        <%
            if (cards != null && cards.length != 0)
                for (Card card : cards) {
        %>
        <tr>
            <td><%=card.getName()%>
            </td>

            <td><%=card.getAccount().getPayments().get(0).getPay()%>
            </td>
            <td><%=card.getAccount().getPayments().get(1).getPay()%>
            </td>
        </tr>
        <%}%>

    </table>
</div>

</body>
</html>
