
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registration</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<a class="w3-button w3-teal" href="http://localhost:8000/Servlet_OOP_war_exploded/adminPage"> go to admin page </a> <br>
<div id="id01" style="margin: auto">
    <form class="w3-content" action="registration" method="post">
        <h2>Add new User</h2>
        <div>
            <%--@declare id="username"--%><%--@declare id="psw"--%> <%--@declare id="firstName"--%><%--@declare id="secondname"--%>

            <label for="firstName"><b>First name</b></label>
            <input class="w3-input" type="text" name="firstName" placeholder="Enter first name" required><br>


            <label for="secondName"><b>Second name</b></label>
            <input class="w3-input" type="text" name="secondName" placeholder="Enter second name" required><br>

            <label for="username"><b>Username</b></label>
            <input class="w3-input" type="text" name="username" placeholder="Enter Username" required><br>

            <label for="psw"><b>Password</b></label>
            <input class="w3-input" type="password" name="password" placeholder="Enter Password" required><br>

        </div>

        <input class="w3-button w3-section w3-teal" type="submit" value="Registration">

    </form>
</div>

<script>
    // Get the modal
    var modal = document.getElementById('id01');

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>
