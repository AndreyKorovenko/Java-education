package ru.sfedu.mmcs.korovenko.model;

public class Lesson {
    private int id;
    private String title;
    private String content;

    // Конструкторы
    public Lesson() {}

    public Lesson(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override //переопределение метода
    public String toString() {
        return "Lesson{" + "id=" + id + ", title='" + title + "'}";
    }
}
