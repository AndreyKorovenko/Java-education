package ru.sfedu.mmcs.korovenko.dao;

import java.sql.*;

public class DatabaseTest {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:lessons.db";

        // SQL для создания таблицы уроков
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS lessons (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                content TEXT
            );
            """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            System.out.println("Подключено к SQLite");

            // Создаем таблицу
            stmt.execute(createTableSQL);
            System.out.println("Таблица lessons создана или уже существует");

            // Вставим тестовый урок
            String insertSQL = "INSERT INTO lessons (title, content) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, "11111111111111");
                pstmt.setString(2, "2222222222222222");
                pstmt.executeUpdate();
                System.out.println("Тестовый урок добавлен");
            }

            // Прочитаем что получилось
            String selectSQL = "SELECT * FROM lessons";
            try (Statement selectStmt = conn.createStatement();
                 ResultSet rs = selectStmt.executeQuery(selectSQL)) {

                while (rs.next()) {
                    System.out.println("Урок " + rs.getInt("id") +
                            ": " + rs.getString("title"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}