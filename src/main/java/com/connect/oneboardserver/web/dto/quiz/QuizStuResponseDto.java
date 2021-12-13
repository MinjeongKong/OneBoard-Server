package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizStu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizStuResponseDto {

    private Long quizResponseId;

    public QuizStuResponseDto(QuizStu entity) {
        this.quizResponseId = entity.getId();
    }
}
