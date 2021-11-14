package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LessonListFindResponseDto {

    private List<Lesson> lessonList;

    @Builder
    public LessonListFindResponseDto(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }
}


