package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.util.ResponseUtil;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("{id}")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        return ResponseUtil.prepareResponse(facultyService.get(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> get(@RequestParam(required = false) String color) {
        return ResponseEntity.ok(facultyService.getAll(color));
    }

    @GetMapping("/find")
    public ResponseEntity<Faculty> findByAgeBetween(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        return ResponseEntity.ok(facultyService.getByNameOrColor(name, color));
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        return ResponseUtil.prepareResponse(facultyService.edit(faculty));
    }

    @DeleteMapping("{id}")
    public Faculty delete(@PathVariable Long id) {
        return facultyService.delete(id);
    }
    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long id) {
        return ResponseUtil.prepareResponse(facultyService.getFacultyStudents(id));
    }
}
