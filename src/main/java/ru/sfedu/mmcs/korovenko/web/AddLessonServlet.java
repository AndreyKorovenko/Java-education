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

// Этот сервлет будет принимать POST-запросы по адресу /add-lesson
@WebServlet("/add-lesson")
public class AddLessonServlet extends HttpServlet {

    private LessonDAO lessonDAO = new LessonDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        Lesson newLesson = new Lesson();
        newLesson.setTitle(title);
        newLesson.setContent(content);

        lessonDAO.save(newLesson);

        response.sendRedirect("/lessons");
    }
}