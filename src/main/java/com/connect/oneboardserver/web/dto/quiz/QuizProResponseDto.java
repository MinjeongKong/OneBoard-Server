package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizProResponseDto {
    private Long quizId;

    public QuizProResponseDto(QuizPro entity) {
        this.quizId = entity.getId();
    }
}
