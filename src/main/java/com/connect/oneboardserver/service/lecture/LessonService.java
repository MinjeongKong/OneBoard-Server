package com.connect.oneboardserver.service.lecture;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.service.attendance.AttendanceService;
import com.connect.oneboardserver.service.storage.StorageService;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.*;
import com.connect.oneboardserver.web.dto.lecture.lesson.note.NoteUploadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonService {

    @Qualifier("FileStorageService")
    private final StorageService storageService;
    private final LessonRepository lessonRepository;
    private final LectureRepository lectureRepository;
    private final AttendanceService attendanceService;

    public ResponseDto findLessonList(Long lectureId) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lecture.getId());
        List<LessonListFindResponseDto> lessonListFindResponseDtoList = new ArrayList<>();
        for (int i = 0; i < lessonList.size(); i++) {
            lessonListFindResponseDtoList.add(LessonListFindResponseDto.toResponseDto(lessonList.get(i)));
        }
        return new ResponseDto("SUCCESS", lessonListFindResponseDtoList);
    }
//
//    @Transactional
//    public ResponseDto createLesson(Long lectureId, LessonCreateRequestDto requestDto) {
//        Lecture lecture = null;
//        try {
//            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseDto("FAIL");
//        }
//
//        Lesson lesson = requestDto.toEntity();
//        lesson.setLecture(lecture);
//
//        Lesson savedLesson = lessonRepository.save(lesson);
//
//        attendanceService.initLessonAttendance(lecture.getId(), savedLesson);
//
//        LessonCreateResponseDto responseDto = LessonCreateResponseDto.builder()
//                .lessonId(savedLesson.getId())
//                .build();
//
//        return new ResponseDto("SUCCESS" ,responseDto);
//
//    }

    @Transactional
    public ResponseDto createLesson(Long lectureId, LessonCreateRequestDto requestDto, MultipartFile file) throws Exception {
        Lecture lecture = null;
        String uploadedFile = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        Lesson lesson = requestDto.toEntity();
        lesson.setLecture(lecture);

        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        }
        Lesson savedLesson = lessonRepository.save(lesson);

        attendanceService.initLessonAttendance(lecture.getId(), savedLesson);

        LessonCreateResponseDto responseDto = LessonCreateResponseDto.builder()
                .lessonId(savedLesson.getId())
                .build();

        if (file != null) {
            String path = "/lecture_" + lectureId + "/lesson_" + lesson.getId() + "/note";
            uploadedFile = storageService.store(path, file);

            lesson.updateNoteUrl(uploadedFile);
        }
        return new ResponseDto("SUCCESS", responseDto);
    }

    public ResponseDto findLesson(Long lectureId, Long lessonId) {
        Lesson lesson = null;
        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            LessonFindResponseDto responseDto = LessonFindResponseDto.toResponseDto(lesson);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto deleteLesson(Long lectureId, Long lessonId) throws IOException {
        Lesson lesson = null;

        try {
            lesson = lessonRepository.findById(lessonId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if(!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            if (lesson.getNoteUrl() != null) {
                System.out.println(lesson.getNoteUrl());
                if (storageService.delete(lesson.getNoteUrl())) {
                    lesson.updateNoteUrl(null);
                }
            }
            attendanceService.deleteLessonAttendance(lesson.getId());
            lessonRepository.deleteById(lessonId);
            return new ResponseDto("SUCCESS");
        }
    }

    @Transactional
    public ResponseDto updateLesson(Long lectureId, long lessonId, LessonUpdateRequestDto requestDto) {
        Lesson lesson = null;

        try {
            lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        if(!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            lesson.update(requestDto.getTitle(), requestDto.getDate(), requestDto.getNoteUrl(), requestDto.getType(),
                    requestDto.getRoom(), requestDto.getMeetingId(), requestDto.getVideoUrl());
            LessonUpdateResponseDto responseDto = LessonUpdateResponseDto.builder()
                    .lessonId(lesson.getId())
                    .build();
            return new ResponseDto("SUCCESS", responseDto);
        }
    }
}