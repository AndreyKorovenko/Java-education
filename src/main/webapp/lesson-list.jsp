<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Список уроков по Java</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Список уроков по Java</h1>

    <c:if test="${empty lessons}">
        <p>Уроки не найдены</p>
    </c:if>

    <c:forEach var="lesson" items="${lessons}">
        <p>
            <a href="/lesson?id=${lesson.id}">${lesson.title}</a>
        </p>
    </c:forEach>

    <hr>
    <a href="/">На главную</a>
</body>
</html>