package com.connect.oneboardserver.service.lesson;

import com.connect.oneboardserver.domain.lesson.LessonRepository;
import com.connect.oneboardserver.web.dto.LessonCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Transactional
    public Long createLesson(LessonCreateRequestDto requestDto) {
        return lessonRepository.save(requestDto.toEntity()).getId();
    }
}