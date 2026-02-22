package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Collection<Student> getAll(Integer age) {
        return studentRepository.getAllByAgeEquals(age);
    }

    public Collection<Student> getAllBetweenAge(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min == null ? 0 : min, max == null ? 100 : max);
    }

    public Student get(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student create(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    @Transactional
    public Student edit(Student student) {
        if (student.getId() == null || !studentRepository.existsById(student.getId())) {
            return null;
        }
        return studentRepository.save(student);
    }

    @Transactional
    public Student delete(Long id) {
        Student deleted = get(id);
        studentRepository.deleteById(id);
        return deleted;
    }

    public Faculty getStudentFaculty(Long id) {
        Student s = get(id);
        return s == null ? null : s.getFaculty();
    }
}
