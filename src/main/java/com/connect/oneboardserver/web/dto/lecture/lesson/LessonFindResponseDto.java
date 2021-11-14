package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonFindResponseDto {

    private Lesson lesson;

    @Builder
    public LessonFindResponseDto(Lesson lesson) {
        this.lesson = lesson;
    }

}
