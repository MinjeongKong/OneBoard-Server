package com.connect.oneboardserver.web.grade;

import com.connect.oneboardserver.service.grade.GradeRatioService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioCreateRequestDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class GradeRatioApiController {

    private final GradeRatioService gradeRatioService;

    //비율 등록 (default:30/70) -> 개발용
    @GetMapping("/lecture/{lectureId}/grade/ratio-dev")
    public void init(@PathVariable Long lectureId) {
        gradeRatioService.init(lectureId);
    }

    //비율 수정
    @PostMapping("/lecture/{lectureId}/grade/ratio")
    public ResponseDto updateGradeRatio(@PathVariable Long lectureId, @Valid @RequestBody GradeRatioUpdateRequestDto requestDto) {
        return gradeRatioService.updateGradeRatio(lectureId, requestDto);
    }

    //비율 조회
    @GetMapping("/lecture/{lectureId}/grade/ratio")
    public ResponseDto findGradeRatio(@PathVariable Long lectureId) {
        return gradeRatioService.findGradeRatio(lectureId);
    }
}
