package ru.sfedu.mmcs.korovenko.web;

import ru.sfedu.mmcs.korovenko.model.Lesson;
import ru.sfedu.mmcs.korovenko.dao.LessonDAO;
import ru.sfedu.mmcs.korovenko.dao.LessonDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/lessons") // метка для Tomcat, чтобы вызывал этот класс
public class LessonListServlet extends HttpServlet { //благодаря наследованию класс становится сервлетом:
                                                     // запрос (request), ответ (response), обрабатывает GET, POST и др.
    private LessonDAO lessonDAO = new LessonDAOImpl(); //экземпляр DAO (работа с БД)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { // при ошибке программа не упадёт: выведется страница ошибки

        List<Lesson> lessons = lessonDAO.findAll(); //получение списка всех уроков 
        request.setAttribute("lessons", lessons); // кладём в request

        request.getRequestDispatcher("/lesson-list.jsp").forward(request, response); //находит файл в ""; Передаёт туда request и запускает эту страницу
    }
}