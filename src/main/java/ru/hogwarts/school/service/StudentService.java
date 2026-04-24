package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Collection<Student> getAll(Integer age) {
        log.info("Was invoked method for get all students {}", age == null ? null : "with age == %d".formatted(age));
        return studentRepository.getAllByAgeEquals(age);
    }

    public Collection<Student> getAllBetweenAge(Integer min, Integer max) {
        min = min == null ? 0 : min;
        max = max == null ? 100 : max;
        log.info("Was invoked method for get students with age between {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Student get(Long id) {
        log.info("Was invoked method for get student by id {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Integer getCount() {
        log.info("Was invoked method for get count of students");
        return studentRepository.getCount();
    }

    public Double getAverageAge() {
        log.info("Was invoked method for get average student's age");
        return studentRepository.getAverageAge().orElse(0.0);
    }

    public Collection<Student> getLast5Students() {
        log.info("Was invoked method for get last 5 students");
        return studentRepository.getLast5Students();
    }

    public Student create(Student student) {
        log.info("Was invoked method for create student");
        student.setId(null);
        return studentRepository.save(student);
    }

    @Transactional
    public Student edit(Student student) {
        log.info("Was invoked method for edit student by id == {}", student.getId());
        if (student.getId() == null || !studentRepository.existsById(student.getId())) {
            log.warn("Student for edit not found or id is null");
            return null;
        }
        return studentRepository.save(student);
    }

    @Transactional
    public Student delete(Long id) {
        log.info("Was invoked method for delete student by id == {}", id);
        Student deleted = get(id);
        studentRepository.deleteById(id);
        return deleted;
    }

    public Faculty getStudentFaculty(Long id) {
        log.info("Was invoked method for get student's faculty by id == {}", id);
        Student s = get(id);
        return s == null ? null : s.getFaculty();
    }
}
