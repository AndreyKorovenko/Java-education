package ru.sfedu.mmcs.korovenko.dao;
import ru.sfedu.mmcs.korovenko.model.Lesson;
import java.util.List;

public interface LessonDAO {
    Lesson findById(int id);
    List<Lesson> findAll();
    void save(Lesson lesson);
    void delete(int id);
    void deleteAll();
}