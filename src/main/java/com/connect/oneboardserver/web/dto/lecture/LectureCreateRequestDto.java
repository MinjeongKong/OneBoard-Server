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
    private String defaultDateTime;
    private String defaultRoom;

    @Builder
    public LectureCreateRequestDto(String title, String semester, String defaultDateTime, String defaultRoom) {
        this.title = title;
        this.semester = semester;
        this.defaultDateTime = defaultDateTime;
        this.defaultRoom = defaultRoom;
    }

    public Lecture toEntity() {
        return Lecture.builder()
                .title(title)
                .semester(semester)
                .defaultDateTime(defaultDateTime)
                .defaultRoom(defaultRoom)
                .build();
    }
}
