package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final StorageService storageService;
    private final LectureRepository lectureRepository;

    public String uploadLecturePlan(Long lectureId, MultipartFile file) {
        String str = null;
        if(!file.isEmpty()) {
            str += file.getName() + '\n';
            str += file.getOriginalFilename() + '\n';
            str += file.getContentType();
        }

        try {
            storageService.store(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
