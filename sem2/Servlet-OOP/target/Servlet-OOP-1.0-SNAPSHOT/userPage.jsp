<%@ page import="com.example.Servlet_OOP.Card" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>ActionPage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="styles.css">
</head>
<%
    int type = 0;//-1=payment 0=card; 1=blockCard; 2=replenishment
    if (request.getAttribute("type") instanceof Integer) {
        type = (Integer) request.getAttribute("type");
    }
    String typeStr = "";
    switch (type) {
        case -1:
            typeStr = "payment";
            break;
        case 0:
            typeStr = "cards";
            break;
        case 1:
            typeStr = "blockCard";
            break;
        case 2:
            typeStr = "replenishment";
            break;
        default:
            break;
    }

    int cardNo = 0;

    Cookie[] cookies = request.getCookies();
    if (cookies.length != 0) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("cardsId")) {
                cardNo = Integer.parseInt(cookie.getValue());
            }
        }
    }
%>
<body>
<div class="w3-bar w3-border w3-teal">
    <% if (type != 0) {%>
            <a class="w3-bar-item w3-button" href="http://localhost:8000/Servlet_OOP_war_exploded/userPage?type=cards"> go to cards </a>
    <% } if (type != 1) {%>
            <a class="w3-bar-item w3-button" href="http://localhost:8000/Servlet_OOP_war_exploded/userPage?type=blockCard"> go to block cards  </a>
    <% }if (type != -1) {%>
            <a class="w3-bar-item w3-button" href="http://localhost:8000/Servlet_OOP_war_exploded/userPage?type=payment"> go to payment  </a>
    <% }if (type != 2) {%>
           <a class="w3-bar-item w3-button" href="http://localhost:8000/Servlet_OOP_war_exploded/userPage?type=replenishment"> go to replenishment  </a>
    <%}%>
    <a class="w3-bar-item w3-button w3-right" id="logout" onclick="function myFunction() {
    document.cookie='SESSION= ; expires = Thu, 01 Jan 1970 00:00:00 GMT';
    document.cookie='cardsId= ; expires = Thu, 01 Jan 1970 00:00:00 GMT';
    location.reload();
}myFunction()">
        log out
    </a>
</div>

<div class="w3-content">
    <form style="margin:auto" action="userPage?type=<%=typeStr%>" method="post">
        <!-- select card-->
        <%
            List<Card> cards = null;


            if (request.getAttribute("cardList") instanceof List) {
                if (((List) request.getAttribute("cardList")).size() > 0 &&
                        (((List) request.getAttribute("cardList")).get(0) instanceof Card)) {
                    cards = (List<Card>) request.getAttribute("cardList");

                    if (type != 0) {/*no createCard*/ %>

        <h2>Select card:</h2>
        <label for="cards"></label>
        <select class="w3-select" id="cards" name="cards">

            <% for (int i = 0; i < cards.size(); i++) {%>
            <option value= <%=type == 1 ? cards.get(i).getId() : i%> > <%=cards.get(i).toString()%> </option>
            <%}%>
        </select> <br>
        <%}

        }
        }%>
        <br>

        <% if (type == 2) {%>
        <label for="quantity">Sum</label>
        <input class="w3-input" type="number" id="quantity" name="sum" min=1 max=10000 required><br>
        <label for="comment">Comment</label>
        <input class="w3-input" type="text" id="comment" name="comment" required><br>
        <input class="w3-button w3-teal" type="submit" id="btnSubmit" value="replenish">
        <%} else if (type == -1) { %>
        <label for="replenishment">Sum</label>
        <input class="w3-input" type="number" id="replenishment" name="sum" min=1 max=10000 required><br>
        <label for="comment">Comment</label>
        <input class="w3-input" type="text" id="comment" name="comment" required><br>
        <input class="w3-button w3-teal" type="submit"  id="btnSubmit" value="pay">
        <%} else if (type == 0) { %>
        <h2>Add Card:</h2>
        <label for="cardName">cardName</label>
        <input class="w3-input" type="text" id="cardName" name="cardName" required><br>
        <input class="w3-button w3-teal" type="submit" id="btnSubmit" value="add">
        <%} else if (type == 1) {%>
        <input class="w3-button w3-teal" type="submit" id="btnSubmit" value="block">
        <%} %>
    </form>

    <div id="result">
        <%if ((type == 0 || type == 1) && cards != null && !cards.isEmpty()) {/*block or add*/ %>
        <h2>Cards:</h2>
        <table class="w3-table-all">
            <tr>
                <th>CardName</th> <th>CurrentMoney</th> <th>Blocked</th> </tr>
            <% for (Card card : cards) { %>
            <tr>
                <th> <%=card.getName()%>   </th>
                <th> <%=card.getBalance()%></th>
                <th> <%=card.isBlocked()%> </th>
            </tr>
            <%}%>
        </table>
        <%}%>
    </div>
</div>


<script>
    let blocked = [];
    <%if (type!=0 ) {%>
    let pays = [];
    let comments = [];
     <%if (cards!=null){%>
         window.onload = function () {
             <% for(int i = 0; i <cards.size() ; i++) { %>
                 blocked.push(<%=cards.get(i).isBlocked()%>);
                 <%if(type!=1){%>
                     let pays<%=i%>=[];
                     let comments<%=i%>=[];
                     <% for(int j = 0; j < cards.get(i).getPayments().size(); j++) {%>
                         pays<%=i%>.push(<%=cards.get(i).getPayments().get(j).getPay()%>);
                         comments<%=i%>.push("<%=cards.get(i).getPayments().get(j).getComment()%>");
                     <%}%>
                     pays.push(pays<%=i%>);
                     comments.push(comments<%=i%>);
                 <%}%>

             <%}%>
             document.getElementById("cards").value=<%=cardNo %>;
             change();
         };
     <%}%>
    document.getElementById("cards").onchange = function () {change()};

     function change() {
                let i = document.getElementById("cards").value;
                document.cookie = 'cardsId=' + i;
                document.getElementById("btnSubmit").disabled=blocked[i];

                <% if (type!=1) { %>
                    let el = document.getElementById('result');
                    let str ="<h2>Transactions:</h2><table class='w3-table-all'><tr><th>Pay</th><th>Comment</th></tr>";
                    for (let a = 0; a < pays[i].length; a++) {
                        if (pays[i][a] <%= ((type==2)? ">0":"<0")%>)
                        {
                            str += "<tr><th>" + pays[i][a] + "</th>" +
                            "<th>" + comments[i][a] + "</th></tr>";
                        }
                    }
                    str +="</table>";
                    el.innerHTML = str;
         <%}%>
        }
     <%}%>


</script>
</body>
</html>
