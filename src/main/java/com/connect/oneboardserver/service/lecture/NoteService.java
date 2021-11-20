package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.note.NoteUploadResponseDto;
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
public class NoteService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;
    private final LessonRepository lessonRepository;

    @Transactional
    public ResponseDto uploadNote(Long lectureId, Long lessonId, MultipartFile file) {
        if(file.isEmpty()) {
            return new ResponseDto("FAIL");
        }
        Lesson lesson = null;
        String uploadedFile = null;
        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);
            if (!lesson.getLecture().getId().equals(lectureId)) {
                return new ResponseDto("FAIL");
            }
            // 강의노트 파일이 있으면 파일 삭제
            if(lesson.getNote() != null) {
                if(storageService.delete(lesson.getNote())) {
                    lesson.updateNote(null);
                }
            }
            String path = "/lecture_" + lectureId + "/lesson_" + lessonId + "/note";
            uploadedFile = storageService.store(path, file);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        lesson.updateNote(uploadedFile);

        NoteUploadResponseDto responseDto = NoteUploadResponseDto.builder()
                .lessonId(lesson.getId())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    public ResponseDto deleteNote(Long lectureId, Long lessonId) {
        Lesson lesson = null;

        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);

            if (!lesson.getLecture().getId().equals(lectureId)) {
                return new ResponseDto("FAIL");
            }
            if(lesson.getNote() != null) {
                System.out.println(lesson.getNote());
                if(storageService.delete(lesson.getNote())) {
                    lesson.updateNote(null);
                    return new ResponseDto("SUCCESS");
                } else {
                    throw new Exception("fail");
                }
            } else {
                throw new Exception("No note");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
    }

    public ResponseEntity<Resource> loadNote(Long lectureId, Long lessonId) {
        Lesson lesson = null;
        Resource resource = null;
        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);
            if(lesson.getNote() != null) {
                String filePath = lesson.getNote();
                resource = storageService.load(filePath);
                if (!lesson.getLecture().getId().equals(lectureId)) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resource);
                }
                String contentDisposition = "attachment; filename=\"" +
                        lesson.getTitle() + "_note\"";
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                        .body(resource);
            }else {
                throw new Exception("No file to load");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resource);
        }
    }
}
