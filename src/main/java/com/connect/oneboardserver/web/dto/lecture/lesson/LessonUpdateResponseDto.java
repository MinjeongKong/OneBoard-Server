package com.connect.oneboardserver.web.dto.lecture.lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonUpdateResponseDto {

    private Long lessonId;

    @Builder
    public LessonUpdateResponseDto(Long lessonId) {
        this.lessonId = lessonId;
    }
}
