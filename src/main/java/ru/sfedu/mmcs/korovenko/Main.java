package ru.sfedu.mmcs.korovenko;

import ru.sfedu.mmcs.korovenko.dao.LessonDAO;
import ru.sfedu.mmcs.korovenko.dao.LessonDAOImpl;
import ru.sfedu.mmcs.korovenko.model.Lesson;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LessonDAO lessonDAO = new LessonDAOImpl();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    findAllLessons();
                    break;
                case 2:
                    findLessonById();
                    break;
                case 3:
                    findLessonDescriptionById();
                    break;
                case 4:
                    addNewLesson();
                    break;
                case 5:
                    updateLesson();
                    break;
                case 6:
                    deleteLesson();
                    break;
                case 7:
                    deleteAllLessons();
                    break;
                case 8:
                    System.out.println("Окончание сессии");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ УПРАВЛЕНИЯ УРОКАМИ ===");
        System.out.println("1. Показать все уроки");
        System.out.println("2. Найти урок по ID");
        System.out.println("3. Найти описание урока по ID");
        System.out.println("4. Добавить новый урок");
        System.out.println("5. Обновить урок");
        System.out.println("6. Удалить урок");
        System.out.println("7. Удалить все уроки");
        System.out.println("8. Выход");
        System.out.print("Ваш выбор: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void findAllLessons() {
        List<Lesson> lessons = lessonDAO.findAll();
        if (lessons.isEmpty()) {
            System.out.println("Уроки не найдены.");
        } else {
            System.out.println("\n--- Список уроков ---");
            for (Lesson lesson : lessons) {
                System.out.println(lesson);
            }
        }
    }

    private static void findLessonById() {
        System.out.print("Введите ID урока: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Lesson lesson = lessonDAO.findById(id);
            if (lesson != null) {
                System.out.println("Найден урок: " + lesson);
            } else {
                System.out.println("Урок с ID " + id + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число.");
        }
    }

    private static void findLessonDescriptionById() {
        System.out.print("Введите ID урока: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Lesson lesson = lessonDAO.findById(id);
            if (lesson != null) {
                System.out.println("Содержание урока " + lesson + "\n" + "Описание: " + lesson.getContent());

            } else {
                System.out.println("Урок с ID " + id + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число.");
        }
    }

    private static void addNewLesson() {
        System.out.println("\n--- Добавление нового урока ---");
        System.out.print("Введите заголовок: ");
        String title = scanner.nextLine();

        System.out.println("Введите содержание (для завершения введите пустую строку):");
        StringBuilder contentBuilder = new StringBuilder();

        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            contentBuilder.append(line).append("\n");
        }

        String content = contentBuilder.toString().trim();

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setContent(content);

        lessonDAO.save(lesson);

        System.out.println("Урок добавлен {id: " + lesson.getId() + "}");
    }

    private static void updateLesson() {
        System.out.println("\n--- Обновление урока ---");
        System.out.print("Введите ID урока для обновления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Lesson existingLesson = lessonDAO.findById(id);

            if (existingLesson == null) {
                System.out.println("Урок с ID " + id + " не найден.");
                return;
            }

            System.out.println("Текущие данные: " + existingLesson);
            System.out.print("Новый заголовок (Enter - оставить текущий): ");
            String title = scanner.nextLine();
            if (!title.trim().isEmpty()) {
                existingLesson.setTitle(title);
            }

            System.out.print("Новое содержание (Enter - оставить текущее): ");
            String content = scanner.nextLine();
            if (!content.trim().isEmpty()) {
                existingLesson.setContent(content);
            }

            lessonDAO.save(existingLesson);
            System.out.println("Урок обновлен.");

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число.");
        }
    }

    private static void deleteLesson() {
        System.out.print("Введите ID урока для удаления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            lessonDAO.delete(id);
            // Метод delete сам выводит сообщение о результате
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число.");
        }
    }

    private static void deleteAllLessons() {
        System.out.print("Вы уверенны что хотите удалить все уроки из БД? Внимание! Восстановление уроков станет невозможным [да/нет]: ");
        String answer = scanner.nextLine();

        if (Objects.equals(answer, "да")){ lessonDAO.deleteAll(); }
        else if (Objects.equals(answer, "нет")){ System.out.print("Отмена удаления"); }
        else { System.out.print("Ошибка: введите корректный ответ [да/нет]"); }
    }
}