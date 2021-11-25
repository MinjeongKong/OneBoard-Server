package com.connect.oneboardserver.web.controller.attendance;

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
    @GetMapping("/lecture/{lectureId}/attendances")
    public ResponseDto findAllLectureAttendanceList(@PathVariable Long lectureId) throws Exception {
        return attendanceService.findAllLectureAttendanceList(lectureId);
    }

    // 과목 전체 출석 수정 - 강의자
    @PutMapping("/lecture/{lectureId}/attendances")
    public ResponseDto updateAllLectureAttendanceList(@PathVariable Long lectureId, @RequestBody AttendanceUpdateAllRequestDto requestDto) {
        return attendanceService.updateAllLectureAttendanceList(lectureId, requestDto);
    }

    // 본인 과목 전체 출석 조회 - 학생
    @GetMapping("/lecture/{lectureId}/attendances/my")
    public ResponseDto findAllMyLectureAttendanceList(@PathVariable Long lectureId, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getUserPk(token);

        return attendanceService.findAllMyLectureAttendanceList(email, lectureId);
    }

    // 학생 수업 출석 조회 - 강의자
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/attendances")
    public ResponseDto findAllLessonAttendanceList(@PathVariable Long lectureId, @PathVariable Long lessonId) throws Exception {
        return attendanceService.findAllLessonAttendanceList(lectureId, lessonId);
    }

    // 학생 수업 출석 수정 - 강의자
    @PutMapping("/lecture/{lectureId}/lesson/{lessonId}/attendances")
    public ResponseDto updateAllLessonAttendanceList(@PathVariable Long lectureId, @PathVariable Long lessonId,
                                                     @RequestBody AttendanceUpdateAllRequestDto requestDto) throws Exception {
        return attendanceService.updateAllLessonAttendanceList(lectureId, lessonId, requestDto);
    }

    // 모든 학생 수업 출석 수정 - 강의자

    // 학생 본인 수업 출석 확인 - 학생

    // 실시간 수업 출석 요청 - 강의자

    // 실시간 수업 출석 응답 - 학생


}
