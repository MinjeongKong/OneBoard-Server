package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LessonCreateRequestDto {

    private String title;
    private String date;
    private Integer type;
    private String videoUrl;
    private String room;

    @Builder
    public LessonCreateRequestDto(String title, String date, Integer type, String videoUrl, String room) {
        this.title = title;
        this.date = date;
        this.type = type;
        this.videoUrl = videoUrl;
        this.room = room;
    }

    public Lesson toEntity() {
        return Lesson.builder()
                .title(title)
                .date(date)
                .type(type)
                .build();
    }
}