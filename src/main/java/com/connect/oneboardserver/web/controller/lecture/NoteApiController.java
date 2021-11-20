package com.connect.oneboardserver.web.controller.lecture;

import com.connect.oneboardserver.service.lecture.NoteService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class NoteApiController {

    private final NoteService noteService;

    // 강의노트 등록 및 수정
    @PostMapping("/lecture/{lectureId}/lesson/{lessonId}/note")
    public ResponseDto uploadNote(@PathVariable Long lectureId, @PathVariable Long lessonId, @RequestParam("file") MultipartFile file) {
        return noteService.uploadNote(lectureId, lessonId, file);
    }

    // 강의노트 삭제
    @DeleteMapping("/lecture/{lectureId}/lesson/{lessonId}/note")
    public ResponseDto deleteNote(@PathVariable Long lectureId, @PathVariable Long lessonId) {
        return noteService.deleteNote(lectureId, lessonId);
    }

    // 강의노트 로드
    @GetMapping("/lecture/{lectureId}/lesson/{lessonId}/note")
    public ResponseEntity<Resource> loadNote(@PathVariable Long lectureId, @PathVariable Long lessonId) throws Exception {
        return noteService.loadNote(lectureId, lessonId);
    }
}