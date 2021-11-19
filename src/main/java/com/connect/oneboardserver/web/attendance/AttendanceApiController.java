package com.connect.oneboardserver.web.attendance;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.service.attendance.AttendanceService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.attendance.AttendanceUpdateAllRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class AttendanceApiController {

    private final AttendanceService attendanceService;
    private final JwtTokenProvider jwtTokenProvider;

    // 과목 전체 출석 조회 - 강의자
    @GetMapping("/lecture/{lectureId}/attendance")
    public ResponseDto findAllAttendance(@PathVariable Long lectureId) {
        return attendanceService.findAllAttendance(lectureId);
    }

    // 과목 전체 출석 수정 - 강의자
    @PutMapping("/lecture/{lectureId}/attendance")
    public ResponseDto updateAllAttendance(@PathVariable Long lectureId, @RequestBody AttendanceUpdateAllRequestDto requestDto) {
        return attendanceService.updateAllAttendance(lectureId, requestDto);
    }

    // 본인 과목 전체 출석 조회 - 학생
    @GetMapping("/lecture/{lectureId}/attendance/my")
    public ResponseDto findAllMyAttendance(@PathVariable Long lectureId, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getUserPk(token);

        return attendanceService.findAllMyAttendance(email, lectureId);
    }

    // 모든 학생 수업 출석 확인 - 강의자

    // 모든 학생 수업 출석 수정 - 강의자

    // 학생 본인 수업 출석 확인 - 학생

    // 실시간 수업 출석 요청 - 강의자

    // 실시간 수업 출석 응답 - 학생


}
