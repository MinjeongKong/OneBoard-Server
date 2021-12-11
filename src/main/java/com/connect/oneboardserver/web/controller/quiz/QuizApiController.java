package com.connect.oneboardserver.web.controller.quiz;

import com.connect.oneboardserver.domain.quiz.QuizPro;
import com.connect.oneboardserver.domain.quiz.QuizStu;
import com.connect.oneboardserver.service.quiz.QuizProService;
import com.connect.oneboardserver.service.quiz.QuizStuService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.quiz.QuizProCreateRequestDto;
import com.connect.oneboardserver.web.dto.quiz.QuizStuCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class QuizApiController {

    private final QuizProService quizProService;
    private final QuizStuService quizStuService;

    //퀴즈 출제 (강의자)
    @PostMapping("/lecture/{lectureId}/lesson/{lessonId}/live/quiz/professor")
    public ResponseDto createQuizPro(@PathVariable Long lectureId, @PathVariable Long lessonId, @RequestBody QuizProCreateRequestDto requestDto) {
        return quizProService.createQuizPro(lectureId, lessonId, requestDto);
    }

    //퀴즈 응답 (학생)
    @PostMapping("/lecture/{lectureId}/lesson/{lessonId}/live/quiz/{quizId}/student")
    public ResponseDto createQuizStu(@PathVariable Long lectureId, @PathVariable Long lessonId, @PathVariable Long quizId,
                                     ServletRequest request, @RequestBody QuizStuCreateRequestDto requestDto) {
        return quizStuService.createQuizStu(lectureId, lessonId, quizId, request, requestDto);
    }

    //퀴즈 결과 확인 (강의자)
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/live/quiz/{quizId}/professor")
    public ResponseDto findResultPro(@PathVariable Long lectureId, @PathVariable Long lessonId, @PathVariable Long quizId) {
        return quizStuService.findResultPro(lectureId, lessonId, quizId);
    }

    //퀴즈 결과 확인 (학생)
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/live/quiz/{quizId}/student")
    public ResponseDto findResultStu(@PathVariable Long lectureId, @PathVariable Long lessonId, @PathVariable Long quizId, ServletRequest request) {
        return quizStuService.findResultStu(lectureId, lessonId, quizId, request);
    }
}
