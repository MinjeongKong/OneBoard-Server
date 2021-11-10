package com.connect.oneboardserver.service.lecture.plan;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.plan.PlanUploadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
        String uploadedFile = null;
        try {
            String path = "/lecture_" + lectureId + "/plan";
            uploadedFile = storageService.store(path, file);

            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        lecture.updateLecturePlan(uploadedFile);
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

            if(lecture.getLecturePlan() != null) {
                System.out.println(lecture.getLecturePlan());
                if(storageService.delete(lecture.getLecturePlan())) {
                    lecture.updateLecturePlan(null);
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
}
