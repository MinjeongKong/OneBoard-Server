package com.connect.oneboardserver.web.dto.assignment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentUpdateRequestDto {

    private String title;
    private String content;
    private String fileUrl;
    private String startDt;
    private String endDt;
    private String exposeDt;
    private Float score;

    @Builder
    public AssignmentUpdateRequestDto(String title, String content, String fileUrl, String startDt, String endDt, String exposeDt, Float score) {
        this.title = title;
        this.content = content;
        this.fileUrl = fileUrl;
        this.startDt = startDt;
        this.endDt = endDt;
        this.exposeDt = exposeDt;
        this.score = score;
    }
}
