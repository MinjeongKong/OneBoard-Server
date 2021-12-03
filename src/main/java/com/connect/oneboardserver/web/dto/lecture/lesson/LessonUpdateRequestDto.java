package com.connect.oneboardserver.web.dto.lecture.lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LessonUpdateRequestDto {

    private String title;
    private String date;
    private Integer type;
    private String videoUrl;
    private String room;

    @Builder
    public LessonUpdateRequestDto(String title, String date, Integer type, String videoUrl, String room) {
        this.title = title;
        this.date = date;
        this.type = type;
        this.videoUrl = videoUrl;
        this.room = room;
    }
}
