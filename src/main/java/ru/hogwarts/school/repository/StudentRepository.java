package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE (:age IS NULL OR s.age = :age)")
    Collection<Student> getAllByAgeEquals(Integer age);

    Collection<Student> findByAgeBetween(Integer ageAfter, Integer ageBefore);
}
