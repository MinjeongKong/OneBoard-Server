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
    private String noteUrl;
    private Integer type;
    private String room;
    private String meetingId;
    private String videoUrl;

    @Builder
    public LessonUpdateRequestDto(String title, String date, String noteUrl, Integer type, String room, String meetingId, String videoUrl) {
        this.title = title;
        this.date = date;
        this.noteUrl = noteUrl;
        this.type = type;
        this.room = room;
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
    }

}
