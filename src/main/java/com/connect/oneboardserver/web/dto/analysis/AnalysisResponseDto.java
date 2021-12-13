package com.connect.oneboardserver.web.dto.analysis;

import com.connect.oneboardserver.domain.lecture.lesson.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AnalysisResponseDto {

    private Long lessonId;
    private String lessonTitle;
    private List<UnderstandAnalysisDto> understandAnalysisDtoList;
    private List<QuizAnalysisDto> quizAnalysisDtoList;

    public AnalysisResponseDto(Lesson entity,
                               List<UnderstandAnalysisDto> understandAnalysisDtoList, List<QuizAnalysisDto> quizAnalysisDtoList) {
        this.lessonId = entity.getId();
        this.lessonTitle = entity.getTitle();
        this.understandAnalysisDtoList = understandAnalysisDtoList;
        this.quizAnalysisDtoList = quizAnalysisDtoList;
    }
}
