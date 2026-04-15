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

@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {

    private LessonDAO lessonDAO = new LessonDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("/lessons");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Lesson lesson = lessonDAO.findById(id);

            if (lesson == null) {
                response.sendRedirect("/lessons");
                return;
            }

            request.setAttribute("lesson", lesson);
            request.getRequestDispatcher("/lesson-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("/lessons");
        }
    }
}