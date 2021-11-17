package com.connect.oneboardserver.domain.assignment;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AssignmentRepositoryTest {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @AfterEach
    public void cleanUp() {
        assignmentRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("과제 생성 및 조회")
    public void createAssignment() {
        //given
        String lectureTitle = "test lecture";
        String lecturePlanUrl = "test url";
        String semester = "2021-2";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlanUrl)
                .semester(semester)
                .build());


        String title = "test title";
        String content = "test content";
        String fileUrl = "test fileUrl";
        String startDt = "test startDt";
        String endDt = "test endDt";
        Float score = 60.8F;

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        Assignment assignment = Assignment.builder()
                .title(title)
                .content(content)
                .fileUrl(fileUrl)
                .startDt(startDt)
                .endDt(endDt)
                .exposeDt(now)
                .score(score)
                .build();

        assignment.setLecture(lecture);
        assignmentRepository.save(assignment);

        //when
        List<Assignment> assignmentList = assignmentRepository.findAll();

        //then
        Assignment assignment1 = assignmentList.get(0);
        assertThat(assignment1.getLecture().getId()).isEqualTo(lecture.getId());
        assertThat(assignment1.getContent()).isEqualTo(content);

    }
}
