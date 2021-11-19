package com.connect.oneboardserver.service.grade;

import com.connect.oneboardserver.domain.grade.GradeRatio;
import com.connect.oneboardserver.domain.grade.GradeRatioRepository;
import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.relation.GradeRatioLecture;
import com.connect.oneboardserver.domain.relation.GradeRatioLectureRepository;
import com.connect.oneboardserver.web.dto.ResponseDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioCreateRequestDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioFindResponseDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioResponseDto;
import com.connect.oneboardserver.web.dto.grade.GradeRatioUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GradeRatioService {

    private final GradeRatioRepository gradeRatioRepository;
    private final LectureRepository lectureRepository;
    private final GradeRatioLectureRepository gradeRatioLectureRepository;

    @Transactional
    public ResponseDto createGradeRatio(Long lectureId, GradeRatioCreateRequestDto requestDto) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        GradeRatio gradeRatio = requestDto.toEntity();
        if (!gradeRatio.isValid()) {
            return new ResponseDto("FAIL");
        } else {
            gradeRatioRepository.save(gradeRatio);
            gradeRatioLectureRepository.save(GradeRatioLecture.builder()
                    .gradeRatio(gradeRatio)
                    .lecture(lecture)
                    .build());
            GradeRatioResponseDto responseDto = new GradeRatioResponseDto(gradeRatio);
            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    @Transactional
    public ResponseDto updateGradeRatio(Long lectureId, GradeRatioUpdateRequestDto requestDto) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        GradeRatioLecture gradeRatioLecture = gradeRatioLectureRepository.findByLectureId(lectureId);
        GradeRatio gradeRatio1 = gradeRatioLecture.getGradeRatio();
        GradeRatio gradeRatio2 = requestDto.toEntity();

        if (!gradeRatio2.isValid()) {
            return new ResponseDto("FAIL");
        } else {
            gradeRatio1.update(gradeRatio2);
            GradeRatioResponseDto responseDto = new GradeRatioResponseDto(gradeRatio1);

            return new ResponseDto("SUCCESS", responseDto);
        }
    }

    public ResponseDto findGradeRatio(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()->new IllegalArgumentException("해당 과목이 없습니다. id="+lectureId));

        GradeRatioLecture gradeRatioLecture = gradeRatioLectureRepository.findByLectureId(lectureId);
        GradeRatio gradeRatio = gradeRatioLecture.getGradeRatio();

        GradeRatioFindResponseDto responseDto = new GradeRatioFindResponseDto(gradeRatio);
        return new ResponseDto("SUCCESS", responseDto);
    }


}
