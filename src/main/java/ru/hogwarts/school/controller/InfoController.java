package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping
public class InfoController {

    @Autowired
    private InfoService infoService;

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/port")
    public Integer getPort() {
        return port;
    }

    @GetMapping("/sum")
    public Integer getSumOfMillionNums() {
        return infoService.getSumOfMillionNums();
    }
}
