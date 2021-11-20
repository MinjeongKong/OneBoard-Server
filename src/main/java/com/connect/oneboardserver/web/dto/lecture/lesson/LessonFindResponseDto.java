package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonFindResponseDto {

    private Long lectureId;
    private Long id;
    private String title;
    private String date;
    private String noteUrl;
    private Integer type;
    private String room;
    private String meetingId;
    private String videoUrl;

    @Builder
    public LessonFindResponseDto(Long id, Long lectureId, String title, String date, String noteUrl, Integer type, String room, String meetingId, String videoUrl) {
        this.id = id;
        this.lectureId = lectureId;
        this.title = title;
        this.date = date;
        this.noteUrl = noteUrl;
        this.type = type;
        this.room = room;
        this.meetingId = meetingId;
        this.videoUrl = videoUrl;
    }

    public static LessonFindResponseDto toResponseDto(Lesson entity) {
        return LessonFindResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .lectureId(entity.getLecture().getId())
                .noteUrl(entity.getNoteUrl())
                .type(entity.getType())
                .room(entity.getRoom())
                .meetingId(entity.getMeetingId())
                .videoUrl(entity.getVideoUrl())
                .build();
    }
}