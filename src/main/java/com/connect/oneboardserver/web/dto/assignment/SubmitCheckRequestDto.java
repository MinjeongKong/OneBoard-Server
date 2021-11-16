package com.connect.oneboardserver.web.dto.assignment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitCheckRequestDto {

    private Float score;
    private String feedback;

    public SubmitCheckRequestDto(Float score, String feedback) {
        this.score = score;
        this.feedback = feedback;
    }
}
