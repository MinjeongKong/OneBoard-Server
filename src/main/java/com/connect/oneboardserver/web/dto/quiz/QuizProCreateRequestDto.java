package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizProCreateRequestDto {

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private Integer answer;

    @Builder
    public QuizProCreateRequestDto(String question, String answer1, String answer2, String answer3, String answer4, String answer5, Integer answer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
        this.answer = answer;
    }

    public QuizPro toEntity() {
        return QuizPro.builder()
                .question(question)
                .answer1(answer1)
                .answer2(answer2)
                .answer3(answer3)
                .answer4(answer4)
                .answer5(answer5)
                .answer(answer)
                .build();
    }
}
