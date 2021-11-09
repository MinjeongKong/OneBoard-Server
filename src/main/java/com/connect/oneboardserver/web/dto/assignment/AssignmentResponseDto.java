package com.connect.oneboardserver.web.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponseDto {

    private String title;
    private String content;
    private String fileUrl;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private LocalDateTime exposeDt;
    private Timestamp createdDt;
    private Timestamp updatedDt;


}
