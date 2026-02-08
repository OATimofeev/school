package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyService {

    private final HashMap<Long, Faculty> facultyMap = new HashMap<>();
    private long lastId = 0;

    public Collection<Faculty> getAll(String color) {
        if (color == null || color.isBlank()) {
            return facultyMap.values();
        }
        return facultyMap.values().stream().filter(x -> x.getColor().equalsIgnoreCase(color)).toList();
    }

    public Faculty get(Long id) {
        return facultyMap.get(id);
    }

    public Faculty create(Faculty faculty) {
        faculty.setId(++lastId);
        facultyMap.put(lastId, faculty);
        return faculty;
    }

    public Faculty edit(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            return facultyMap.put(faculty.getId(), faculty);
        }
        return null;
    }

    public Faculty delete(Long id) {
        return facultyMap.remove(id);
    }
}
