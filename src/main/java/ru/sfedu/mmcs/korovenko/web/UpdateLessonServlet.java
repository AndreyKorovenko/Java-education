package ru.sfedu.mmcs.korovenko.web;

import ru.sfedu.mmcs.korovenko.dao.LessonDAO;
import ru.sfedu.mmcs.korovenko.dao.LessonDAOImpl;
import ru.sfedu.mmcs.korovenko.model.Lesson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-lesson")
public class UpdateLessonServlet extends HttpServlet {

    private LessonDAO lessonDAO = new LessonDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        String title = request.getParameter("title");
        String content = request.getParameter("content");

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("/lessons");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            Lesson lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setContent(content);
            lesson.setId(id);

            lessonDAO.save(lesson);

            response.sendRedirect("/lesson?id=" + id);

        } catch (NumberFormatException e) {
            response.sendRedirect("/lessons");
        }
    }
}
