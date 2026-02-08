package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        return prepareResponse(studentService.get(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAll(@RequestParam(required = false) Integer age) {
        return ResponseEntity.ok(studentService.getAll(age));
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return prepareResponse(studentService.create(student));
    }

    @PutMapping
    public ResponseEntity<Student> edit(@RequestBody Student student) {
        return prepareResponse(studentService.edit(student));
    }

    @DeleteMapping("{id}")
    public Student delete(@PathVariable Long id) {
        return studentService.delete(id);
    }

    private ResponseEntity<Student> prepareResponse(Student checked) {
        if (checked == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(checked);
    }

}
