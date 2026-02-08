package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {

    private final HashMap<Long, Student> studentMap = new HashMap<>();
    private long lastId = 0;

    public Collection<Student> getAll(Integer age) {
        if (age == null) {
            return studentMap.values();
        }
        return studentMap.values().stream().filter(x -> x.getAge().equals(age)).toList();
    }

    public Student get(Long id) {
        return studentMap.get(id);
    }

    public Student create(Student student) {
        student.setId(++lastId);
        studentMap.put(lastId, student);
        return student;
    }

    public Student edit(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student delete(Long id) {
        return studentMap.remove(id);
    }
}
