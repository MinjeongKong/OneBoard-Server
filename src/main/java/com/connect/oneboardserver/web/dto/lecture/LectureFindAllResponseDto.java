package com.connect.oneboardserver.web.dto.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureFindAllResponseDto {

    private Long id;
    private String title;
    private String semester;
    private String lecturePlan;

    @Builder
    public LectureFindAllResponseDto(Long id, String title, String semester, String lecturePlan) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.lecturePlan = lecturePlan;
    }

    public static LectureFindAllResponseDto toResponseDto(Lecture entity) {
        return LectureFindAllResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .semester(entity.getSemester())
                .lecturePlan(entity.getLecturePlan())
                .build();
    }
}
