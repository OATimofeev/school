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
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

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
            .age(15)
            .build();
    private static JSONObject studentJson;

    @SneakyThrows
    @BeforeAll
    public static void setUp() {
        studentJson = new JSONObject();
        studentJson.put("name", student1.getName());
        studentJson.put("age", 15);
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        when(studentService.get(student1.getId())).thenReturn(student1);

        mockMvc.perform(get("/students/{id}", student1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        Collection<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAll(null)).thenReturn(students);

        mockMvc.perform(get("/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void findStudentsByAgeBetweenTest() throws Exception {
        Collection<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllBetweenAge(10, 20)).thenReturn(students);

        mockMvc.perform(get("/students/find")
                        .param("min", "10")
                        .param("max", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void createStudentTest() throws Exception {
        when(studentService.create(any(Student.class))).thenReturn(student1);

        mockMvc.perform(post("/students")
                        .content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));
    }

    @Test
    public void editStudentTest() throws Exception {
        when(studentService.edit(any(Student.class))).thenReturn(student1);

        mockMvc.perform(put("/students")
                        .content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        when(studentService.delete(student1.getId())).thenReturn(student1);

        mockMvc.perform(delete("/students/{id}", student1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));
    }

    @Test
    public void getStudentFacultyTest() throws Exception {
        when(studentService.getStudentFaculty(student1.getId())).thenReturn(faculty);

        mockMvc.perform(get("/students/{id}/faculty", student1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("ElectroTech"))
                .andExpect(jsonPath("$.color").value("Red"));
    }
}