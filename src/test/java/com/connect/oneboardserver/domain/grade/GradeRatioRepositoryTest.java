package com.connect.oneboardserver.domain.grade;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.relation.GradeRatioLecture;
import com.connect.oneboardserver.domain.relation.GradeRatioLectureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GradeRatioRepositoryTest {

    @Autowired
    private GradeRatioRepository gradeRatioRepository;

    @Autowired
    private GradeRatioLectureRepository gradeRatioLectureRepository;

    @Autowired
    private LectureRepository lectureRepository;


    @AfterEach
    public void cleanUp() {
        gradeRatioLectureRepository.deleteAll();
        gradeRatioRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("학점 비율 입력 및 조회")
    public void createGradeRatio() {
        //given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlan)
                .semester(semester)
                .build());

        Integer aRatio = 20;
        Integer bRatio = 50;

        GradeRatio gradeRatio = gradeRatioRepository.save(GradeRatio.builder()
                .aratio(aRatio)
                .bratio(bRatio)
                .build());

        gradeRatioLectureRepository.save(GradeRatioLecture.builder()
                .lecture(lecture)
                .gradeRatio(gradeRatio)
                .build());

        // when
        List<GradeRatio> gradeRatioList = gradeRatioRepository.findAll();
        List<GradeRatioLecture> gradeRatioLectureList = gradeRatioLectureRepository.findAll();

        // then
        GradeRatioLecture gradeRatioLecture = gradeRatioLectureList.get(0);
        GradeRatio gradeRatio1 = gradeRatioList.get(0);
        GradeRatio gradeRatio2 = gradeRatioLecture.getGradeRatio();
        assertThat(gradeRatio1.getAratio()).isEqualTo(aRatio);
        assertThat(gradeRatio1.getBratio()).isEqualTo(gradeRatio2.getBratio());

    }
}
