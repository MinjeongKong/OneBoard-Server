package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import com.connect.oneboardserver.domain.quiz.QuizStu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultStuResponseDto {

    private Long lessonId;
    private String lessonTitle;
    private Long quizId;
    private String createdDt;
    private Integer yourAnswerNum;
    private String yourAnswerStr;
    private Integer correctAnswerNum;
    private String correctAnswerStr;
    private Integer correctNum;
    private Integer incorrectNum;

    @Builder
    public ResultStuResponseDto(QuizStu entity, Integer correctNum, Integer incorrectNum) {
        this.lessonId = entity.getQuizPro().getLesson().getId();
        this.lessonTitle = entity.getQuizPro().getLesson().getTitle();
        this.quizId = entity.getQuizPro().getId();
        this.createdDt = entity.getQuizPro().getCreatedDt();
        this.yourAnswerNum = entity.getResponse();
        this.yourAnswerStr = entity.getQuizPro().getAnswerStr(yourAnswerNum);
        this.correctAnswerNum = entity.getQuizPro().getAnswer();
        this.correctAnswerStr = entity.getQuizPro().getAnswerStr(correctAnswerNum);
        this.correctNum = correctNum;
        this.incorrectNum = incorrectNum;
    }
}
