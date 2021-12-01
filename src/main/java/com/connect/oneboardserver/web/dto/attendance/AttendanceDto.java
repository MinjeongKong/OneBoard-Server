package com.connect.oneboardserver.web.dto.attendance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceDto {
    private Long lessonId;
    private String lessonDate;
    private Integer status;

    @Builder
    public AttendanceDto(Long lessonId, String lessonDate, Integer status) {
        this.lessonId = lessonId;
        this.lessonDate = lessonDate;
        this.status = status;
    }
}
