package com.connect.oneboardserver.domain.lecture;

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
public class LectureRepositoryTest {

    @Autowired
    LectureRepository lectureRepository;

    @AfterEach
    public void cleanUp() {
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("과목 생성 및 조회")
    public void createLecture() {
        // given
        String title = "test_lecture_1";
        String lecturePlan = "test_url";
        String semester = "2021-2";

        lectureRepository.save(Lecture.builder()
                .title(title)
                .lecturePlan(lecturePlan)
                .semester(semester)
                .build());

        // when
        List<Lecture> lectureList = lectureRepository.findAll();

        // then
        Lecture lecture = lectureList.get(0);
        assertThat(lecture.getTitle()).isEqualTo(title);
    }
}
