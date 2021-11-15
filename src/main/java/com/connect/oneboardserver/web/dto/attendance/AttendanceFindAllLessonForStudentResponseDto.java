package com.connect.oneboardserver.web.dto.attendance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AttendanceFindAllLessonForStudentResponseDto {

    private Long studentId;
    private String studentNumber;
    private String studentName;
    private List<AttendanceFindForLesson> attendanceList;

    @Builder
    public AttendanceFindAllLessonForStudentResponseDto(Long studentId, String studentNumber, String studentName, List<AttendanceFindForLesson> attendanceList) {
        this.studentId = studentId;
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.attendanceList = attendanceList;
    }

    public void setAttendanceList(List<AttendanceFindForLesson> attendanceList) {
        this.attendanceList = attendanceList;
    }
}