package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonCreateRequestDto {

    private String title;
    private String date;
    private String note;
    private Integer type;
    private String room;
    private String meetingId;
    private String videoUrl;

    @Builder
    public LessonCreateRequestDto(String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
    }

    public Lesson toEntity() {
        return Lesson.builder()
                .title(title)
                .date(date)
                .note(note)
                .type(type)
                .room(room)
                .meetingId(meetingId)
                .videoUrl(videoUrl)
                .build();
    }
}