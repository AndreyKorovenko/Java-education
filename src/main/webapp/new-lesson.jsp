<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Добавить урок</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <h1>Добавить новый урок</h1>

    <form action="/add-lesson" method="POST">
        <div>
            <label for="title">Название урока:</label><br>
            <input type="text" id="title" name="title" required size="50">
        </div>
        <br>
        <div>
            <label for="content">Содержание урока:</label><br>
            <textarea id="content" name="content" rows="15" cols="70" required></textarea>
        </div>
        <br>
        <button type="submit">Сохранить урок</button>
        <a href="/lessons">Отмена</a>
    </form>
</body>
</html>