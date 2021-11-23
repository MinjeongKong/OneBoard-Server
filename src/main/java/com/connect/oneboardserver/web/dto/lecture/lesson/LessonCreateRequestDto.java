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
    private String noteUrl;
    private Integer type;
    private String room;
    private String meetingId;
    private String videoUrl;

    @Builder
    public LessonCreateRequestDto(String title, String date, String noteUrl, Integer type, String room, String meetingId, String videoUrl) {
        this.title = title;
        this.date = date;
        this.noteUrl = noteUrl;
        this.type = type;
        this.room = room;
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
    }

    public Lesson toEntity() {
        return Lesson.builder()
                .title(title)
                .date(date)
                .noteUrl(noteUrl)
                .type(type)
                .room(room)
                .meetingId(meetingId)
                .videoUrl(videoUrl)
                .build();
    }
}