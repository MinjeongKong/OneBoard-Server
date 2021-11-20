package com.connect.oneboardserver.domain.grade;

import com.connect.oneboardserver.domain.lecture.Lecture;
import com.connect.oneboardserver.domain.lecture.LectureRepository;
import com.connect.oneboardserver.domain.login.Member;
import com.connect.oneboardserver.domain.login.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
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

    @BeforeEach
    public void cleanUp() {
        gradeRepository.deleteAll();
        memberRepository.deleteAll();
        lectureRepository.deleteAll();
    }

    @AfterEach
    public void cleanUp2() {
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
        String name = "공민정";
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

        String studentNumber1 = "201823787";
        String name1 = "공주식";
        String password1 = "0000";
        String email1 = "kong1605@ajou.ac.kr";
        String userType1 = "S";
        String university1 = "Ajou";
        String major1 = "Software";

        Member student1 = memberRepository.save(Member.builder()
                .studentNumber(studentNumber1)
                .name(name1)
                .password(password1)
                .email(email1)
                .userType(userType1)
                .university(university1)
                .major(major1)
                .roles(Collections.singletonList(userType1))
                .build());

        String studentNumber2 = "201823787";
        String name2 = "정혜미";
        String password2 = "0000";
        String email2 = "jeong@ajou.ac.kr";
        String userType2 = "S";
        String university2 = "Ajou";
        String major2 = "Software";

        Member student2 = memberRepository.save(Member.builder()
                .studentNumber(studentNumber2)
                .name(name2)
                .password(password2)
                .email(email2)
                .userType(userType2)
                .university(university2)
                .major(major2)
                .roles(Collections.singletonList(userType2))
                .build());


        Float score = 40.3f;
        Float score1 = 50f;
        Float score2 = 30f;

        gradeRepository.save(Grade.builder()
                .lecture(lecture)
                .student(student)
                .totalScore(score)
                .build());

        gradeRepository.save(Grade.builder()
                .lecture(lecture)
                .student(student1)
                .totalScore(score1)
                .build());

        gradeRepository.save(Grade.builder()
                .lecture(lecture)
                .student(student2)
                .totalScore(score2)
                .build());

        // when
        List<Grade> gradeList = gradeRepository.findAll();
        List<Grade> sortList = gradeRepository.findAllByLectureIdOrderByTotalScoreDesc(lecture.getId());
        sortList.forEach(grade -> {
            System.out.println(grade.getStudent().getName()+":"+grade.getTotalScore());
        });



        // then
        Grade grade = gradeList.get(0);
        assertThat(grade.getStudent().getEmail()).isEqualTo(email);
        assertThat(grade.getLecture().getId()).isEqualTo(lecture.getId());
        assertThat(grade.getTotalScore()).isEqualTo(score);

    }

}
