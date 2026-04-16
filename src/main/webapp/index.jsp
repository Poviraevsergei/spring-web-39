<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h2>Форма регистрации</h2>
<form action="/security/registration" method="post">
    <label for="firstName">Имя:</label><br>
    <input type="text" id="firstName" name="firstName" required><br><br>

    <label for="lastName">Фамилия:</label><br>
    <input type="text" id="lastName" name="lastName" required><br><br>

    <label for="age">Возраст:</label><br>
    <input type="number" id="age" name="age" required><br><br>

    <label for="email">Email:</label><br>
    <input type="text" id="email" name="email" required><br><br>

    <label for="username">Имя пользователя:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Пароль:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>

