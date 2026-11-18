package ru.sfedu.mmcs.korovenko.web;

// ... импорты (не забудь импортировать LessonDAO, LessonDAOImpl, HttpServlet и т.д.)

import ru.sfedu.mmcs.korovenko.dao.LessonDAO;
import ru.sfedu.mmcs.korovenko.dao.LessonDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-lesson")
public class DeleteLessonServlet extends HttpServlet {

    private LessonDAO lessonDAO = new LessonDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("/lessons");
            return;
        }

        try{
            //преобр строку в число
            int id = Integer.parseInt(idParam);

            //вызов метода удаления из DAO
            lessonDAO.delete(id);

            //перенаправление обратно на список уроков
            response.sendRedirect("/lessons");

        } catch (NumberFormatException e) {
            //если id не явл числом => отправляем на список
            response.sendRedirect("/lessons");
        }
    }
}
