package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("{id}")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        return prepareResponse(facultyService.get(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> get(@RequestParam(required = false) String color) {

        return ResponseEntity.ok(facultyService.getAll(color));
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        return prepareResponse(facultyService.edit(faculty));
    }

    @DeleteMapping("{id}")
    public Faculty delete(@PathVariable Long id) {
        return facultyService.delete(id);
    }

    private ResponseEntity<Faculty> prepareResponse(Faculty checked) {
        if (checked == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(checked);
    }
}
