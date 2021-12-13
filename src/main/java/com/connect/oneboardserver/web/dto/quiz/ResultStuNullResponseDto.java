package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultStuNullResponseDto {

    private Long lessonId;
    private String lessonTitle;
    private Long quizId;
    private String createdDt;
    private Integer correctNum;
    private Integer incorrectNum;

    public ResultStuNullResponseDto(QuizPro entity) {
        this.lessonId = entity.getLesson().getId();
        this.lessonTitle = entity.getLesson().getTitle();
        this.quizId = entity.getId();
        this.createdDt = entity.getCreatedDt();
        this.correctNum = entity.getCorrectNum();
        this.incorrectNum = entity.getIncorrectNum();
    }
}
