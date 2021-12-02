package com.connect.oneboardserver.web.controller.lecture;

import com.connect.oneboardserver.service.lecture.LessonService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class LessonApiController {

    private final LessonService lessonService;

    // 수업 목록 조회
    @GetMapping("/lecture/{lectureId}/lessons")
    public ResponseDto findLessonList(@PathVariable Long lectureId) {
        return lessonService.findLessonList(lectureId);
    }

    // 수업 정보 조회
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}")
    public ResponseDto findLesson(@PathVariable Long lectureId, @PathVariable Long lessonId) {
        return lessonService.findLesson(lectureId, lessonId);
    }

    // 수업 생성
    @PostMapping("/lecture/{lectureId}/lesson")
    public ResponseDto createLesson(@PathVariable Long lectureId, @ModelAttribute LessonCreateRequestDto requestDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        return lessonService.createLesson(lectureId, requestDto, file);
    }

    // 수업 수정
    @PutMapping("/lecture/{lectureId}/lesson/{lessonId}")
    public ResponseDto updateLesson(@PathVariable Long lectureId, @PathVariable Long lessonId,
                                    @ModelAttribute LessonUpdateRequestDto requestDto, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        return lessonService.updateLesson(lectureId, lessonId, requestDto, file);
    }

    // 수업 삭제
    @DeleteMapping("/lecture/{lectureId}/lesson/{lessonId}")
    public ResponseDto deleteLesson(@PathVariable Long lectureId, @PathVariable Long lessonId) throws IOException {
        return lessonService.deleteLesson(lectureId, lessonId);
    }

    // 수업 생성 시 디폴트 정보 요청
    @GetMapping("/lecture/{lectureId}/lesson/default")
    public ResponseDto findLessonDefaultInfo(@PathVariable Long lectureId) {
        return lessonService.findLessonDefaultInfo(lectureId);
    }

    // 수업 생성 - 테스트용
    @PostMapping("/lecture/{lectureId}/lesson/test")
    public ResponseDto createLessonForTest(@PathVariable Long lectureId, @RequestBody LessonCreateRequestDto requestDto) {
        return lessonService.createLessonForTest(lectureId, requestDto);
    }

    // 수업 수정 - 테스트용
    @PutMapping("/lecture/{lectureId}/lesson/{lessonId}/test")
    public ResponseDto updateLessonForTest(@PathVariable Long lectureId, @PathVariable Long lessonId,
                                           @RequestBody LessonUpdateRequestDto requestDto) {
        return lessonService.updateLessonForTest(lectureId, lessonId, requestDto);
    }

}