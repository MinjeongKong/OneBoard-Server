package com.connect.oneboardserver.service.understanding;

import com.connect.oneboardserver.config.socket.SocketService;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.understanding.UnderstandPro;
import com.connect.oneboardserver.domain.understanding.UnderstandProRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.understanding.UnderstandProResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UnderstandProService {

    private final UnderstandProRepository understandProRepository;
    private final LessonRepository lessonRepository;
    private final SocketService socketService;

    @Transactional
    public ResponseDto createUnderstandPro(Long lectureId, Long lessonId, String session) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()->new IllegalArgumentException("해당 수업이 없습니다. id="+lessonId));

        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            UnderstandPro understandPro = UnderstandPro.builder()
                    .lesson(lesson).build();
            understandProRepository.save(understandPro);

            socketService.sendUnderstandingRequestEvent(session);

            UnderstandProResponseDto responseDto = new UnderstandProResponseDto(understandPro);
            return new ResponseDto("SUCCESS", responseDto);
        }

    }

}
