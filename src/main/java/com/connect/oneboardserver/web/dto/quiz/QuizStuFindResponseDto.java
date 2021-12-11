package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizStu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizStuFindResponseDto {

    private Long studentId;
    private String studentName;
    private String studentNumber;
    private Integer response;

    @Builder
    public QuizStuFindResponseDto(QuizStu entity) {
        this.studentId = entity.getStudent().getId();
        this.studentName = entity.getStudent().getName();
        this.studentNumber = entity.getStudent().getStudentNumber();
        this.response = entity.getResponse();
    }
}
