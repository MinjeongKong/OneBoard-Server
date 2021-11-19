package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Submit;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubmitFindResponseDto {

    private Long submitId;
    private Long assignmentId;
    private String assignmentTitle;
    private Long userId;
    private String userName;
    private String studentNumber;
    private String content;
    private String fileUrl;
    private Float score;
    private String feedback;
    private String createdDt;
    private String updatedDt;

    public SubmitFindResponseDto(Submit entity) {
        this.submitId = entity.getId();
        this.assignmentId = entity.getAssignment().getId();
        this.assignmentTitle = entity.getAssignment().getTitle();
        this.userId = entity.getStudent().getId();
        this.userName = entity.getStudent().getName();
        this.studentNumber = entity.getStudent().getStudentNumber();
        this.content = entity.getContent();
        this.fileUrl = entity.getFileUrl();
        this.score = entity.getScore();
        this.feedback = entity.getFeedback();
        this.createdDt = entity.getCreatedDt();
        this.updatedDt = entity.getUpdatedDt();
    }

    public static SubmitFindResponseDto toResponseDto(Submit entity) {
        return new SubmitFindResponseDto(entity);
    }
}
