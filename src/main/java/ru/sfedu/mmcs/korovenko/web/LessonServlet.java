package ru.sfedu.mmcs.korovenko.web;

import org.commonmark.Extension;
import org.commonmark.renderer.html.HtmlRenderer;
import ru.sfedu.mmcs.korovenko.model.Lesson;
import ru.sfedu.mmcs.korovenko.dao.LessonDAO;
import ru.sfedu.mmcs.korovenko.dao.LessonDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.commonmark.parser.Parser;
import org.commonmark.ext.gfm.tables.TablesExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

            //Markdown в HTML
            List<Extension> extensions = Arrays.asList(TablesExtension.create());

            Parser parser = Parser.builder()
                    .extensions(extensions)
                    .build();

            HtmlRenderer renderer = HtmlRenderer.builder()
                    .extensions(extensions)
                    .build();
            String html = renderer.render(parser.parse(lesson.getContent()));

            //передача в JSP оригинального объекта и готового HTML
            request.setAttribute("lesson", lesson);
            request.setAttribute("lessonContentHtml", html);

            request.getRequestDispatcher("/lesson-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("/lessons");
        }
    }
}