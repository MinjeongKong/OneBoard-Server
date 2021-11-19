package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LessonListFindResponseDto {

    private Long lectureId;
    private Long id;
    private String title;
    private String date;
    private String note;
    private Integer type;
    private String room;
    private String meeting_id;
    private String video_url;

    @Builder
    public LessonListFindResponseDto(Long id, Long lectureId, String title, String date, String note, Integer type, String room, String meeting_id, String video_url) {
        this.id = id;
        this.lectureId = lectureId;
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
        this.meeting_id = meeting_id;
        this.video_url = video_url;
    }

    public static LessonListFindResponseDto toResponseDto(Lesson entity) {
        return LessonListFindResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .lectureId(entity.getLecture().getId())
                .note(entity.getNote())
                .type(entity.getType())
                .room(entity.getRoom())
                .meeting_id(entity.getMeeting_id())
                .video_url(entity.getVideo_url())
                .build();
    }
}


