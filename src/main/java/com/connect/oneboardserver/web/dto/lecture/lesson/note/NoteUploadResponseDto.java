package com.connect.oneboardserver.web.dto.lecture.lesson.note;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoteUploadResponseDto {

    private Long lessonId;

    @Builder
    public NoteUploadResponseDto(Long lessonId) {
        this.lessonId = lessonId;
    }
}