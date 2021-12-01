package com.connect.oneboardserver.web.dto.attendance;

import com.connect.oneboardserver.domain.attendance.Attendance;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceFindMyLessonResponseDto {
    private Long studentId;
    private Long lessonId;
    private Integer status;

    @Builder
    public AttendanceFindMyLessonResponseDto(Long studentId, Long lessonId, Integer status) {
        this.studentId = studentId;
        this.lessonId = lessonId;
        this.status = status;
    }

    public static AttendanceFindMyLessonResponseDto toResponseDto(Attendance entity) {
        return AttendanceFindMyLessonResponseDto.builder()
                .studentId(entity.getMember().getId())
                .lessonId(entity.getLesson().getId())
                .status(entity.getStatus())
                .build();
    }
}
