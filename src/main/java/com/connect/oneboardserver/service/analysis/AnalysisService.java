package com.connect.oneboardserver.service.analysis;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import com.connect.oneboardserver.domain.lecture.lesson.LessonRepository;
import com.connect.oneboardserver.domain.quiz.QuizPro;
import com.connect.oneboardserver.domain.quiz.QuizProRepository;
import com.connect.oneboardserver.domain.understanding.UnderstandPro;
import com.connect.oneboardserver.domain.understanding.UnderstandProRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.analysis.AnalysisResponseDto;
import com.connect.oneboardserver.web.dto.analysis.QuizAnalysisDto;
import com.connect.oneboardserver.web.dto.analysis.UnderstandAnalysisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnalysisService {

    private final LessonRepository lessonRepository;
    private final UnderstandProRepository understandProRepository;
    private final QuizProRepository quizProRepository;

    @Transactional
    public ResponseDto findAnalysis(Long lectureId, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()->new IllegalArgumentException("해당 수업이 없습니다. id="+lessonId));

        if (!lesson.getLecture().getId().equals(lectureId)) {
            return new ResponseDto("FAIL");
        } else {
            if (lesson.getLiveMeeting() == null || lesson.getLiveMeeting().getEndDt() == null) {
                return new ResponseDto("FAIL");
            } else {
                List<UnderstandPro> understandProList = understandProRepository.findAllByLessonId(lessonId);
                List<QuizPro> quizProList = quizProRepository.findAllByLessonId(lessonId);

                List<UnderstandAnalysisDto> understandAnalysisDtoList = new ArrayList<>();
                List<QuizAnalysisDto> quizAnalysisDtoList = new ArrayList<>();

                for (int i = 0; i < understandProList.size(); i++) {
                    understandAnalysisDtoList.add(new UnderstandAnalysisDto(understandProList.get(i)));
                }
                for (int i = 0; i < quizProList.size(); i++) {
                    quizAnalysisDtoList.add(new QuizAnalysisDto(quizProList.get(i)));
                }

                AnalysisResponseDto responseDto = new AnalysisResponseDto(lesson, understandAnalysisDtoList, quizAnalysisDtoList);
                return new ResponseDto("SUCCESS", responseDto);
            }
        }
    }
}
