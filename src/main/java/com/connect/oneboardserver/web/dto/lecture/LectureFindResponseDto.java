package com.connect.oneboardserver.web.dto.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LectureFindResponseDto {

    private Long id;
    private String title;
    private String semester;
    private String lecturePlan;
    private String professor;

    @Builder
    public LectureFindResponseDto(Long id, String title, String semester, String lecturePlan, String professor) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.lecturePlan = lecturePlan;
        this.professor = professor;
    }

    public static LectureFindResponseDto toResponseDto(Lecture entity) {
        return LectureFindResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .semester(entity.getSemester())
                .lecturePlan(entity.getLecturePlan())
                .build();
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
