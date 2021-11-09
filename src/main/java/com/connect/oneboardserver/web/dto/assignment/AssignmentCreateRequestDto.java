package com.connect.oneboardserver.web.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCreateRequestDto {

    @NotEmpty
    private String title;

    private String content;
    private String fileUrl;

    @NotEmpty
    private LocalDateTime startDt;
    @NotEmpty
    private LocalDateTime endDt;

    private LocalDateTime exposeDt;
    @NotEmpty
    private Timestamp createdDt;
    private Timestamp updatedDt;

}
