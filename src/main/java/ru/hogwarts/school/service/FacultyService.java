package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
@Slf4j
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public Collection<Faculty> getAll(String color) {
        if (color == null || color.isBlank()) {
            log.info("Was invoked method for get all faculties");
            return facultyRepository.findAll();
        }
        log.info("Was invoked method for get faculties by color == {}", color);
        return facultyRepository.getAllByColorEqualsIgnoreCase(color);
    }

    public Faculty get(Long id) {
        log.info("Was invoked method for get faculty by id = {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty getByNameOrColor(String name, String color) {
        log.info("Was invoked method for get faculty by name == {} or color == {}", name, color);
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }

    public Faculty create(Faculty faculty) {
        log.info("Was invoked method for create faculty");
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Transactional
    public Faculty edit(Faculty faculty) {
        log.info("Was invoked method for edit faculty by id = {}", faculty.getId());
        if (faculty.getId() == null || !facultyRepository.existsById(faculty.getId())) {
            log.warn("Faculty for edit not found or id is null");
            return null;
        }
        return facultyRepository.save(faculty);
    }

    @Transactional
    public Faculty delete(Long id) {
        log.info("Was invoked method for delete faculty by id = {}", id);
        Faculty deleted = get(id);
        facultyRepository.deleteById(id);
        return deleted;
    }

    public Collection<Student> getFacultyStudents(Long id) {
        log.info("Was invoked method for get all faculty's student by id = {}", id);
        Faculty f = get(id);
        return f == null ? null : f.getStudents();
    }
}
