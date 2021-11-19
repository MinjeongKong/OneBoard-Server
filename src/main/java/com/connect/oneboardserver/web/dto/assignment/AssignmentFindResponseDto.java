package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentFindResponseDto {

    private Long assignmentId;
    private Long lectureId;
    private String lectureTitle;
    private String title;
    private String content;
    private String fileUrl;
    private String startDt;
    private String endDt;
    private String exposeDt;
    private String createdDt;
    private String updatedDt;
    private Float score;

    public AssignmentFindResponseDto(Assignment entity) {
        this.assignmentId = entity.getId();
        this.lectureId = entity.getLecture().getId();
        this.lectureTitle = entity.getLecture().getTitle();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.fileUrl = entity.getFileUrl();
        this.startDt = entity.getStartDt();
        this.endDt = entity.getEndDt();
        this.exposeDt = entity.getExposeDt();
        this.createdDt = entity.getCreatedDt();
        this.updatedDt = entity.getUpdatedDt();
        this.score = entity.getScore();
    }

    public static AssignmentFindResponseDto toResponseDto(Assignment entity) {
        return new AssignmentFindResponseDto(entity);
    }

}
