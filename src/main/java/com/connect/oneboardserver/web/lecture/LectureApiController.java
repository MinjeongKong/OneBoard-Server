package com.connect.oneboardserver.web.lecture;

import com.connect.oneboardserver.service.lecture.LectureService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.LectureCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class LectureApiController {

    private final LectureService lectureService;

    // 과목 등록
    @PostMapping("/lecture")
    public ResponseDto createLecture(@RequestBody LectureCreateRequestDto requestDto) {
        return lectureService.createLecture(requestDto);
    }

    // 과목 목록 조회
//    @GetMapping("/lectures")
//    public ResponseDto findLectureList() {
//        return lectureService.findLectureList();
//    }

    // 과목 정보 조회
}
