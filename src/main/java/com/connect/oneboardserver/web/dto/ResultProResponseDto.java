package com.connect.oneboardserver.web.dto;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import com.connect.oneboardserver.web.dto.quiz.QuizStuFindResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultProResponseDto {

    private Long lessonId;
    private String lessonTitle;
    private Long quizId;
    private String createdDt;
    private Integer correctNum;
    private Integer incorrectNum;

    private List<QuizStuFindResponseDto> quizOList;
    private List<QuizStuFindResponseDto> quizXList;

    public ResultProResponseDto(QuizPro entity, List<QuizStuFindResponseDto> quizOList, List<QuizStuFindResponseDto> quizXList) {
        this.lessonId = entity.getLesson().getId();
        this.lessonTitle = entity.getLesson().getTitle();
        this.quizId = entity.getId();
        this.createdDt = entity.getCreatedDt();
        this.correctNum = entity.getCorrectNum();
        this.incorrectNum = entity.getIncorrectNum();
        this.quizOList = quizOList;
        this.quizXList = quizXList;
    }
}
