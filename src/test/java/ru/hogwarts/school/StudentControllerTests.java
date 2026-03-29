package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@AutoConfigureRestTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void getTest() {
        Student student = restTestClient.get()
                .uri("http://localhost:" + port + "/students/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Student.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(student).isNotNull();
    }

    @Test
    void findStudentsEmptyTest() {
        restTestClient.get()
                .uri("http://localhost:" + port + "/students/find?min=20&max=0")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    void createTest() {
        String json = """
                {
                    "name": "Иван",
                    "age": 22
                }
                """;

        restTestClient.post()
                .uri("http://localhost:" + port + "/students")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Student.class);
    }

    @Test
    void editTest() {
        String json = """
                {
                    "id": 1,
                    "name": "Петр",
                    "age": 25
                }
                """;

        restTestClient.put()
                .uri("http://localhost:" + port + "/students")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Student.class);
    }

    @Test
    void deleteTest() {
        restTestClient.delete()
                .uri("http://localhost:" + port + "/students/{id}", 200L)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getFaculty404Test() {
        restTestClient.get()
                .uri("http://localhost:" + port + "/students/{id}/faculty", 100000L)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Faculty.class);
    }

}
