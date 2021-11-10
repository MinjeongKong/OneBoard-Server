package com.connect.oneboardserver.web.lecture;

import com.connect.oneboardserver.service.lecture.LectureService;
import com.connect.oneboardserver.service.storage.FileStorageService;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class LectureApiController {

    private final LectureService lectureService;

    // 과목 강의계획서 등록
    @PostMapping("/lecture/{lectureId}/plan")
    public String uploadLecturePlan(@PathVariable Long lectureId, @RequestParam("file") MultipartFile file) {
        return lectureService.uploadLecturePlan(lectureId, file);
    }

    // 과목 강의계획서 조회

    // 과목 강의계획서 수정
}
