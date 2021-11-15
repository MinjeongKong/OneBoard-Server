package com.connect.oneboardserver.web.lecture;

import com.connect.oneboardserver.config.security.JwtTokenProvider;
import com.connect.oneboardserver.service.lecture.LectureService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class LectureApiController {

    private final LectureService lectureService;
    private final JwtTokenProvider jwtTokenProvider;

    // 과목 생성 -> 개발용
    @PostMapping("/lecture")
    public ResponseDto createLecture(@RequestBody LectureCreateRequestDto requestDto) {
        return lectureService.createLecture(requestDto);
    }

    // 과목 등록 -> 개발용
    @GetMapping("/lecture/register")
    public ResponseDto registerMemberToLecture(@RequestParam Long memberId, @RequestParam Long lectureId) {
        return lectureService.registerLecture(memberId, lectureId);
    }

    // 전체 과목 조회 -> 개발용
    @GetMapping("/lectures/all")
    public ResponseDto findAllLecture() {
        return lectureService.findAllLecture();
    }

    // 과목 목록 조회
    @GetMapping("/lectures")
    public ResponseDto findLectureList(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String email = jwtTokenProvider.getUserPk(token);

        return lectureService.findLectureList(email);
    }

    // 과목 정보 조회
    @GetMapping("/lecture/{lectureId}")
    public ResponseDto findLecture(@PathVariable Long lectureId) {
        return lectureService.findLecture(lectureId);
    }
}
