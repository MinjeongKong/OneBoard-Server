package com.connect.oneboardserver.web.dto.assignment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitCheckRequestDto {

    private Long score;
    private String feedback;

    public SubmitCheckRequestDto(Long score, String feedback) {
        this.score = score;
        this.feedback = feedback;
    }
}
