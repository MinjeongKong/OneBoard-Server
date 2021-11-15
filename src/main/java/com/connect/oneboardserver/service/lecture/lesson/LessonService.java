package com.connect.oneboardserver.service.lecture.lesson;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.lecture.notice.Notice;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.LessonCreateRequestDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.LessonCreateResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.LessonFindResponseDto;
import com.connect.oneboardserver.web.dto.lecture.lesson.LessonListFindResponseDto;
import com.connect.oneboardserver.web.dto.lecture.notice.NoticeFindResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LectureRepository lectureRepository;

    public ResponseDto findLessonList(Long lectureId) {
        Lecture lecture = null;

        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }
        List<Lesson> lessonList = lessonRepository.findAllByLectureId(lecture.getId());
        LessonListFindResponseDto responseDto = LessonListFindResponseDto.builder()
                .lessonList(lessonList)
                .build();
        return new ResponseDto("SUCCESS", responseDto);
    }
    @Transactional
    public ResponseDto createLesson(Long lectureId, LessonCreateRequestDto requestDto) {
        Lecture lecture = null;
        try {
            lecture = lectureRepository.findById(lectureId).orElseThrow(Exception::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto("FAIL");
        }

        Lesson lesson = requestDto.toEntity();
        lesson.setLecture(lecture);

        LessonCreateResponseDto responseDto = LessonCreateResponseDto.builder()
                .lessonId(lessonRepository.save(lesson).getId())
                .build();
        return new ResponseDto("SUCCESS" ,responseDto);
    }

    public ResponseDto findLesson(Long lectureId, Long lessonId) {
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
            LessonFindResponseDto responseDto = LessonFindResponseDto.builder()
                    .lesson(lesson)
                    .build();
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

}