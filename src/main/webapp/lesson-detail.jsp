<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>${lesson.title}</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    <script>hljs.highlightAll();</script>
</head>
<body>
    <div class="container">
        <h1>${lesson.title}</h1>
        <div class="content">
            ${lessonContentHtml}
        </div>

        <hr>
        <a href="/lessons"> Назад к списку уроков</a><br>
        <a href="/">На главную</a><br>
        <a href="/edit-lesson?id=${lesson.id}">Редактировать урок</a><br>
        <a href="/delete-lesson?id=${lesson.id}" onclick="return confirm('Вы уверены, что хотите удалить этот урок?');">Удалить урок</a>
    </div>
</body>
</html>