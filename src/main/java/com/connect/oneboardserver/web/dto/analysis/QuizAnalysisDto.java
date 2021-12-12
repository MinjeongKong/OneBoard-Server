package com.connect.oneboardserver.web.dto.analysis;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizAnalysisDto {

    private Long quizId;
    private String createdDt;
    private Integer correctNum;
    private Integer incorrectNum;

    public QuizAnalysisDto(QuizPro entity) {
        this.quizId = entity.getId();
        this.createdDt = entity.getCreatedDt();
        this.correctNum = entity.getCorrectNum();
        this.incorrectNum = entity.getIncorrectNum();
    }
}
