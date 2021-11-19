package com.connect.oneboardserver.web.lecture;

import com.connect.oneboardserver.service.lecture.PlanService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class PlanApiController {

    private final PlanService planService;

    // 과목 강의계획서 등록 및 수정
    @PostMapping("/lecture/{lectureId}/plan")
    public ResponseDto uploadLecturePlan(@PathVariable Long lectureId, @RequestParam("file") MultipartFile file) {
        return planService.uploadLecturePlan(lectureId, file);
    }

    // 과목 강의계획서 삭제
    @DeleteMapping("/lecture/{lectureId}/plan")
    public ResponseDto deleteLecturePlan(@PathVariable Long lectureId) {
        return planService.deleteLecturePlan(lectureId);
    }

    // 과목 강의계획서 로드
    @GetMapping("/lecture/{lectureId}/plan")
    public ResponseEntity<Resource> loadLecturePlan(@PathVariable Long lectureId) throws Exception {
        return planService.loadLecturePlan(lectureId);
    }

}
