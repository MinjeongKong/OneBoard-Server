package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.plan.PlanUploadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PlanService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;

    private final LectureRepository lectureRepository;

    @Transactional
    public ResponseDto uploadLecturePlan(Long lectureId, MultipartFile file) {
        if(file.isEmpty()) {
            return new ResponseDto("FAIL");
        }

        Lecture lecture = null;
        String uploadedFilePath = null;
        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);

            // 강의계획서 파일이 있으면 파일 삭제
            if(lecture.getLecturePlanUrl() != null) {
                if(storageService.delete(lecture.getLecturePlanUrl())) {
                    lecture.updateLecturePlanUrl(null);
                }
            }

            String path = "/lecture_" + lectureId + "/plan";
            uploadedFilePath = storageService.store(path, file);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        lecture.updateLecturePlanUrl(uploadedFilePath);

        PlanUploadResponseDto responseDto = PlanUploadResponseDto.builder()
                .lectureId(lecture.getId())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto deleteLecturePlan(Long lectureId) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);

            if(lecture.getLecturePlanUrl() != null) {
                System.out.println(lecture.getLecturePlanUrl());
                if(storageService.delete(lecture.getLecturePlanUrl())) {
                    lecture.updateLecturePlanUrl(null);
                    return new ResponseDto("SUCCESS");
                } else {
                    throw new Exception("No file to delete");
                }
            } else {
                throw new Exception("No plan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
    }

    public ResponseEntity<Resource> loadLecturePlan(Long lectureId) {
        Lecture lecture = null;
        Resource resource = null;
        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
            String filePath = lecture.getLecturePlanUrl();
            resource = storageService.load(filePath);
            String contentDisposition = "attachment; filename=\"" +
                    lecture.getTitle() + "_plan" + filePath.substring(filePath.lastIndexOf(".")) + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resource);
        }
    }
}
