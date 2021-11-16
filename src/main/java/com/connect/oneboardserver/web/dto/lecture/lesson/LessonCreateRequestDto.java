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
    private String meeting_id;
    private String video_url;

    @Builder
    public LessonCreateRequestDto(String title, String date, String note, Integer type, String room, String meeting_id, String video_url) {
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
        this.meeting_id = meeting_id;
        this.video_url = video_url;
    }





    public Lesson toEntity() {
        return Lesson.builder()
                .title(title)
                .date(date)
                .note(note)
                .type(type)
                .room(room)
                .meeting_id(meeting_id)
                .video_url(video_url)
                .build();
    }
}