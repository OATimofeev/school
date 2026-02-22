package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.util.ResponseUtil;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        return ResponseUtil.prepareResponse(studentService.get(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAll(@RequestParam(required = false) Integer age) {
        return ResponseEntity.ok(studentService.getAll(age));
    }

    @GetMapping("/find")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        return ResponseEntity.ok(studentService.getAllBetweenAge(min, max));
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseUtil.prepareResponse(studentService.create(student));
    }

    @PutMapping
    public ResponseEntity<Student> edit(@RequestBody Student student) {
        return ResponseUtil.prepareResponse(studentService.edit(student));
    }

    @DeleteMapping("{id}")
    public Student delete(@PathVariable Long id) {
        return studentService.delete(id);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        return ResponseUtil.prepareResponse(studentService.getStudentFaculty(id));
    }

}
