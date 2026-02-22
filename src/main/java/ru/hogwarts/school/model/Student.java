package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

}
