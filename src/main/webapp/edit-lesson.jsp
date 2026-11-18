<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Редактировать урок</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <h1>Редактировать урок</h1>

    <form action="/update-lesson" method="POST">
        <input type="hidden" name="id" value="${lesson.id}">

        <div>
            <label for="title">Название урока:</label><br>
            <input type="text" id="title" name="title" value="${lesson.title}" required size="50">
        </div>
        <br>
        <div>
            <label for="content">Содержание урока:</label><br>
            <textarea id="content" name="content" rows="15" cols="70" required>${lesson.content}</textarea>
        </div>
        <br>
        <button type="submit">Сохранить изменения</button>
        <a href="/lessons">Отмена</a>
    </form>
</body>
</html>