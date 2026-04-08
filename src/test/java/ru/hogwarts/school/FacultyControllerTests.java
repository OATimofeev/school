package ru.hogwarts.school;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyService facultyService;

    private static final Faculty faculty = Faculty.builder()
            .id(1L)
            .name("ElectroTech")
            .color("Red")
            .build();

    private static final Student student1 = Student.builder()
            .id(1L)
            .name("Test1")
            .age(15)
            .build();

    private static final Student student2 = Student.builder()
            .id(2L)
            .name("Test2")
            .age(16)
            .build();

    private static JSONObject facultyJson;

    @SneakyThrows
    @BeforeAll
    public static void setUp() {
        facultyJson = new JSONObject();
        facultyJson.put("name", faculty.getName());
        facultyJson.put("color", faculty.getColor());
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        when(facultyService.get(faculty.getId())).thenReturn(faculty);

        mockMvc.perform(get("/faculties/{id}", faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void getAllFacultiesTest() throws Exception {
        Collection<Faculty> faculties = Arrays.asList(faculty);
        when(facultyService.getAll(null)).thenReturn(faculties);

        mockMvc.perform(get("/faculties")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void findByNameOrColorTest() throws Exception {
        when(facultyService.getByNameOrColor("Electro", "Red")).thenReturn(faculty);

        mockMvc.perform(get("/faculties/find")
                        .param("name", "Electro")
                        .param("color", "Red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void createFacultyTest() throws Exception {
        when(facultyService.create(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculties")
                        .content(facultyJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void editFacultyTest() throws Exception {
        when(facultyService.edit(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculties")
                        .content(facultyJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        when(facultyService.delete(faculty.getId())).thenReturn(faculty);

        mockMvc.perform(delete("/faculties/{id}", faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void getFacultyStudentsTest() throws Exception {
        Collection<Student> students = Arrays.asList(student1, student2);
        when(facultyService.getFacultyStudents(faculty.getId())).thenReturn(students);

        mockMvc.perform(get("/faculties/{id}/students", faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(student1.getId()))
                .andExpect(jsonPath("$[1].id").value(student2.getId()));
    }
}