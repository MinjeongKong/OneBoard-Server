package com.connect.oneboardserver.web.dto.grade;

import com.connect.oneboardserver.domain.grade.Grade;
import com.connect.oneboardserver.web.dto.attendance.AttendanceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GradeFindResponseDto {

    private Long lectureId;
    private String lectureTitle;
    private Long userId;
    private String userName;
    private String studentNumber;
    private Float totalScore;
    private Float submitScore;
    private Float attendScore;
    private String result;

    private List<SubmitScoreResponseDto> submitList;
    private List<AttendanceDto> attendanceList;

    public GradeFindResponseDto(Grade entity, List<SubmitScoreResponseDto> submitList, List<AttendanceDto> attendanceList) {
        this.lectureId = entity.getLecture().getId();
        this.lectureTitle = entity.getLecture().getTitle();
        this.userId = entity.getStudent().getId();
        this.userName = entity.getStudent().getName();
        this.studentNumber = entity.getStudent().getStudentNumber();
        this.totalScore = entity.getTotalScore();
        this.submitScore = entity.getSubmitScore();
        this.attendScore = entity.getAttendScore();
        this.result = entity.getResult();
        this.submitList = submitList;
        this.attendanceList = attendanceList;
    }


}
