package ru.sfedu.mmcs.korovenko.dao;

import ru.sfedu.mmcs.korovenko.model.Lesson;

import java.util.List;
import java.util.ArrayList;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class LessonDAOImpl implements LessonDAO {
// implements - все методы интерфейса нужно реализовать в этом классе

    //_______________________________КОНСТАНТЫ__________________________________________________

    private static final String SQL_FIND_BY_ID = "SELECT * FROM lessons WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM lessons";
    private static final String SQL_UPDATE_INFO = "UPDATE lessons SET title = ?, content = ? WHERE id = ?";
    private static final String SQL_INSERT_INFO = "INSERT INTO lessons (title, content) VALUES (?, ?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM lessons WHERE id = ?";
    private static final String SQL_FIND_LAST_ID = "SELECT last_insert_rowid()";
    private static final String SQL_DELETE_ALL = "DELETE FROM lessons";

    //_____________________________ВСПОМОГАТЕЛЬНЫЕ_ФУНКЦИИ___________________________________________________

    private final String dbUrl;

    // Конструктор по умолчанию для ссылки
    public LessonDAOImpl() {
        this("jdbc:sqlite:lessons.db");
    }

    // Конструктор с параметром для ссылки
    public LessonDAOImpl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    //создание переменной класса Lesson
    private Lesson makeLesson(ResultSet rs) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getInt("id"));
        lesson.setTitle(rs.getString("title"));
        lesson.setContent(rs.getString("content"));
        return lesson;
    }

    //проверка корректности id
    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Некорректный ID: " + id);
        }
    }

    //проверка корректности lesson
    private void validateLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null");
        }
    }

    //________________________________ФУНКЦИИ_________________________________________________

    @Override
    public Lesson findById(int id) {
        validateId(id);  // выбросит исключение, если ID некорректный

        try (Connection conn = DriverManager.getConnection(this.dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_BY_ID)){ //передача запроса в БД

            // 1. Подготовка шаблона
            pstmt.setInt(1, id); //передача значения и подстановка его вместо ?
            ResultSet rs = pstmt.executeQuery(); // выполнение запроса (результат: урок с id=1)

            if (rs.next()) {
                return makeLesson(rs);
            } else {
                return null;
            }

        } catch (SQLException err1) {
            err1.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Lesson> findAll() {
        List<Lesson> lessons = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(this.dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_ALL)) { //передача запроса в БД

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lessons.add(makeLesson(rs));
            }
        } catch (SQLException err1) {
            err1.printStackTrace();
        }
        return lessons;
    }

    @Override
    public void save(Lesson lesson) {
        validateLesson(lesson); // выбросит исключение, если lesson == null

        if (lesson.getId() > 0 && findById(lesson.getId()) != null) {
            // если урок существует - обновляем
            try (Connection conn = DriverManager.getConnection(this.dbUrl);
                 PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_INFO)) {

                pstmt.setString(1, lesson.getTitle());
                pstmt.setString(2, lesson.getContent());
                pstmt.setInt(3, lesson.getId());

                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // если нет - создаём
            try (Connection conn = DriverManager.getConnection(this.dbUrl);
                 PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT_INFO)) {

                pstmt.setString(1, lesson.getTitle());
                pstmt.setString(2, lesson.getContent());

                pstmt.executeUpdate();

                // ПОЛУЧАЕМ ID ЧЕРЕЗ last_insert_rowid()
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL_FIND_LAST_ID);
                if (rs.next()) {
                    lesson.setId(rs.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        validateId(id);  // выбросит исключение, если ID некорректный

        try (Connection conn = DriverManager.getConnection(this.dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_BY_ID)) {

            pstmt.setInt(1, id); //передача значения и подстановка его вместо ?
            int rowsAffected = pstmt.executeUpdate(); // выполнение запроса (результат: урок с id=1)

            if (rowsAffected > 0) {
                System.out.println("Урок {id: " + id + "} успешно удален");
            } else {
                System.out.println("Урок {id: " + id + "} не найден");
            }

        } catch (SQLException err1) {
            System.err.println("Ошибка при удалении урока {id: " + id + "}");
            err1.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try (Connection conn = DriverManager.getConnection(this.dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_ALL)) {

            int rowsAffected = pstmt.executeUpdate(); // выполнение запроса (результат: урок с id=1)

            if (rowsAffected > 0) {
                System.out.println("Уроки успешно удалены");
            } else {
                System.out.println("Не удалось выполнить операцию");
            }

        } catch (SQLException err1) {
            System.err.println("Ошибка при удалении уроков");
            err1.printStackTrace();
        }
    }

}
