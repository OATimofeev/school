package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public Collection<Faculty> getAll(String color) {
        if (color == null || color.isBlank()) {
            return facultyRepository.findAll();
        }
        return facultyRepository.getAllByColorEqualsIgnoreCase(color);
    }

    public Faculty get(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty getByNameOrColor(String name, String color) {
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }

    public Faculty create(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Transactional
    public Faculty edit(Faculty faculty) {
        if (faculty.getId() == null || !facultyRepository.existsById(faculty.getId())) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    @Transactional
    public Faculty delete(Long id) {
        Faculty deleted = get(id);
        facultyRepository.deleteById(id);
        return deleted;
    }

    public Collection<Student> getFacultyStudents(Long id) {
        Faculty f = get(id);
        return f == null ? null : f.getStudents();
    }
}
