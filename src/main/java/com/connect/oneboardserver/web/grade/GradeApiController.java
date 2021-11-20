package com.connect.oneboardserver.web.grade;

import com.connect.oneboardserver.service.grade.GradeService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.grade.GradeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class GradeApiController {

    private final GradeService gradeService;

    // 개발용
    @GetMapping("/lecture/{lectureId}/grade-dev")
    public void init(@PathVariable Long lectureId) {
        gradeService.init(lectureId);
    }

    // 성적 조회 (학생용)
    @GetMapping("/lecture/{lectureId}/grade")
    public ResponseDto findGradeStu(@PathVariable Long lectureId, ServletRequest request) {
        return gradeService.findGradeStu(lectureId, request);
    }

    // 성적 조회 (강의자용)
    @GetMapping("/lecture/{lectureId}/grade/{studentId}")
    public ResponseDto findGrade(@PathVariable Long lectureId, @PathVariable Long studentId) {
        return gradeService.findGradePro(lectureId, studentId);
    }

    // 성적 리스트 조회 (강의자용)
    @GetMapping("/lecture/{lectureId}/grade/list")
    public ResponseDto findGradeList(@PathVariable Long lectureId) {
        return gradeService.findGradeList(lectureId);
    }

    // 학점 수정 (강의자용)
    @PostMapping("/lecture/{lectureId}/grade/{studentId}")
    public ResponseDto updateResult(@PathVariable Long lectureId, @PathVariable Long studentId, @RequestBody GradeUpdateRequestDto requestDto) {
        return gradeService.updateResult(lectureId, studentId, requestDto);
    }
}
