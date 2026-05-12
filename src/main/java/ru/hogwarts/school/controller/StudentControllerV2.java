package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("v2/students")
public class StudentControllerV2 {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<Collection<String>> getNamesStartsWithLetter(@RequestParam(required = false) String letter) {
        return ResponseEntity.ok(studentService.getNamesStartsWithLetter(letter));
    }

    @GetMapping("/avgAge")
    public ResponseEntity<Double> getStudentAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAgeByStreams());
    }

    @GetMapping("/print-parallel")
    public ResponseEntity printParallel() {
        studentService.printParallel();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity printSynchronized() {
        studentService.printSynchronized();
        return ResponseEntity.ok().build();
    }

}
