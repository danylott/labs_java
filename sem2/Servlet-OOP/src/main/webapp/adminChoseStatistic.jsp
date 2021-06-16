
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>chose period</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<a class="w3-button w3-teal" href="http://localhost:8000/Servlet_OOP_war_exploded/adminPage"> go to admin page </a> <br>
<form class="w3-content" action="statistics" method="get">
    <h2>Select the period for which you want to get a report</h2>
    <label for="startDay">startDay:</label>
    <input class="w3-input" type="date" id="startDay" name="startDay" required><br>
    <label for="endDay">endDay:</label>
    <input class="w3-input" type="date" id="endDay" name="endDay" required>

    <input class="w3-button w3-teal w3-section" type="submit" value="Show">
</form>
</body>
</html>
