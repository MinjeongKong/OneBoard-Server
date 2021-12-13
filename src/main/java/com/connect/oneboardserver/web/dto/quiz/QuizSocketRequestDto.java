package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizSocketRequestDto {

    private Long quizId;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;

    @Builder
    public QuizSocketRequestDto(QuizPro entity) {
        this.quizId = entity.getId();
        this.question = entity.getQuestion();
        this.answer1 = entity.getAnswer1();
        this.answer2 = entity.getAnswer2();
        this.answer3 = entity.getAnswer3();
        this.answer4 = entity.getAnswer4();
        this.answer5 = entity.getAnswer5();
    }
}
