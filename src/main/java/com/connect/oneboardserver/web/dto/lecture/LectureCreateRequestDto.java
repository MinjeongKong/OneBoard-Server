package com.connect.oneboardserver.web.dto.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureCreateRequestDto {

    private String title;
    private String semester;

    @Builder
    public LectureCreateRequestDto(String title, String semester) {
        this.title = title;
        this.semester = semester;
    }

    public Lecture toEntity() {
        return Lecture.builder()
                .title(title)
                .semester(semester)
                .build();
    }
}
