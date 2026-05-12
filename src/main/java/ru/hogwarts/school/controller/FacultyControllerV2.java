package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.util.ResponseUtil;

@RestController
@RequestMapping("v2/faculties")
public class FacultyControllerV2 {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/longestName")
    public ResponseEntity<String> getLongestFacultyName() {
        return ResponseUtil.prepareResponse(facultyService.getLongestFacultyName());
    }
}
