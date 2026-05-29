package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
public class InfoService {

    public Integer getSumOfMillionNums() {
        log.info("Was invoked method for get sum of million numbers");
        return Stream.iterate(1, a -> a + 1).limit(1_000_000).parallel().reduce(0, Integer::sum);

    }
}
