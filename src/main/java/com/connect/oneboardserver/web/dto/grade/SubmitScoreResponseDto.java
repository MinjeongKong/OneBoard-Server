package com.connect.oneboardserver.web.dto.grade;

import com.connect.oneboardserver.domain.assignment.Submit;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitScoreResponseDto {

    private Long submitId;
    private Long assignmentId;
    private String assignmentTitle;
    private Float score;

    public SubmitScoreResponseDto(Submit entity) {
        this.submitId = entity.getId();
        this.assignmentId = entity.getAssignment().getId();
        this.assignmentTitle = entity.getAssignment().getTitle();
        this.score = entity.getScore();
    }
}
