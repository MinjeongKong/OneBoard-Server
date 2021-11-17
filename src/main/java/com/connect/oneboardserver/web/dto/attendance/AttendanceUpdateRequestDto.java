package com.connect.oneboardserver.web.dto.attendance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceUpdateRequestDto {

    private Long studentId;
    private Long lessonId;
    private Integer status;

    @Builder
    public AttendanceUpdateRequestDto(Long studentId, Long lessonId, Integer status) {
        this.studentId = studentId;
        this.lessonId = lessonId;
        this.status = status;
    }
}
