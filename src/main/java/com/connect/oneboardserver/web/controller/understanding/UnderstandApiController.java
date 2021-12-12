package com.connect.oneboardserver.web.controller.understanding;

import com.connect.oneboardserver.service.understanding.UnderstandProService;
import com.connect.oneboardserver.service.understanding.UnderstandStuService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.understanding.UnderstandStuCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class UnderstandApiController {

    private final UnderstandProService understandProService;
    private final UnderstandStuService understandStuService;

    //이해도 평가 요청 (강의자)
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/live/understanding/professor")
    public ResponseDto createUnderstandPro(@PathVariable Long lectureId, @PathVariable Long lessonId,
                                           @RequestParam("session") String session) {
        return understandProService.createUnderstandPro(lectureId, lessonId, session);
    }

    //이해도 평가 결과 확인 (강의자)
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/live/understanding/{understandId}/professor")
    public ResponseDto findResult(@PathVariable Long lectureId, @PathVariable Long lessonId, @PathVariable Long understandId) {
        return understandStuService.findResult(lectureId, lessonId, understandId);
    }

    //이해도 평가 응답 (학생)
    @PostMapping("/lecture/{lectureId}/lesson/{lessonId}/live/understanding/{understandId}/student")
    public ResponseDto createUnderstandStu(@PathVariable Long lectureId, @PathVariable Long lessonId, @PathVariable Long understandId,
                                           @RequestParam("session") String session, ServletRequest request,
                                           @RequestBody UnderstandStuCreateRequestDto requestDto) {
        return understandStuService.createUnderstandStu(lectureId, lessonId, understandId, session, request, requestDto);
    }
}
