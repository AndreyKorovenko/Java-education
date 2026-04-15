<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>${lesson.title}</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>${lesson.title}</h1>
    <div class="content">
        <pre>${lesson.content}</pre>
    </div>

    <hr>
    <a href="/lessons"> Назад к списку уроков</a><br>
    <a href="/">На главную</a>
</body>
</html>