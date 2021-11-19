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
    private String professor;

    @Builder
    public LectureFindResponseDto(Long id, String title, String semester, String professor) {
        this.id = id;
        this.title = title;
        this.semester = semester;
        this.professor = professor;
    }

    public static LectureFindResponseDto toResponseDto(Lecture entity) {
        return LectureFindResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .semester(entity.getSemester())
                .build();
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
