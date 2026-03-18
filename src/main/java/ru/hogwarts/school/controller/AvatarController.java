package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("avatars")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam("file") MultipartFile file) {
        if (file.getSize() > 1024 * 300) return ResponseEntity.badRequest().body("Size too large");

        avatarService.upload(studentId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/preview")
    public ResponseEntity<byte[]> getPreview(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findByStudentId(studentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @SneakyThrows
    @GetMapping("/{studentId}")
    public void get(@PathVariable Long studentId, HttpServletResponse response) {
        Avatar avatar = avatarService.findByStudentId(studentId);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength(avatar.getFileSize().intValue());
            is.transferTo(os);
        }
    }
}
