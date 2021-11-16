package com.connect.oneboardserver.web.dto.lecture.lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonCreateResponseDto {

    private Long lessonId;


    @Builder
    public LessonCreateResponseDto(Long lessonId) {
        this.lessonId = lessonId;
    }
}
