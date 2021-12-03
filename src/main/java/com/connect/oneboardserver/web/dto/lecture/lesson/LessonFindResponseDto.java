package com.connect.oneboardserver.web.dto.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LessonFindResponseDto {

    private Long lectureId;
    private Long lessonId;
    private String title;
    private String date;
    private String noteUrl;
    private Integer type;
    private String videoUrl;
    private String session;
    private String room;

    @Builder
    public LessonFindResponseDto(Long lectureId, Long lessonId, String title, String date, String noteUrl,
                                 Integer type, String videoUrl, String session, String room) {
        this.lectureId = lectureId;
        this.lessonId = lessonId;
        this.title = title;
        this.date = date;
        this.noteUrl = noteUrl;
        this.type = type;
        this.videoUrl = videoUrl;
        this.session = session;
        this.room = room;
    }

    public static LessonFindResponseDto toResponseDto(Lesson entity) {
        return LessonFindResponseDto.builder()
                .lectureId(entity.getLecture().getId())
                .lessonId(entity.getId())
                .title(entity.getTitle())
                .date(entity.getDate())
                .type(entity.getType())
                .videoUrl(entity.getVideoUrl())
                .room(entity.getRoom())
                .build();
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }

    public void setSession(String session) {
        this.session = session;
    }
}