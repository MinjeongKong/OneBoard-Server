package com.connect.oneboardserver.domain.grade;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.login.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GradeRepositoryTest {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @AfterEach
    public void cleanUp() {
        gradeRepository.deleteAll();
        memberRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @Test
    @DisplayName("성적 부여 및 조회")
    public void createGrade() {
        //given
        String lectureTitle = "test lecture";
        String lecturePlan = "test url";
        String semester = "2021-2";

        Lecture lecture = lectureRepository.save(Lecture.builder()
                .title(lectureTitle)
                .lecturePlanUrl(lecturePlan)
                .semester(semester)
                .build());

        String studentNumber = "201823787";
        String name = "Minjeong Kong";
        String password = "0000";
        String email = "kong1301@ajou.ac.kr";
        String userType = "S";
        String university = "Ajou";
        String major = "Software";

        Member student = memberRepository.save(Member.builder()
                .studentNumber(studentNumber)
                .name(name)
                .password(password)
                .email(email)
                .userType(userType)
                .university(university)
                .major(major)
                .roles(Collections.singletonList(userType))
                .build());

        Float score = 40.3f;

        gradeRepository.save(Grade.builder()
                .lecture(lecture)
                .student(student)
                .score(score)
                .build());


        // when
        List<Grade> gradeList = gradeRepository.findAll();

        // then
        Grade grade = gradeList.get(0);
        assertThat(grade.getStudent().getEmail()).isEqualTo(email);
        assertThat(grade.getLecture().getId()).isEqualTo(lecture.getId());
        assertThat(grade.getScore()).isEqualTo(score);
    }
}
