package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

@AutoConfigureRestTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void contextLoads() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void getFaculty404Test() {
        restTestClient.get()
                .uri("http://localhost:" + port + "/faculties/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Faculty.class);
    }

    @Test
    void getAllFacultiesTest() {
        restTestClient.get()
                .uri("http://localhost:" + port + "/faculties")
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    void findFacultyNotFoundTest() {
        restTestClient.get()
                .uri("http://localhost:" + port + "/faculties/find?name=NonExistent&color=Unknown")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Faculty.class);
    }

    @Test
    void createFacultyTest() {
        String json = """
                {
                    "name": "Dark Arts",
                    "color": "Black"
                }
                """;

        restTestClient.post()
                .uri("http://localhost:" + port + "/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Faculty.class);
    }

    @Test
    void editFaculty404Test() {
        String json = """
                {
                    "id": 999,
                    "name": "Potions",
                    "color": "Green"
                }
                """;

        restTestClient.put()
                .uri("http://localhost:" + port + "/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Faculty.class);
    }

    @Test
    void deleteFacultyTest() {
        restTestClient.delete()
                .uri("http://localhost:" + port + "/faculties/{id}", 1L)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getFacultyStudentsEmptyTest() {
        restTestClient.get()
                .uri("http://localhost:" + port + "/faculties/999/students")
                .exchange()
                .expectStatus().isNotFound();
    }
}
