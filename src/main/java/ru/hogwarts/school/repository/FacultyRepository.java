package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    @Query("SELECT f FROM Faculty f WHERE (:color IS NULL OR UPPER(f.color) = UPPER(:color))")
    Collection<Faculty> getAllByColorEqualsIgnoreCase(String color);
}
