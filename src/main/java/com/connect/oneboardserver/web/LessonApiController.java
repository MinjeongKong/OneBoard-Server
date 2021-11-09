package com.connect.oneboardserver.web;

import com.connect.oneboardserver.service.lesson.LessonService;
import com.connect.oneboardserver.web.dto.LessonCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class LessonApiController {

    private final LessonService postsService;

    @PostMapping("/lecture/lesson")
    public Long createLesson(@RequestBody LessonCreateRequestDto requestDto) {
        return postsService.createLesson(requestDto);
    }
}