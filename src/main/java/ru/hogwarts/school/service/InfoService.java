package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Service
@Transactional
@Slf4j
public class InfoService {

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public InfoService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public Collection<Avatar> getAll(Integer pageNumber, Integer pageSize) {
        log.info("Was invoked method for get all avatars for page number = {} and size = {}", pageNumber, pageSize);
        return avatarRepository.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }

    @SneakyThrows
    public void upload(Long studentId, MultipartFile file) {
        log.info("Was invoked method for upload avatar for student id = {}", studentId);
        Student student = studentService.get(studentId);

        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findByStudentId(studentId);

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateFixImage(filePath));
        avatarRepository.save(avatar);
    }

    public Avatar findByStudentId(Long studentId) {
        log.info("Was invoked method for get avatar for student id = {}", studentId);
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    @SneakyThrows
    private byte[] generateFixImage(Path filePath) {
        log.debug("Was invoked method for generate fix image");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() * 100 / image.getWidth();
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D g2d = preview.createGraphics();
            g2d.drawImage(image, 0, 0, 100, height, null);
            g2d.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);

            log.debug("Fix image was generated");
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        log.debug("Get extension for file {}", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
