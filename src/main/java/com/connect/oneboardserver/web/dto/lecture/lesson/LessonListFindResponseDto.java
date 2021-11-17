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
    private String meetingId;
    private String videoUrl;

    @Builder
    public LessonListFindResponseDto(Long id, Long lectureId, String title, String date, String note, Integer type, String room, String meetingId, String videoUrl) {
        this.id = id;
        this.lectureId = lectureId;
        this.title = title;
        this.date = date;
        this.note = note;
        this.type = type;
        this.room = room;
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
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
                .meetingId(entity.getMeetingId())
                .videoUrl(entity.getVideoUrl())
                .build();
    }
}

