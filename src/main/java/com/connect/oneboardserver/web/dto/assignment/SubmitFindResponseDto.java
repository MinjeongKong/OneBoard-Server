package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Submit;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitFindResponseDto {

    private Long id;
    private Long assignmentId;
    private Long userId;
    private String content;
    private String fileUrl;
    private Long score;
    private String feedback;
    private String createdDt;
    private String updatedDt;

    public SubmitFindResponseDto(Submit entity) {
        this.id = entity.getId();
        this.assignmentId = entity.getAssignment().getId();
        this.userId = entity.getMember().getId();
        this.content = entity.getContent();
        this.fileUrl = entity.getFileUrl();
        this.score = entity.getScore();
        this.feedback = entity.getFeedback();
        this.createdDt = entity.getCreatedDt();
        this.updatedDt = entity.getUpdatedDt();
    }
}
