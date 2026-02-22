package ru.hogwarts.school.util;

import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<T> prepareResponse(T entity) {
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }
}
