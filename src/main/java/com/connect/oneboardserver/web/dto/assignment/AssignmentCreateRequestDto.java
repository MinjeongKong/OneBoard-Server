package com.connect.oneboardserver.web.dto.assignment;

import com.connect.oneboardserver.domain.assignment.Assignment;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AssignmentCreateRequestDto {

    private String title;
    private String content;
    private String fileUrl;
    private String startDt;
    private String endDt;
    private String exposeDt;
    private Float score;

    @Builder
    public AssignmentCreateRequestDto(String title, String content, String fileUrl, String startDt, String endDt, String exposeDt, Float score) {
        this.title = title;
        this.content = content;
        this.fileUrl = fileUrl;
        this.startDt = startDt;
        this.endDt = endDt;
        this.exposeDt = exposeDt;
        this.score = score;
    }

    public Assignment toEntity() {
        return Assignment.builder()
                .title(title)
                .content(content)
                .fileUrl(fileUrl)
                .startDt(startDt)
                .endDt(endDt)
                .exposeDt(exposeDt)
                .score(score)
                .build();
    }
}
