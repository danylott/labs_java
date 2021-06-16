<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<div class="w3-content">
    <form style="margin: auto" class="modal-content animate" action="login" method="post">
        <header class="w3-container w3-center w3-teal">
            <h1>Login</h1>
        </header>
        <div>
            <label for="username"><b>Username</b></label>
            <input class="w3-input" type="text" id="username" name="username" placeholder="Enter Username" required>

            <label for="password"><b>Password</b></label>
            <input class="w3-input" type="password" id="password" name="password" placeholder="Enter Password" required>
            <input class="w3-button w3-section w3-teal w3-ripple" type="submit" value="Login">
            <label>
                <input type="checkbox" checked="checked" name="remember"> Remember me
            </label>

        </div>

    </form>
    <div id="invalid" class="w3-red w3-bar">
        <%Cookie[] cookies = request.getCookies();
            if (cookies==null || cookies.length == 0) return;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("success")) {
                    if (cookie.getValue().equals("no")) { %>

                        invalid login or password

                        <% cookie.setValue("");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        %>
    </div>
</div>

<script>


</script>

</body>
</html>