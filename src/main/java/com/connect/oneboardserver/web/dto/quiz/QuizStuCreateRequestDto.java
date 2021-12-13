package com.connect.oneboardserver.web.dto.quiz;

import com.connect.oneboardserver.domain.quiz.QuizStu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizStuCreateRequestDto {

    private Integer response; //1,2,3,4,5

    @Builder
    public QuizStuCreateRequestDto(Integer response) {
        this.response = response;
    }

    public QuizStu toEntity() {
        return QuizStu.builder()
                .response(response).build();
    }
}
