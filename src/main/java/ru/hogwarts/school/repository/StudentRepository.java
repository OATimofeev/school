package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE (:age IS NULL OR s.age = :age)")
    Collection<Student> getAllByAgeEquals(Integer age);

    Collection<Student> findByAgeBetween(Integer ageAfter, Integer ageBefore);

    @Query("SElECT COUNT(*) FROM Student s")
    Integer getCount();

    @Query("SELECT AVG(s.age) FROM Student s")
    Optional<Double> getAverageAge();

    @Query("SELECT s FROM Student s ORDER BY s.id DESC LIMIT 5")
    Collection<Student> getLast5Students();
}
