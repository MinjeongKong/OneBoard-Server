package com.connect.oneboardserver.web.grade;

import com.connect.oneboardserver.service.grade.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class GradeApiController {

    private final GradeService gradeService;

    // 개발용
    @GetMapping("/lecture/{lectureId}/grade-dev")
    public void createGrade(@PathVariable Long lectureId) {
        gradeService.createGrade(lectureId);
    }
}
