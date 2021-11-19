package com.connect.oneboardserver.web.dto.attendance;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AttendFindAllForStuResponseDto {

    private Long studentId;
    private String studentNumber;
    private String studentName;
    private List<AttendFindForStuDto> attendanceList;

    @Builder
    public AttendFindAllForStuResponseDto(Long studentId, String studentNumber, String studentName, List<AttendFindForStuDto> attendanceList) {
        this.studentId = studentId;
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.attendanceList = attendanceList;
    }

    public void setAttendanceList(List<AttendFindForStuDto> attendanceList) {
        this.attendanceList = attendanceList;
    }
}